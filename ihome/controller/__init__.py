# -*- coding:utf8 -*-


from flask import Blueprint

# 创建蓝图对象
api = Blueprint("controller", __name__)
default = Blueprint("", __name__)

# 导入蓝图的视图
# 一定要保证视图导入要在蓝图注册之后，否则会产生依赖循环导入的错误。
from . import index_controller, area_controller
