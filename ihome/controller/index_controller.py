# -*- coding:utf8 -*-


from flask import render_template, request
from . import api, default
from models import House, HouseTag


# 跳转首页
@default.route('/', methods=['GET'])
def index():
    return render_template("index.html")


# 跳转注册页
@default.route("/reg", methods=['GET'])
def reg():
    return render_template("reg.html")


# 跳转登录页
@default.route("/login", methods=['GET'])
def login():
    return render_template("login.html")
