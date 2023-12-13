package com.comix.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof User)
            result = username == ((User) other).username && password == ((User) other).password;
        return result;
    }
}
