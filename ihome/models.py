# -*- coding:utf8 -*-

from ihome import db  # db是app/__init__.py生成的关联后的SQLAlchemy实例


class Area(db.Model):
    __tablename__ = "t_area"
    __table_args__ = {"useexisting": True}

    id = db.Column("area_id", db.Integer, primary_key=True)
    name = db.Column("name", db.String)

    def to_dict(self):
        """将对象转换为字典"""
        d = {
            "aid": self.id,
            "aname": self.name
        }
        return d

    def __repr__(self):
        return '<Area %r>' % self.name


class User(db.Model):
    """用户"""

    __tablename__ = "t_user"
    __table_args__ = {"useexisting": True}

    user_id = db.Column(db.Integer, primary_key=True)
    mobile = db.Column(db.String(11), unique=True, nullable=True)
    username = db.Column(db.String(32), unique=True, nullable=True)
    password = db.Column(db.String(32), nullable=True, )
    real_name = db.Column(db.String(32))
    real_id_card = db.Column(db.String(18))
    avatar_url = db.Column(db.String(128))
