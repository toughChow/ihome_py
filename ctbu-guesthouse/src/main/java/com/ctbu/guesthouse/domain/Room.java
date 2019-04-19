package com.ctbu.guesthouse.domain;

import javax.persistence.*;

@Entity
@Table(name = "gh_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String roomCode; // 房间编号

    @Column(length = 32)
    private String roomName; // 房间名字

    @Column
    private Float price; // 房间价格

    @Column
    private Float requiredPrice; // 需交金钱

    @Column
    private Float givedPrice; // 实交金钱

    @Column(length = 64)
    private String guestName; // 客人姓名

    @Column(length = 18)
    private String guestId; // 客人身份证

    @Column(length = 11)
    private String startTime; // 入住日期

    @Column(length = 11)
    private String endTime; // 退住日期

    @Column
    private Integer guestTime; // 退住日期

    @Column(length = 1)
    private Integer status; // 状态 0 未入住 1 已入住 2 警告

    @Column(length = 11)
    private String remark; // 备注：脏 空调坏（待维修） 等等

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="pos_id", insertable = false,updatable = false)
    private Position position; // 位置

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", insertable = false,updatable = false)
    private User user; // 管理员

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Room setRoomCode(String roomCode) {
        this.roomCode = roomCode;
        return this;
    }

    public String getRoomName() {
        return roomName;
    }

    public Room setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public Room setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Float getRequiredPrice() {
        return requiredPrice;
    }

    public Room setRequiredPrice(Float requiredPrice) {
        this.requiredPrice = requiredPrice;
        return this;
    }

    public Float getGivedPrice() {
        return givedPrice;
    }

    public Room setGivedPrice(Float givedPrice) {
        this.givedPrice = givedPrice;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Room setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Room setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public Room setPosition(Position position) {
        this.position = position;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Room setUser(User user) {
        this.user = user;
        return this;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room setGuestName(String guestName) {
        this.guestName = guestName;
        return this;
    }

    public String getGuestId() {
        return guestId;
    }

    public Room setGuestId(String guestId) {
        this.guestId = guestId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Room setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Room setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getGuestTime() {
        return guestTime;
    }

    public Room setGuestTime(Integer guestTime) {
        this.guestTime = guestTime;
        return this;
    }
}
