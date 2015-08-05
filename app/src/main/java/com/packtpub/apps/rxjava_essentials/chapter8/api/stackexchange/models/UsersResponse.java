package com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {

    public List<User> getUsers() {
        return users;
    }

    @SerializedName("items")
    private List<User> users;
}
