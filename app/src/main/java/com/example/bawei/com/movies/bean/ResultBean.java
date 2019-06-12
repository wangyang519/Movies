package com.example.bawei.com.movies.bean;

public class ResultBean {

    public int userId;
    public String sessionId;
    public UserInfo userInfo;

    @Override
    public String toString() {
        return "ResultBean{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
