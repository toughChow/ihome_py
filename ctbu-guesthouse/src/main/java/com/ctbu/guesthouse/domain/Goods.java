package com.ctbu.guesthouse.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gh_goods")
public class Goods {

    @Id
    @Column(name = "goods_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String goodsName;

    @Column(length = 18)
    private String goodsNumber;

    @Column(length = 11)
    private String goodsPrice;

    @Column(length = 11)
    private String goodsPurchaseprice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPurchaseprice() {
        return goodsPurchaseprice;
    }

    public void setGoodsPurchaseprice(String goodsPurchaseprice) {
        this.goodsPurchaseprice = goodsPurchaseprice;
    }
}
