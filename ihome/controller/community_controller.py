# -*- coding:utf8 -*-


from flask import render_template, request
from . import api, default
from ihome.models import Community


# 查询小区信息
@default.route('/community/detail', methods=['GET'])
def community_detail():
    cid = request.args.get("id")  # 获取小区id
    com_query = Community.query.filter_by(community_id=cid).first()
    com_dict = com_query.to_com_detail_dict()
    return render_template("community/detail.html", com=com_dict)
