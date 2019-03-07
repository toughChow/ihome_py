# -*- coding:utf8 -*-


from flask import render_template, Blueprint
from . import api, default


@default.route('/', methods=['GET'])
def index():
    print("coming")
    return render_template("index.html")


# @default.errorhandler(404)
# def page_not_found(e):
#     print(e)
#     return render_template('404.html'), 404
