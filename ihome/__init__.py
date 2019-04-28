# -*- coding:utf8 -*-
from datetime import timedelta

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
    # config_class = config_map.get(config_name)
    # app.config.from_object(config_class)
    # app.register_error_handler(404, page_not_found)

    # db
    USERNAME = 'ihomepy_f'
    PASSWORD = 'ihome2019'
    HOST = 'lxwc9k5r.2375.dnstoo.com'
    PORT = '5510'
    DATABASE = 'ihomepy'
    app.config['SQLALCHEMY_DATABASE_URI'] = "mysql+pymysql://{}:{}@{}:{}/{}".format(USERNAME, PASSWORD, HOST, PORT,
                                                                                    DATABASE)
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

    app.config['UPLOAD_FOLDER'] = '/static/avatar/'

    # session
    app.config['SESSION_TYPE'] = 'filesystem'
    app.config['PERMANENT_SESSION_LIFETIME'] = timedelta(days=7)  # 设置session的保存时间
    app.config['SECRET_KEY'] = '123456'  # 设置为24位的字符,每次运行服务器都是不同的，所以服务器启动一次上次的session就清除。

    # 使用app初始化db
    db.init_app(app)

    Session(app)

    # 注册蓝图
    from ihome import controller
    app.register_blueprint(controller.api, url_prefix="/api/v1.0")

    app.register_blueprint(controller.default)

    return app
