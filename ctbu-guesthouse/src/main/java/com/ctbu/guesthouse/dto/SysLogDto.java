package com.ctbu.guesthouse.dto;

import java.io.Serializable;
import java.util.Date;

public class SysLogDto implements Serializable{
    private Long id;
    private Long roomId; // 房间编号
    private String roomCode;
    private Float price; // 房间单价
    private Float guestPrice; // 实交金钱
    private String guestName; // 客人姓名
    private String guestId; // 客人身份证
    private String startTime; // 入住日期
    private String endTime; // 退住日期
    private Integer guestTime; // 住房天数
    private Date ctTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getGuestPrice() {
        return guestPrice;
    }

    public void setGuestPrice(Float guestPrice) {
        this.guestPrice = guestPrice;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getGuestTime() {
        return guestTime;
    }

    public void setGuestTime(Integer guestTime) {
        this.guestTime = guestTime;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }
}
