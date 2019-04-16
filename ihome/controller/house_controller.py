# -*- coding:utf8 -*-


from flask import request, jsonify, render_template
from . import api, default
from ihome.utils.response_code import RET
from ihome import db
from ihome.models import House, Area, HouseTag, HouseImage, Community, Facility, HouseFacility
import json


@default.route("/house/own", methods=['GET'])
def house_own():
    return render_template('house/own.html')


@api.route("/house/list", methods=['GET'])
def house_list():
    param_price = request.args.get("price")
    param_kind = request.args.get("kind")
    param_condition = request.args.get("condition")
    param_title = request.args.get("title")
    page_size = request.args.get("page_size", default=10)
    page_no = request.args.get("page_no", default=1)
    page_no = int(page_no)
    # 拼接查询条件
    # filter = house_list_filter(param_kind, param_price, param_condition, param_title)
    list_params = house_list_filter(param_kind, param_price, param_condition, param_title)
    print(filter)
    page_query = None
    if filter:
        page_query = House.query.filter(*list_params).order_by(House.create_time.desc()).paginate(page_no, page_size,
                                                                                                  False)
    else:
        page_query = House.query.order_by(House.create_time.desc()).paginate(page_no, page_size, False)
    print(page_query)
    page_obj = page_query.items
    page_dict = []
    for o in page_obj:
        tag_query = HouseTag.query.filter_by(house_id=o.house_id).all()
        tag_dict = []
        for t in tag_query:
            tag_dict.append(t.to_tag_dict())
        house_dict = o.to_house_list_dict()
        house_dict["tag"] = tag_dict
        page_dict.append(house_dict)
    page = {
        'data': page_dict,
        'total_page': page_query.pages,
        'current_page': page_query.page,
        'next_num': page_query.next_num
    }

    res_dict = dict(error_code=RET.OK, error_msg="OK", data=page)
    return jsonify(res_dict)


@default.route("/house/detail", methods=['GET'])
def house_detail():
    hid = request.args.get("id")

    if not hid:
        render_template('index.html')

    print(filter)

    house_query = None
    house_query = House.query.filter(House.house_id == hid).first()

    # 用户信息
    # user_query = User.query.filter(User.user_id == house_query.user_id).first()
    # user_dict = user_query.to_user_dict()
    user_dict = {"name": "旺旺", "mobile": "13987654321", "avatar": "/static/image/avatar/default.png"}

    # 学区
    area_query = Area.query.filter_by(area_id=house_query.area_id).first()
    area_dict = None
    if area_query:
        area_query.to_dict()

    # 标签
    tag_query = HouseTag.query.filter_by(house_id=hid).all()
    tags_dict = []
    for t in tag_query:
        tags_dict.append(t.to_tag_dict())

    # 图片
    image_query = HouseImage.query.filter_by(house_id=hid).all()
    images_dict = []
    for i in image_query:
        images_dict.append(i.to_image_dict())

    # 小区
    community_query = Community.query.filter_by(community_id=house_query.community_id).first()
    community_dict = None
    if community_query:
        community_dict = community_query.to_com_dict()

    # 设施
    facility_sql = "select f.* from t_facility f " \
                   "left JOIN t_house h " \
                   "on h.house_id = 1 " \
                   "left join t_house_facility hf " \
                   "on h.house_id = hf.house_id " \
                   "WHERE f.facility_id = hf.facility_id"
    facility_query = db.session.execute(facility_sql)
    facility_data = facility_query.fetchall()
    facility_dict = []
    for f in facility_data:
        d = {
            "facility_id": f[0],
            "name": f[1],
            "icon": f[2]
        }
        facility_dict.append(d)
    data = house_query.to_house_detail_dict(area_dict, tags_dict, images_dict, community_dict)
    data["user"] = user_dict
    data["facility"] = facility_dict
    print(data)
    return render_template('house/detail.html', h=data)

# 首页查询条件          类型  价格    筛选（待定）  标题
def house_list_filter(kind, price, condition, title):
    params = []
    if kind:
        params.append(House.kind == kind)
    if price:
        price = int(price)
        if price == 0:
            params.append(House.price <= 1500)
        elif price == 1:
            params.append(House.price > 1500)
            params.append(House.price <= 3000)
        elif price == 2:
            params.append(House.price > 3000)
            params.append(House.price <= 4500)
        elif price == 3:
            params.append(House.price > 4500)
    if title:
        tag = '%' + title + '%'
        params.append(House.title.like(tag))
    return params
