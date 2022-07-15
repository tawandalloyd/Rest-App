package com.example.rest_app.Repo;

import com.example.rest_app.utils.STATUS;

import java.util.ArrayList;

public interface getUserResults {

    void allUsers(boolean wasSuccessful, ArrayList<Users> tours, STATUS status);
    void deleteUsers(boolean wasSuccessful,String id, STATUS status);
    void CreateUsers(boolean wasSuccessful, ArrayList<Users> tours, STATUS status);

}
