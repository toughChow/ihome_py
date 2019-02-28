# coding utf-8

import redis


class Config(object):
    """配置信息"""

    SECRET_KEY = "ASDFasdf*1234"

    # 数据源
    SQLALCHEMY_DATABASE_URI = "mysql://root/mysql@127.0.0.1:3306/ihome"
    SQLALCHEMY_TRACK_MODIFICATIONS = True

    # redid
    REDIS_HOST = "127.0.0.1"
    REDIS_PORT = 6379

    # flask-session配置
    SESSION_TYPE = "redis"
    SESSION_REDIS = redis.StrictRedis(host=REDIS_HOST, port=REDIS_PORT)
    SESSION_USE_SIGNER = True  # 对Cookie中的session_id信息进行隐藏
    PERMANENT_SESSION_LIFETIME = 86400  # session有效期 单位秒


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
