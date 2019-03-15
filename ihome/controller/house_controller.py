# -*- coding:utf8 -*-


from flask import request, jsonify, render_template
from . import api,default


@default.route("/house/own", methods=['GET'])
def house_own():
    return render_template('house/own.html')
