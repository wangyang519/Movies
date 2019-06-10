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
    public long userId;
    public String sessionId;
    @Generated(hash = 1162964473)
    public UserInfo(String nickName, String phone, long birthday, int sex,
            long lastLoginTime, String headPic, long userId, String sessionId) {
        this.nickName = nickName;
        this.phone = phone;
        this.birthday = birthday;
        this.sex = sex;
        this.lastLoginTime = lastLoginTime;
        this.headPic = headPic;
        this.userId = userId;
        this.sessionId = sessionId;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
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
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
