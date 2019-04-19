# -*- coding:utf8 -*-
import os
import re

from flask import render_template, Blueprint, request, jsonify, session, app
from werkzeug.security import check_password_hash, generate_password_hash

from ihome import db
from ihome.models import User
from ihome.utils.response_code import RET
from . import default


@default.route('/account', methods=['GET'])
def account():
    """
    个人信息设置
    :return:
    """
    # 从Session中获取用户名
    username = session.get("username")
    if username is None:
        return render_template("/login")
    else:
        user = User.query.filter_by(username=username).first()
        return render_template("/user/account.html", user=user)


@default.route('/changePassword', methods=['GET', 'POST'])
def change_password():
    """ 修改密码
    通过匹配原密码，若原密码匹配则更新新密码
    :return:
    """
    if request.method == 'GET':
        return render_template("/user/password.html")
    else:
        req_dict = request.get_json()
        password_origin = req_dict.get("password_origin")
        password_new = req_dict.get("password_new")
        try:
            username = session.get("username")
            if username is None:
                return jsonify(errno=RET.SESSIONERR, errmsg="身份已过期 请重新登录")
            user = User.query.filter_by(username=username).first()
            verifi = check_password_hash(user.password, password_origin)
            if verifi:
                user.password = generate_password_hash(password_new)
                db.session.commit()
                return jsonify(errno=RET.OK, errmsg="更新成功")
            else:
                return jsonify(errno=RET.PWDERR, errmsg="原始密码错误")
        except Exception as e:
            print(e)


@default.route('/real_msg', methods=['GET', 'POST'])
def real_msg():
    """ 更新或新增实名身份
    通过session中存取用户信息进行个人实名信息更新或新增
    :return:
    """
    # 从Session中获取用户名
    username = session.get("username")
    if request.method == 'GET':
        if username is None:
            return render_template("/login")
        else:
            user = User.query.filter_by(username=username).first()
            return render_template("/user/real_msg.html", user=user)
    else:
        # 获取表单传过来的真实姓名和身份证
        req_dict = request.get_json()
        real_name = req_dict.get("real_name")
        real_id_card = req_dict.get("real_id_card")
        if not re.match(r"\d{18}", real_id_card):
            return jsonify(errno=RET.DATAERR, errmsg="身份证格式有误")
        try:
            if username is None:
                return jsonify(errno=RET.SESSIONERR, errmsg="身份已过期 请重新登录")
            user = User.query.filter_by(username=username).first()
            user.real_name = real_name
            user.real_id_card = real_id_card
            db.session.commit()
            return jsonify(errno=RET.OK, errmsg="更新成功")
        except Exception as e:
            print(e)


@default.route('/mobile', methods=['GET', 'POST'])
def mobile():
    """ 修改手机号码
    通过匹配原密码，若原密码匹配则更新手机号码
    :return:
    """
    if request.method == 'GET':
        return render_template("/user/mobile.html")
    else:
        req_dict = request.get_json()
        password_origin = req_dict.get("password_origin")
        mobile_origin = req_dict.get("mobile_origin")
        mobile_new = req_dict.get("mobile_new")
        try:
            username = session.get("username")
            if username is None:
                return jsonify(errno=RET.SESSIONERR, errmsg="身份已过期 请重新登录")
            user = User.query.filter_by(username=username).first()
            verifi = check_password_hash(user.password, password_origin)
            if verifi:
                # 若原始手机号
                if (user.mobile != mobile_origin):
                    return jsonify(errno=RET.DATAERR, errmsg="原始电话不匹配")
                # 若新手机号格式错误
                if not re.match(r"1[3456789]\d{9}", mobile_new):
                    return jsonify(errno=RET.DATAERR, errmsg="手机格式错误")
                else:
                    # 更新成功
                    user.mobile = mobile_new
                    db.session.commit()
                    return jsonify(errno=RET.OK, errmsg="更新成功")
            else:
                return jsonify(errno=RET.PWDERR, errmsg="原始密码错误")
        except Exception as e:
            print(e)


@default.route('/my_info', methods=['GET', 'POST'])
def my_info():
    """
    修改头像以及用户昵称
    :return:
    """
    # 从Session中获取用户名
    username = session.get("username")
    if request.method == 'GET':
        if username is None:
            return render_template("/login")
        else:
            user = User.query.filter_by(username=username).first()
            return render_template("/user/my_info.html", user=user)
    else:
        image_file = request.files.get("avatar")
        # 获取数据库用户信息
        user = User.query.filter_by(username=username).first()
        username = request.form["username"]
        if image_file is None:
            return jsonify(errno=RET.DATAERR, errmsg="未上传图片")

        # 获取特定的文件名
        file_suffix = os.path.splitext(image_file.filename)
        file_name = 'avatar' + str(user.user_id) + file_suffix[1]

        # 获取项目路径
        cur_path = os.path.abspath(os.path.dirname(__file__))
        root_path = cur_path[:cur_path.find("ihome_py\\") + len("ihome_py\\")]  # 获取myProject，也就是项目的根路径

        path = root_path + "ihome/static/images/avatar/"
        file_path = path + file_name
        # 保存头像
        image_file.save(file_path)

        # 更新用户信息
        user.avatar_url = "/static/images/avatar/" + file_name
        user.username = username
        db.session.commit()

        # 更新session
        session["username"] = username

        return jsonify(errno=RET.OK, errmsg="成功")
