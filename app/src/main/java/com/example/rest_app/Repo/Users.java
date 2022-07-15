package com.example.rest_app.Repo;

import org.json.JSONException;
import org.json.JSONObject;

public class Users {
    //attributes
    private String name,username, email, phone, website, _id;



    public Users(String name, String username, String email, String phone, String website) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public Users(JSONObject object){
        try {
            this.name = object.getString("name");
            this.username = object.getString("username");
            this.email = object.getString("email");
            this.phone = object.getString("phone");
            this.website = object.getString("website");
            this._id = object.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getUsername(){return username;}

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public JSONObject toJson(){
        JSONObject tour = new JSONObject();
        try {
            tour.put("name",this.name);
            tour.put("username",this.username);
            tour.put("email",this.email);
            tour.put("phone",this.phone);
            tour.put("website",this.website);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tour;
    }
}
