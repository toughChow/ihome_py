# -*- coding:utf8 -*-


from flask import render_template, request, jsonify
from werkzeug.security import generate_password_hash

from ihome import db
from ihome.models import User
from utils.response_code import RET
from . import api, default


# 跳转登录页
@default.route("/login2", methods=['POST'])
def regist():
    req_dict = request.get_json()

    username = req_dict.get("username")
    password = req_dict.get("password")
    mobile = req_dict.get("mobile")
    user = None
    try:
        user = User.query.filter_by(mobile=mobile).first()
    except Exception as e:
        print(e)
    if user is not None:
        return jsonify(errno=RET.DATAEXIST, errmag="手机号已存在")
    else:
        password = generate_password_hash(password)
        user = User(real_name=username, password=password)
        db.session.add(user)
        db.session.commit()
        return jsonify(errno=RET.OK, errmsg="成功")


# 跳转登录页
@default.route("/login", methods=['POST'])
def login1():
    username = request.form['username']
    password = request.form['password']
    sql = "SELECT * FROM T_USER WHERE username ='%s' AND password = '%s'" % (
        username, password)
    # rows = user_dal.User_Dal.query(sql)
    # print('查询结果>>>', rows)
    return render_template('user/index.html')
