package com.ctbu.guesthouse.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gh_log")
public class SysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private Long roomId; // 房间编号

    @Column
    private Float price; // 房间单价

    @Column
    private Float guestPrice; // 实交金钱

    @Column(length = 64)
    private String guestName; // 客人姓名

    @Column(length = 18)
    private String guestId; // 客人身份证

    @Column(length = 11)
    private String startTime; // 入住日期

    @Column(length = 11)
    private String endTime; // 退住日期

    @Column
    private Integer guestTime; // 住房天数

    @Column(length = 55)
    private String mdcValue;

    @Column
    private Integer isDeleted;

    @Column
    @CreationTimestamp
    private Date ctTime;

    public SysLog() {
    }

    public SysLog(Long roomId, Float price, Float guestPrice, String guestName, String guestId, String startTime, String endTime, Integer guestTime) {
        this.roomId = roomId;
        this.price = price;
        this.guestPrice = guestPrice;
        this.guestName = guestName;
        this.guestId = guestId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.guestTime = guestTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public SysLog setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRoomId() {
        return roomId;
    }

    public SysLog setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public SysLog setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Float getGuestPrice() {
        return guestPrice;
    }

    public SysLog setGuestPrice(Float guestPrice) {
        this.guestPrice = guestPrice;
        return this;
    }

    public String getGuestName() {
        return guestName;
    }

    public SysLog setGuestName(String guestName) {
        this.guestName = guestName;
        return this;
    }

    public String getGuestId() {
        return guestId;
    }

    public SysLog setGuestId(String guestId) {
        this.guestId = guestId;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public SysLog setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public SysLog setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getGuestTime() {
        return guestTime;
    }

    public SysLog setGuestTime(Integer guestTime) {
        this.guestTime = guestTime;
        return this;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }

    public String getMdcValue() {
        return mdcValue;
    }

    public void setMdcValue(String mdcValue) {
        this.mdcValue = mdcValue;
    }
}
