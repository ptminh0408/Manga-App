package com.example.mangaapp.display;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.R;
import com.example.mangaapp.adapter.BXHAdapter;
import com.example.mangaapp.api.ApiService;
import com.example.mangaapp.mainscreen.MainScreen;
import com.example.mangaapp.model.Truyen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BXHThang extends AppCompatActivity {
    RecyclerView recyclerView;
    BXHAdapter adapter;
    List<Truyen> listTruyen;
    ImageView home;
    TextView bxh_tong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_bxhthang);
        init();
        initGridView();
        getTruyen();
        home.setOnClickListener(v -> {
            startActivity(new Intent(BXHThang.this, MainScreen.class));
            finish();
        });
        bxh_tong.setOnClickListener(v -> {
            startActivity(new Intent(BXHThang.this, BXH.class));
            finish();
        });
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void getTruyen() {
        ApiService.apiService.GetTatCaTruyen().enqueue(new Callback<List<Truyen>>() {
            @Override
            public void onResponse(@NonNull Call<List<Truyen>> call, @NonNull Response<List<Truyen>> response) {
                listTruyen = response.body();
                List<Truyen> list = new ArrayList<>();
                if (listTruyen != null) {
                    for (int i = 0; i < listTruyen.size(); i++) {
                        if (listTruyen.get(i).isTrangThai()) {
                            list.add(listTruyen.get(i));
                            Log.e("list", ""+list);
                        }
                    }
                    list.sort(Comparator.comparing(Truyen::getLuotXemThang).reversed());
                    adapter = new BXHAdapter(BXHThang.this, list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Truyen>> call, @NonNull Throwable t) {

            }
        });
    }

    private void initGridView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
    }

    public void init() {
        recyclerView = findViewById(R.id.rcv_bxhthang);
        home = findViewById(R.id.bxhthang_home);
        bxh_tong = findViewById(R.id.bxhthang_tong);
    }
}