package com.example.bawei.com.movies.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {

    public String nickName;
    public String phone;
    public long birthday;
    public int sex;
    public long lastLoginTime;
    public String headPic;
    @Id
    public long id;
    public String sessionId;

    int status;


    @Generated(hash = 231273328)
    public UserInfo(String nickName, String phone, long birthday, int sex,
            long lastLoginTime, String headPic, long id, String sessionId,
            int status) {
        this.nickName = nickName;
        this.phone = phone;
        this.birthday = birthday;
        this.sex = sex;
        this.lastLoginTime = lastLoginTime;
        this.headPic = headPic;
        this.id = id;
        this.sessionId = sessionId;
        this.status = status;
    }


    @Generated(hash = 1279772520)
    public UserInfo() {
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", lastLoginTime=" + lastLoginTime +
                ", headPic='" + headPic + '\'' +
                ", id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", status=" + status +
                '}';
    }

    public String getNickName() {
        return this.nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getPhone() {
        return this.phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public long getBirthday() {
        return this.birthday;
    }


    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }


    public int getSex() {
        return this.sex;
    }


    public void setSex(int sex) {
        this.sex = sex;
    }


    public long getLastLoginTime() {
        return this.lastLoginTime;
    }


    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public String getHeadPic() {
        return this.headPic;
    }


    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getSessionId() {
        return this.sessionId;
    }


    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public int getStatus() {
        return this.status;
    }


    public void setStatus(int status) {
        this.status = status;
    }
}
