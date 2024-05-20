package com.danhlee.osahaneat.Entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class KeyMenuRestaurant implements Serializable {
    @Column(name = "cate_id")
    private int cateId;

    @Column(name = "red_id")
    private int redId;

    public KeyMenuRestaurant() {
    }

    public KeyMenuRestaurant(int cateId, int redId) {
        this.cateId = cateId;
        this.redId = redId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public int getRedId() {
        return redId;
    }

    public void setRedId(int redId) {
        this.redId = redId;
    }
}
