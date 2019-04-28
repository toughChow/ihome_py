# -*- coding:utf8 -*-
from datetime import datetime

from datetime import datetime

from ihome import db  # db是app/__init__.py生成的关联后的SQLAlchemy实例


class BaseModel(object):
    """模型基类，为每个模型补充创建时间与更新时间"""

    create_time = db.Column(db.DateTime, default=datetime.now)  # 记录的创建时间
    update_time = db.Column(db.DateTime, default=datetime.now, onupdate=datetime.now)  # 记录的更新时间


class Area(db.Model):
    __tablename__ = "t_area"
    __table_args__ = {"useexisting": True}

    area_id = db.Column(db.Integer, primary_key=True)
    name = db.Column("name", db.String)

    def to_dict(self):
        """将对象转换为字典"""
        d = {
            "area_id": self.area_id,
            "name": self.name
        }
        return d

    def __repr__(self):
        return '<Area %r>' % self.name


# 房屋表
class House(BaseModel, db.Model):
    __tablename__ = "t_house"
    __table_args__ = {"useexisting": True}

    house_id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String)
    address = db.Column(db.String)
    depict = db.Column(db.String)
    price = db.Column(db.Float)
    floor = db.Column(db.Integer)
    user_id = db.Column(db.Integer)
    area_id = db.Column(db.Integer)
    community_id = db.Column(db.Integer)
    type_id = db.Column(db.Integer)
    kind = db.Column(db.Integer)
    house_img = db.Column(db.String)
    house_status = db.Column(db.Integer)

    def to_house_detail_dict(self, area, tag, image, community):
        d = {
            "house_id": self.house_id,
            "user_id": self.user_id,
            "title": self.title,
            "address": self.address,
            "depict": self.depict,
            "price": self.price,
            "floor": self.floor,
            "area": area,
            "tag": tag,
            "image": image,
            "community": community,
        }
        return d

    def to_house_list_dict(self):
        d = {
            "house_id": self.house_id,
            "price": self.price,
            "kind": self.kind,
            "title": self.title,
            "house_status": self.house_status,
            "house_img": self.house_img
        }
        return d


# 户型表
class HomeType(db.Model):
    __tablename__ = "t_house_type"
    __table_args__ = {"useexisting": True}

    type_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)


# 房屋标签表
class HouseTag(db.Model):
    __tablename__ = "t_house_tag"
    __table_args__ = {"useexisting": True}

    tag_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    content = db.Column(db.String)

    def to_tag_dict(self):
        if self:
            d = {
                "tag_id": self.tag_id,
                "content": self.content
            }
            return d
        return None


# 房屋图片表
class HouseImage(db.Model):
    __tablename__ = __ = "t_house_image"
    __table_args__ = {"useexisting": True}

    image_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    url = db.Column(db.String)

    def to_image_dict(self):
        d = {
            "image_id": self.image_id,
            "url": self.url
        }
        return d


# 房屋设备表
class Facility(db.Model):
    __tablename__ = "t_facility"
    __table_args__ = {"useexisting": True}

    facility_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    icon = db.Column(db.String)


# 房屋与设备中间表
class HouseFacility(db.Model):
    __tablename__ = "t_house_facility"
    __table_args__ = {"useexisting": True}

    id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    facility_id = db.Column(db.Integer)


# 小区表
class Community(db.Model):
    __tablename__ = "t_community"
    __table_args__ = {"useexisting": True}

    community_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    building_time = db.Column(db.String)
    safe = db.Column(db.Float)
    quiet = db.Column(db.Float)
    green = db.Column(db.Integer)
    address = db.Column(db.String)
    developers = db.Column(db.String)
    url = db.Column(db.String)

    def to_com_dict(self):
        if self:
            d = {
                "community_id": self.community_id,
                "name": self.name
            }
            return d
        return None

    def to_com_detail_dict(self):
        if self:
            d = {
                "community_id": self.community_id,
                "name": self.name,
                "building_time": self.building_time,
                "safe": self.safe,
                "quiet": self.quiet,
                "green": self.green,
                "address": self.address,
                "developers": self.developers,
                "url": self.url
            }
            return d
        return None


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

#
# class Facility(db.Model):
#     """房屋类型"""
#     __tablename__ = "t_facility"
#     __table_args__ = {"useexisting": True}
#
#     facility_id = db.Column(db.Integer, primary_key=True)
#     name = db.Column(db.String(50))
#     icon = db.Column(db.String(50))


class Tag(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_tag"
    __table_args__ = {"useexisting": True}

    tag_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    content = db.Column(db.String(11))

#
# class HouseFacility(db.Model):
#     """房屋类型"""
#     __tablename__ = "t_house_facility"
#     __table_args__ = {"useexisting": True}
#
#     id = db.Column(db.Integer, primary_key=True)
#     house_id = db.Column(db.Integer)
#     facility_id = db.Column(db.Integer)


class HouseImg(db.Model):
    """房屋类型"""
    __tablename__ = "t_house_image"
    __table_args__ = {"useexisting": True}

    image_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    url = db.Column(db.String(255))


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


# 房屋表
class House(BaseModel, db.Model):
    __tablename__ = "t_house"
    __table_args__ = {"useexisting": True}

    house_id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String)
    address = db.Column(db.String)
    depict = db.Column(db.String)
    price = db.Column(db.Float)
    floor = db.Column(db.Integer)
    user_id = db.Column(db.Integer)
    area_id = db.Column(db.Integer)
    community_id = db.Column(db.Integer)
    type_id = db.Column(db.Integer)
    kind = db.Column(db.Integer)
    house_img = db.Column(db.String)
    house_status = db.Column(db.Integer)

    def to_house_detail_dict(self, area, tag, image, community):
        d = {
            "house_id": self.house_id,
            "user_id": self.user_id,
            "title": self.title,
            "address": self.address,
            "depict": self.depict,
            "price": self.price,
            "floor": self.floor,
            "area": area,
            "tag": tag,
            "image": image,
            "community": community,
        }
        return d

    def to_house_list_dict(self):
        d = {
            "house_id": self.house_id,
            "price": self.price,
            "kind": self.kind,
            "title": self.title,
            "house_status": self.house_status,
            "house_img": self.house_img
        }
        return d


# 户型表
class HomeType(db.Model):
    __tablename__ = "t_house_type"
    __table_args__ = {"useexisting": True}

    type_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)


# 房屋标签表
class HouseTag(db.Model):
    __tablename__ = "t_house_tag"
    __table_args__ = {"useexisting": True}

    tag_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    content = db.Column(db.String)

    def to_tag_dict(self):
        if self:
            d = {
                "tag_id": self.tag_id,
                "content": self.content
            }
            return d
        return None


# 房屋图片表
class HouseImage(db.Model):
    __tablename__ = __ = "t_house_image"
    __table_args__ = {"useexisting": True}

    image_id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    url = db.Column(db.String)

    def to_image_dict(self):
        d = {
            "image_id": self.image_id,
            "url": self.url
        }
        return d


# 房屋设备表
class Facility(db.Model):
    __tablename__ = "t_facility"
    __table_args__ = {"useexisting": True}

    facility_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    icon = db.Column(db.String)


# 房屋与设备中间表
class HouseFacility(db.Model):
    __tablename__ = "t_house_facility"
    __table_args__ = {"useexisting": True}

    id = db.Column(db.Integer, primary_key=True)
    house_id = db.Column(db.Integer)
    facility_id = db.Column(db.Integer)


# 小区表
class Community(db.Model):
    __tablename__ = "t_community"
    __table_args__ = {"useexisting": True}

    community_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    building_time = db.Column(db.String)
    safe = db.Column(db.Float)
    quiet = db.Column(db.Float)
    green = db.Column(db.Integer)
    address = db.Column(db.String)
    developers = db.Column(db.String)
    url = db.Column(db.String)

    def to_com_dict(self):
        if self:
            d = {
                "community_id": self.community_id,
                "name": self.name
            }
            return d
        return None

    def to_com_detail_dict(self):
        if self:
            d = {
                "community_id": self.community_id,
                "name": self.name,
                "building_time": self.building_time,
                "safe": self.safe,
                "quiet": self.quiet,
                "green": self.green,
                "address": self.address,
                "developers": self.developers,
                "url": self.url
            }
            return d
        return None
