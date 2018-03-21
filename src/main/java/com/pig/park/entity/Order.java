package com.pig.park.entity;


import javax.persistence.*;

@Entity
@Table( name="P_order" )
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderid;     //订单ID

    private String rentId;    //租主ID

    private String tenantId;  //租户ID

    private String parkArea;  //停车位区域

    private String parkBuild; //停车位楼号

    private String parkNum;   //停车位号码

    private int price;        //订单花费

    private String releaseDate; //租主发布的日期

    private String confirmDate; //租户下单的日期

    private String orderDate;  //车位使用的日期

    private int orderState = 1; //0,1,2,3分别为过期，发布中，租借中，完成

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOrderId() {
        return orderid;
    }

    public void setOrderId(Long orderid) {
        this.orderid = orderid;
    }
}
