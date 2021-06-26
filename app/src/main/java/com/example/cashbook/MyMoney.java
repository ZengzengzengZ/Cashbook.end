package com.example.cashbook;

public class MyMoney {
    private double income;
    private double cost;
    static private int Type =0;
    MyMoney()
    {
        this.income = 0;
        this.cost = 0;
    }
    public void setType(int type)
    {
        this.Type = type;
    }
    public int getType()
    {
        return this.Type;
    }
    public void setIncome(double income)
    {
        this.income += income;
    }
    public void setCost(double cost)
    {
        this.cost += cost;
    }
    public double getIncome()
    {
        return this.income;
    }
    public double getCost()
    {
        return this.cost;
    }
}
