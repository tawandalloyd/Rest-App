package com.example.rest_app.Repo;

import org.json.JSONException;
import org.json.JSONObject;

public class Users {
    //attr
    private String name, duration, maxGroupSize, difficulty, _id;



    public Users(String name, String duration, String maxGroupSize, String difficulty) {
        this.name = name;
        this.duration = duration;
        this.maxGroupSize = maxGroupSize;
        this.difficulty = difficulty;
    }

    public Users(JSONObject object){
        try {
            this.name = object.getString("name");
            this.duration = object.getString("duration");
            this.maxGroupSize = object.getString("maxGroupSize");
            this.difficulty = object.getString("difficulty");
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

    public String getDuration() {
        return duration;
    }

    public String getMaxGroupSize() {
        return maxGroupSize;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public JSONObject toJson(){
        JSONObject tour = new JSONObject();
        try {
            tour.put("name",this.name);
            tour.put("duration",this.duration);
            tour.put("maxGroupSize",this.maxGroupSize);
            tour.put("difficulty",this.difficulty);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tour;
    }
}
