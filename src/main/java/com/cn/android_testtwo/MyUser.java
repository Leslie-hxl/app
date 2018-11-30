package com.cn.android_testtwo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class MyUser {

    @Id(autoincrement = true)
    private Long id;
    private String songName;
    private Integer songId;

    @Generated(hash = 102265432)
    public MyUser(Long id, String songName, Integer songId) {
        this.id = id;
        this.songName = songName;
        this.songId = songId;
    }

    @Generated(hash = 623865568)
    public MyUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSongId() {
        return this.songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

}
