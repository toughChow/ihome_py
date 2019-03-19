# -*- coding:utf8 -*-


from flask import render_template, Blueprint, request, jsonify, session
from werkzeug.security import generate_password_hash, check_password_hash

from ihome import db
from models import User
from utils.response_code import RET
from . import api, default


# 跳转首页
@default.route('/', methods=['GET'])
def index():
    print("coming")
    return render_template("index.html")


# 跳转注册页
@default.route("/reg", methods=['GET', 'POST'])
def reg():
    if request.method == 'GET':
        return render_template("reg.html")
    else:
        req_dict = request.get_json()
        username = req_dict.get("username")
        password = req_dict.get("password")
        mobile = req_dict.get("mobile")
        user = None
        try:
            user = User.query.filter_by(username=username).first()
            if user is not None:
                return jsonify(errno=RET.DATAEXIST, errmsg="用户名已存在")
            user = User.query.filter_by(mobile=mobile).first()
            if user is not None:
                return jsonify(errno=RET.DATAEXIST, errmsg="手机已存在")
            password = generate_password_hash(password)
            user = User(username=username, password=password, mobile=mobile)
            db.session.add(user)
            db.session.commit()
            return jsonify(errno=RET.OK, errmsg="注册成功")
        except Exception as e:
            print(e)


# 跳转登录页
@default.route("/login", methods=['GET', 'POST'])
def login():
    if request.method == 'GET':
        username = session.get("username")
        if username is not None:
            print(username)
        return render_template("login.html")
    else:
        req_dict = request.get_json()
        username = req_dict.get("username")
        password = req_dict.get("password")
        # password = generate_password_hash(password)
        user = None
        user = User.query.filter_by(username=username).first()
        if user is not None:
            # 如果验证相同成功，保存登录状态， 在session中
            verifi = check_password_hash(user.password, password)
            if verifi is True:
                session["username"] = user.username
                session["mobile"] = user.mobile
                session["user_id"] = user.user_id
                return jsonify(errno=RET.OK, errmsg="登录成功")
            else:
                return jsonify(errno=RET.PWDERR, errmsg="用户名或密码错误")
        else:
            return jsonify(errno=RET.NODATA, errmsg="用户不存在")
        # sql = "SELECT * FROM T_USER WHERE username ='%s' AND password = '%s'" % (
        #     username, password)
        # rows = user_dal.User_Dal.query(sql)
        # print('查询结果>>>', rows)
        # return render_template('user/index.html')
