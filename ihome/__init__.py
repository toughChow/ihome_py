# -*- coding:utf8 -*-


from flask import Flask, render_template
from flask_sqlalchemy import SQLAlchemy
from flask_session import Session
from config import config_map

# 数据库
db = SQLAlchemy()


def page_not_found(e):
    print(e)
    return render_template('error/404.html'), 404


def create_app(config_name):
    """
    创建flask的应用对象
    :param config_name: str  配置模式的模式的名字 （"develop",  "product"）
    :return:
    """
    app = Flask(__name__)

    # 根据配置模式的名字获取配置参数的类
    config_class = config_map.get(config_name)
    app.config.from_object(config_class)
    app.register_error_handler(404, page_not_found)

    # 使用app初始化db
    db.init_app(app)

    Session(app)

    # 注册蓝图
    from ihome import controller
    app.register_blueprint(controller.api, url_prefix="/api/v1.0")
    app.register_blueprint(controller.default)

    return app

