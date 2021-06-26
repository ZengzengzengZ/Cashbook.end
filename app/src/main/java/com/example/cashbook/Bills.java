package com.example.cashbook;

public class Bills {
    private String costTitle;
    private String costDate;
    private String costMoney;
    private String costType;
    private int id;

    public  Bills() {
    }

    public Bills(String costTitle , String costDate,String  costMoney, String costType) {
        this.costTitle = costTitle;
        this.costDate = costDate;
        this.costMoney = costMoney;
        this.costType = costType;
        id = 0;
    }



    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }
    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getCostType() {
        return costType;
    }
    public void setCostType(String costType){
        this.costType=costType;
    }

}
