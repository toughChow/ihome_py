# coding utf-8


class Config(object):
    """配置信息"""

    SECRET_KEY = "iyuyun"

    # 数据源
    SQLALCHEMY_DATABASE_URI = "mysql://root/mysql@127.0.0.1:3306/ihome"
    SQLALCHEMY_TRACK_MODIFICATIONS = True


class DevelopmentConfig(Config):
    """开发环境"""
    DEBUG = True


class ProductConfig(Config):
    """生产环境"""
    pass


config_map = {
    "dev": DevelopmentConfig,
    "prod": ProductConfig
}
