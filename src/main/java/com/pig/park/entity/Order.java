package com.pig.park.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;     //订单ID

    private String rentID;    //租主ID

    private String tenantID;  //租户ID

    private String parkArea;  //停车位区域

    private String parkBuild; //停车位楼号

    private String parkNum;   //停车位号码

    private int price;        //订单花费

    private Date releaseDate; //租主发布的日期

    private Date confirmDate; //租户下单的日期

    private Date orderDate;  //车位使用的日期

    private int orderState; //0,1,2,3分别为过期，发布中，租借中，完成

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getParkArea() {
        return parkArea;
    }

    public void setParkArea(String parkArea) {
        this.parkArea = parkArea;
    }

    public String getParkBuild() {
        return parkBuild;
    }

    public void setParkBuild(String parkBuild) {
        this.parkBuild = parkBuild;
    }

    public String getParkNum() {
        return parkNum;
    }

    public void setParkNum(String parkNum) {
        this.parkNum = parkNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
}
