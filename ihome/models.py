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


class House(db.Model):
    """房屋"""
    __tablename__ = "t_house"
    __table_args__ = {"useexisting": True}

    house_id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer)
    title = db.Column(db.String(255))
    area_id = db.Column(db.Integer)
    depict = db.Column(db.String(255), nullable=True)
    price = db.Column(db.Integer)
    type_id = db.Column(db.Integer)
    floor = db.Column(db.Integer, nullable=True)
    address = db.Column(db.String(50))
    community_id = db.Column(db.Integer)
    kind = db.Column(db.Integer)
    house_img = db.Column(db.String(50))
    house_status = db.Column(db.Integer)

    def to_house_dict(self):
        """将房屋信息转换为字典数据"""
        house_dict = {
            "house_id": self.id,
            "title": self.title,
            "area_id": self.area_id,
            "depict": self.depict,
            "price": self.price,
            "type_id": self.type_id,
            "floor": self.floor,
            "address": self.address,
            "community_id": self.community_id,
            "kind": self.kind
        }
        return house_dict


class Community(db.Model):
    """小区"""
    __tablename__ = "t_community"
    __table_args__ = {"useexisting": True}

    community_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50))
    building_time = db.Column(db.String(50))
    safe = db.Column(db.Float)
    quiet = db.Column(db.Float)
    address = db.Column(db.String(255))
    developers = db.Column(db.String(255))
    green = db.Column(db.Integer)

    def to_dict(self):
        """将对象转换为字典"""
        d = {
            "aid": self.community_id,
            "aname": self.name
        }
        return d


class HouseType(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_type"
    __table_args__ = {"useexisting": True}

    type_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(10))

    def to_dict(self):
        """将对象转换为字典"""
        d = {
            "aid": self.type_id,
            "aname": self.name
        }
        return d


class Facility(db.Model):
    """房屋类型"""
    __tablename__ = "t_facility"
    __table_args__ = {"useexisting": True}

    facility_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50))
    icon = db.Column(db.String(50))


class Tag(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_tag"
    __table_args__ = {"useexisting": True}

    tag_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    content = db.Column(db.String(11))


class HouseFacility(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_facility"
    __table_args__ = {"useexisting": True}

    id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    facility_id = db.Column(db.Integer)


class HouseImg(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_image"
    __table_args__ = {"useexisting": True}

    image_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    url = db.Column(db.String(255))


