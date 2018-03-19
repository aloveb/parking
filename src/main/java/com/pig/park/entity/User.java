package com.pig.park.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User {
    @Id
    private String userId;   //用户ID
    private String userName; //用户昵称
    private String cardId;   //一卡通ID
    private String plateNum; //车牌号

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

}
