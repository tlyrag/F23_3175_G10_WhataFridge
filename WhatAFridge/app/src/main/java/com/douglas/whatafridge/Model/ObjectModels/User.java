package com.douglas.whatafridge.Model.ObjectModels;

public class User {

    private String userId;
    private String password;
    private String userName;
    private String age;
    private String weight;
    private String height;
    private String gluten;
    private int vegeType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGluten() {
        return gluten;
    }

    public void setGluten(String gluten) {
        this.gluten = gluten;
    }

    public int getVegeType() {
        return vegeType;
    }

    public void setVegeType(int vegeType) {
        this.vegeType = vegeType;
    }
}
