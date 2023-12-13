package com.comix.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.comix.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserFileDao implements UserDao {
    private Map<String, User> users;
    private ObjectMapper objectMapper;
    private static final String FILENAME = "data/users.json";
    private PersonalCollectionDao personalDao;

    public UserFileDao(ObjectMapper objectMapper, PersonalCollectionDao personalDao) throws IOException {
        this.objectMapper = objectMapper;
        this.personalDao = personalDao;
        load();
    }

    private void load() throws IOException {
        users = new TreeMap<String, User>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(FILENAME), User[].class);

        // Add each user to the tree map
        for (User user : userArray) {
            // if the username isn't already in the tree map, add it
            if (!users.containsKey(user.getUsername())) {
                users.put(user.getUsername(), user);
            }
        }
    }

    public ArrayList<User> getUsersArray() {
        ArrayList<User> usersArray = new ArrayList<>();
        usersArray.addAll(users.values());

        return usersArray;
    }

    public User getUser(String username) {
        synchronized (users) {
            if (users.containsKey(username))
                return users.get(username);
            else
                return null;
        }
    }

    private void save() throws IOException {
        ArrayList<User> usersArray = getUsersArray();
        objectMapper.writeValue(new File(FILENAME), usersArray);
    }

    @Override
    public void setPersonalCollectionDao(PersonalCollectionDao collection) {
        this.personalDao = collection;
    }

    @Override
    public User login(String username, String password) {
        synchronized (users) {
            for (User user : users.values()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User createAccount(String username, String password) throws IOException {
        synchronized (users) {
            for (User user : users.values()) {
                if (user.getUsername().equals(username))
                    return null;
            }
            User newUser = new User(username, password);
            users.put(newUser.getUsername(), newUser);
            personalDao.createCollection(newUser.getUsername());
            System.out.println("Created new user: " + newUser.getUsername());
            save();
            return newUser;
        }
    }
}
