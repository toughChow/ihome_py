# -*- coding:utf8 -*-


from . import api
from flask import request, jsonify, render_template
from ihome import db
from ihome.utils.response_code import RET
from ihome.models import Area
import json


@api.route("/area", methods=['GET'])
def index():
    return render_template('area/index.html')


@api.route('/area/get', methods=['GET'])
def get():
    areas = Area.query.all()
    print(areas)
    print(type(areas))
    areas_dict = []
    for a in areas:
        areas_dict.append(a.to_dict())
    print(areas_dict)

    # 将数据转换为json字符串
    resp_dict = dict(errno=RET.OK, errmsg="OK", data=areas_dict)
    resp_json = json.dumps(resp_dict)

    return resp_json, 200, {"Content-Type": "application/json"}


@api.route('/area/add', methods=['POST'])
def add():
    if request.method == 'POST':
        name = request.form.get('name', None)

        if not name:
            return jsonify(err_no=RET.PARAMERR, error_msg="参数错误")

        try:
            newobj = Area(name=name)
            db.session.add(newobj)
        except Exception as e:
            print(e)
            db.session.rollback()
            return jsonify(err_no=RET.DBERR, error_msg="保存数据失败")

        return jsonify(err_no=RET.OK, error_msg="OK", data={"area_id": newobj.id})
