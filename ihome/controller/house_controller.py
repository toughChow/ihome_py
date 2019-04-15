# -*- coding:utf8 -*-

import os
from flask import g, current_app, render_template, Blueprint, request, jsonify, session
from . import api, default
from ihome.models import Area, Community, HouseType, Facility ,House ,Tag, HouseFacility, HouseImg
from ihome import db
import json


@default.route("/house/own", methods=['GET'])
def house_own():
    return render_template('house/own.html')


# 跳转添加租房页
@default.route("/addhouse", methods=['GET', 'POST'])
def addhouse():
    return render_template("addhouse.html")


#获取学校列表
@default.route("/areas", methods=['GET', 'POST'])
def getAreaInfo():
    try:
        area_li = Area.query.all()
    except Exception as e:
        current_app.logger.error(e)
        return jsonify(errno=-1, errmsg="数据库异常")
    area_dict_li = []
    # 将对象转换为字典
    for area in area_li:
        area_dict_li.append(area.to_dict())

    # 将数据转换为json字符串
    resp_dict = dict(errno=0, errmsg="OK", data=area_dict_li)
    resp_json = json.dumps(resp_dict)
    return resp_json, 200,  {"Content-Type": "application/json"}


#获取小区列表
@default.route("/community", methods=['GET', 'POST'])
def getCommunityInfo():
    try:
        community_li = Community.query.all()
    except Exception as e:
        current_app.logger.error(e)
        return jsonify(errno=-1, errmsg="数据库异常")
    community_dict_li = []
    # 将对象转换为字典
    for community in community_li:
        community_dict_li.append(community.to_dict())

    # 将数据转换为json字符串
    resp_dict = dict(errno=0, errmsg="OK", data=community_dict_li)
    resp_json = json.dumps(resp_dict)
    return resp_json, 200,  {"Content-Type": "application/json"}


#获取房屋类型列表
@default.route("/houseType", methods=['GET', 'POST'])
def getHouseTypeInfo():
    try:
        houseType_li = HouseType.query.all()
    except Exception as e:
        current_app.logger.error(e)
        return jsonify(errno=-1, errmsg="数据库异常")
    htype_dict_li = []
    # 将对象转换为字典
    for hType in houseType_li:
        htype_dict_li.append(hType.to_dict())

    # 将数据转换为json字符串
    resp_dict = dict(errno=0, errmsg="OK", data=htype_dict_li)
    resp_json = json.dumps(resp_dict)
    return resp_json, 200,  {"Content-Type": "application/json"}


@default.route("/housesInfo", methods=["POST"])
def save_house_info():
    """保存房屋的基本信息"""

    # 获取数据
    user_id = session.get("user_id")
    house_data = request.get_json()

    title = house_data.get("title")  # 房屋名称标题
    price = house_data.get("price")  # 房屋月租
    area_id = house_data.get("area_id")  # 房屋所在学校的编号
    address = house_data.get("address")  # 房屋地址
    community_id = house_data.get("community_id")  # 房屋所在小区的编号
    housetype_id = house_data.get("houseType_id")  # 房屋类型
    floor = house_data.get("floor")  # 房屋楼层
    depict = house_data.get("depict")  # 房屋描述
    kind = house_data.get("kind") #是否学区房
    beds = house_data.get("beds") #标签
    # 校验参数
    if not all([title, price, address, community_id, housetype_id, floor, depict, kind]):
        return jsonify(errno=-1, errmsg="参数不完整")
    if kind == "0":
        area_id = None
    # 判断城区id是否存在
    if area_id is not None:
        try:
            area = Area.query.get(area_id)
        except Exception as e:
            current_app.logger.error(e)
            return jsonify(errno=-1, errmsg="数据库异常")
        if area is None:
            return jsonify(errno=-1, errmsg="学校信息有误")
    # 保存房屋信息

    house = House(
        user_id=user_id,
        area_id=area_id,
        title=title,
        price=price,
        address=address,
        community_id=community_id,
        type_id=housetype_id,
        floor=floor,
        depict=depict,
        kind=kind
    )
    try:
        db.session.add(house)
        db.session.commit()
    except Exception as e:
        current_app.logger.error(e)
        db.session.rollback()
        return jsonify(errno=300, errmsg="保存房屋数据失败")

    # 保存数据成功
    # 处理房屋标签
    for i in beds.split("，"):
        tag = Tag(
            house_id=house.house_id,
            content=i
        )
        try:
            db.session.add(tag)
            db.session.commit()
        except Exception as e:
            current_app.logger.error(e)
            db.session.rollback()
            return jsonify(errno=-1, errmsg="保存标签数据失败")
    # 处理房屋的设施信息
    facility_ids = house_data.get("facility")
    for i in facility_ids:
        hf = HouseFacility(
            house_id=house.house_id,
            facility_id=i
        )
        try:
            db.session.add(hf)
            db.session.commit()
        except Exception as e:
            current_app.logger.error(e)
            db.session.rollback()
            return jsonify(errno=-1, errmsg="保存设配数据失败")
    return jsonify(errno=0, errmsg="OK", data={"house_id": house.house_id})


@default.route("/image", methods=["POST"])
def save_image():
    """保存房屋的图片
        参数 图片 房屋的id
        """
    image_file = request.files.get("house_image")
    house_id = request.form.get("house_id")
    # 上传图片次数
    img_count = request.form.get("img_count")

    if not all([image_file, house_id, img_count]):
        return jsonify(errno=-1, errmsg="参数错误")
    # 获取特定的文件名
    file_suffix = os.path.splitext(image_file.filename)
    file_name = 'house' + str(house_id) + "_" + img_count + file_suffix[1]
    # 获取项目路径
    cur_path = os.path.abspath(os.path.dirname(__file__))
    root_path = cur_path[:cur_path.find("ihome_py\\") + len("ihome_py\\")]  # 获取myProject，也就是项目的根路径

    path = root_path + "ihome/static/images/house/"
    file_path = path + file_name

    # 保存图片
    image_file.save(file_path)
    if img_count == "0":
        house = House.query.filter_by(house_id=house_id).first()
        # 更新房屋信息
        house.house_img = "/static/images/house/" + file_name
        house.house_id = house_id
        db.session.commit()
    else:
        house_img = HouseImg(
            house_id=house_id,
            url="/static/images/house/" + file_name
        )
        db.session.add(house_img)
        db.session.commit()
    return jsonify(errno=img_count, errmsg="OK", data={"image_url": "/static/images/house/"+file_name})


@default.route("/update_house", methods=['GET', 'POST'])
def update_house():
    house_id = 15
    house = House.query.filter_by(house_id=house_id).first()
    if request.method == 'GET':
        return render_template("updatehouse.html", house=house)
    else:
        # 获取数据
        house_data = request.get_json()
        title = house_data.get("title")  # 房屋名称标题
        price = house_data.get("price")  # 房屋月租
        address = house_data.get("address")  # 房屋地址
        community_id = house_data.get("community_id")  # 房屋所在小区的编号
        housetype_id = house_data.get("houseType_id")  # 房屋类型
        floor = house_data.get("floor")  # 房屋楼层
        depict = house_data.get("depict")  # 房屋描述
        status = house_data.get("status") # 房屋状态
        # 设置更改信息
        house.house_id = house_id
        house.title = title
        house.price = price
        house.address = address
        house.community_id = community_id
        house.type_id = housetype_id
        house.floor = floor
        house.depict = depict
        house.house_status = status
        db.session.commit()
        return jsonify(errno=0, errmsg="OK")

