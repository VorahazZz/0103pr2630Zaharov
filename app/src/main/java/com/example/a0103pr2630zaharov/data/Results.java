package com.example.a0103pr2630zaharov.data;

public class Results {
    private int id;
    private String expression;
    private String res;

    public Results(String expression, String res) {
        this.expression = expression;
        this.res = res;
    }
    public Results(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
