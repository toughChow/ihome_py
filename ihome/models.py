# -*- coding:utf8 -*-


from ihome import db  # db是app/__init__.py生成的关联后的SQLAlchemy实例


class Area(db.Model):
    __tablename__ = "t_area"

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
