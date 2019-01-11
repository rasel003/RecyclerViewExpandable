package com.rasel.recyclerviewexpandable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "rsl";

    List<Clients> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();

        Call<ApiResponse> call = RetrofitClient.getInstance().getApi().getApiResponse();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    return;
                }
                ApiResponse apiResponse = response.body();
                if (apiResponse != null && !apiResponse.getError()) {

                    if((apiResponse.getMessage().equals("Client content available"))){
                       data = apiResponse.getClient();
                        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(data));
                    }else{
                        Log.d(TAG, "onResponse: Client List is Empty");
                    }
                }else {
                    if(apiResponse==null) {
                        Log.d(TAG, "onResponse: apiResponse is NULL");
                    }else{
                        Log.d(TAG, "onResponse: Error Returned");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
