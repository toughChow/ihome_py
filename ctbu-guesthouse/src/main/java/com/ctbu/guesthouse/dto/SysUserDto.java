package com.ctbu.guesthouse.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SysUserDto implements Serializable {

    private List<Integer> months;
    private List<Float> monthlUserMoney;

    public List<Integer> getMonths() {
        return months;
    }

    public SysUserDto setMonths(List<Integer> months) {
        this.months = months;
        return this;
    }

    public List<Float> getMonthlUserMoney() {
        return monthlUserMoney;
    }

    public SysUserDto setMonthlUserMoney(List<Float> monthlUserMoney) {
        this.monthlUserMoney = monthlUserMoney;
        return this;
    }
}
