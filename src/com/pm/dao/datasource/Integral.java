package com.pm.dao.datasource;

import javax.persistence.*;

@Entity
@Table(name = "integral", schema = "pm")
public class Integral {
    private int id ;
    private int integral;
    private int userId;
    @Basic
    @Column(name = "integral", nullable = false)
    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
