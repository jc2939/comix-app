package com.comix.persistence;

import java.io.IOException;

import com.comix.model.user.User;

public interface UserDao {
    void setPersonalCollectionDao(PersonalCollectionDao collection);

    User login(String username, String password);

    User createAccount(String username, String password) throws IOException;

}
