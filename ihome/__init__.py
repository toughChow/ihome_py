# coding utf-8

from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_session import Session
from flask_wtf import CSRFProtect
from config import config_map
from ihome import api_1_0

# 数据库
db = SQLAlchemy()


# 工厂模式
def create_app(config_name):
    """
    创建flask的应用对象
    :param config_name: str 配置模式的名字 ("dev", "prod")
    :return:
    """
    app = Flask(__name__)

    # 根据配置模式的名字获取参数的类
    config_class = config_map.get(config_name)
    app.config.from_object(config_class)

    # 使用app初始化db
    db.init_app(app)

    # 利用 flask-session, 讲session数据保存到redis中
    Session(app)

    # 为flask 补充csrf
    CSRFProtect(app)

    # 注册蓝图
    app.register_blueprint(api_1_0.api, url_prefix="/api/v1.0")

    return app
