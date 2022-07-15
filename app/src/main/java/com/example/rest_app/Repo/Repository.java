package com.example.rest_app.Repo;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;

import com.example.rest_app.utils.STATUS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Repository {

    // Singleton
    private static Repository instance ;
    private String SERVER_URL = "http://192.168.8.103:3003";
    private String GET_USERS_URL = SERVER_URL + "/api/v1/users";
    private String DELETE_USERS_URL = SERVER_URL +"/api/v1/users";
    private String CREATE_USERS = SERVER_URL+"/api/v1/users";
    private OkHttpClient client;
    public  String deleted;



    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    // Get Instance
    public static  Repository getInstance(){
        if (instance == null) instance = new Repository();
        return instance;
    }

    public Repository(){
        this.client = new OkHttpClient();
    }

    //add Users
    public void addUsers(Users user, getUserResults onCreateListener){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(user.toJson().toString(),JSON);
        Request request = new Request.Builder()
                .url(CREATE_USERS)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onCreateListener.CreateUsers(false,null,STATUS.NETWORK_FAILED);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              if(response.isSuccessful()){
                  onCreateListener.CreateUsers(true,null,STATUS.SUCCESS);
              }
            }
        });

    }

    // Delete Users
    public void deleteUsers(String id, getUserResults onDeleteListener){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DELETE_USERS_URL +"/"+id)
                .delete()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onDeleteListener.deleteUsers(false,id,STATUS.NETWORK_FAILED);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        deleted = id;
                        onDeleteListener.deleteUsers(true,id,STATUS.SUCCESS);
                    }
                    else {
                        onDeleteListener.deleteUsers(false,id,STATUS.BAD_REQUEST);
                    }
                }
                else {
                    onDeleteListener.deleteUsers(false,id,STATUS.SERVER_UNREACHABLE);
                }
            }
        });
    }

    // Get Tours
    public void getUsers(getUserResults onUserResults){
        // list of Tours
        ArrayList<Users> allUsers = new ArrayList<>();

        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(GET_USERS_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onUserResults.allUsers(false,null,STATUS.NETWORK_FAILED);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String jsonString = response.body().string();

                    // JSON Object
                    try {
                        JSONObject root = new JSONObject(jsonString);

                        // Extract List
                        JSONArray usersJSONArray = root.getJSONArray("data");

                        // Iterate the array
                        for (int i = 0; i < usersJSONArray.length(); i++){
                            allUsers.add(new Users(usersJSONArray.getJSONObject(i)));
                        }

                        onUserResults.allUsers(true,allUsers,STATUS.SUCCESS);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    onUserResults.allUsers(false,null,STATUS.SERVER_UNREACHABLE);
                }
            }
        });
    }

}
