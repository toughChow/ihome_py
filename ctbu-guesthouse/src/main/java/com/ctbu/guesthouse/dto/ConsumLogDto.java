package com.ctbu.guesthouse.dto;

import java.io.Serializable;
import java.util.Date;

public class ConsumLogDto implements Serializable{
    private Long id;
    private String goodsId;
    private String goodsNumber;
    private String consumePrice;
    private String mdcValue;
    private Date ctTime;

    private String roomCode;
    private String goodsName;

    public String getGoodsName() {
        return goodsName;
    }

    public ConsumLogDto setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public ConsumLogDto setRoomCode(String roomCode) {
        this.roomCode = roomCode;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ConsumLogDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(String consumePrice) {
        this.consumePrice = consumePrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public ConsumLogDto setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public ConsumLogDto setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
        return this;
    }

    public String getMdcValue() {
        return mdcValue;
    }

    public ConsumLogDto setMdcValue(String mdcValue) {
        this.mdcValue = mdcValue;
        return this;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public ConsumLogDto setCtTime(Date ctTime) {
        this.ctTime = ctTime;
        return this;
    }
}
