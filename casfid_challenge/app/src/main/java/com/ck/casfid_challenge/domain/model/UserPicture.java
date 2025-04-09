package com.ck.casfid_challenge.domain.model;

import java.io.Serializable;

public class UserPicture implements Serializable {
    public String large;
    public String thumbnail;

    public UserPicture() {
    }

    public UserPicture(String large, String thumbnail) {
        this.large = large;
        this.thumbnail = thumbnail;
    }
}
