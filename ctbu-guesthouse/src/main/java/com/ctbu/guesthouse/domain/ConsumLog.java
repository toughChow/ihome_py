package com.ctbu.guesthouse.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gh_consume_log")
public class ConsumLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String goodsId;

    @Column(length = 18)
    private String goodsNumber;

    @Column(length = 18)
    private String consumePrice;

    @Column(length = 55)
    private String mdcValue;

    @Column
    private Integer isDeleted; // 0 false 1 true

    @Column
    @CreationTimestamp
    private Date ctTime;

    public ConsumLog() {
    }

    public ConsumLog(String goodsId, String goodsNum, String mdcValue) {
        this.goodsId = goodsId;
        this.goodsNumber = goodsNum;
        this.mdcValue = mdcValue;
    }

    public ConsumLog(String goodsId, String goodsNum, String consumePrice, String mdcValue) {
        this.goodsId = goodsId;
        this.goodsNumber = goodsNum;
        this.consumePrice = consumePrice;
        this.mdcValue = mdcValue;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(String consumePrice) {
        this.consumePrice = consumePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getMdcValue() {
        return mdcValue;
    }

    public void setMdcValue(String mdcValue) {
        this.mdcValue = mdcValue;
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }
}
