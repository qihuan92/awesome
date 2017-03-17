package com.qihuan.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * user
 * Created by Qi on 2017/3/14.
 */
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @Column(name = "id", nullable = false, length = 32, unique = true)
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @GeneratedValue(generator = "generator")
    private String id;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    private String password;

    private String nickName;

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
