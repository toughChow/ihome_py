# coding utf-8
from datetime import timedelta


class Config(object):
    """公共配置信息"""


class DevelopmentConfig(Config):
    """开发环境的配置信息"""

    # 数据库
    USERNAME = 'root'
    PASSWORD = 'root'
    HOST = '127.0.0.1'
    PORT = '3306'
    DATABASE = 'db_ihome'

    SQLALCHEMY_DATABASE_URI = "mysql+pymysql://{}:{}@{}:{}/{}".format(USERNAME, PASSWORD, HOST, PORT, DATABASE)
    SQLALCHEMY_TRACK_MODIFICATIONS = True

    DEBUG = True


class ProductionConfig(Config):
    """生产环境的配置信息"""

    # 数据库
    USERNAME = 'ihomepy_f'
    PASSWORD = 'ihome2019'
    HOST = 'lxwc9k5r.2375.dnstoo.com'
    PORT = '5510'
    DATABASE = 'ihomepy'

    SQLALCHEMY_DATABASE_URI = "mysql+pymysql://{}:{}@{}:{}/{}".format(USERNAME, PASSWORD, HOST, PORT, DATABASE)
    SQLALCHEMY_TRACK_MODIFICATIONS = True

    # session
    # app.secret_key = 'super secret key'
    # SESSION_TYPE = 'filesystem'
    # PERMANENT_SESSION_LIFETIME = timedelta(days=7)  # 设置session的保存时间
    # SECRET_KEY = '123456'  # 设置为24位的字符,每次运行服务器都是不同的，所以服务器启动一次上次的session就清除。


config_map = {
    "develop": DevelopmentConfig,
    "product": ProductionConfig
}
