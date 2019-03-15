# coding utf-8


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



config_map = {
    "develop": DevelopmentConfig,
    "product": ProductionConfig
}