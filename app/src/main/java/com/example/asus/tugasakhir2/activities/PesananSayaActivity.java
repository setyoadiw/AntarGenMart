package com.example.asus.tugasakhir2.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.adapter.PesananAdapter;
import com.example.asus.tugasakhir2.models.Pesanan;
import com.example.asus.tugasakhir2.models.PesananResponse;
import com.example.asus.tugasakhir2.network.APIService;
import com.example.asus.tugasakhir2.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananSayaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PesananAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_saya);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Menampilkan icon panah kembali di action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mengganti judul action bar
        getSupportActionBar().setTitle("Pesanan Saya");

        //Deklarasi dan konfigurasi recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_pesanan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);
        loadPesanan(Integer.parseInt(preferences.getString("ID", "")));
    }

    private void loadPesanan(int id){
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<PesananResponse> userCall = service.getPesanan(id);

        userCall.enqueue(new Callback<PesananResponse>() {
            @Override
            public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesanans = response.body().getPesanans();
                    adapter = new PesananAdapter(pesanans);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PesananResponse> call, Throwable t) {
                Toast.makeText(PesananSayaActivity.this, "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
