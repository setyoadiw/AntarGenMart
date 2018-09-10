package com.example.asus.tugasakhir2.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.tugasakhir2.R;
import com.example.asus.tugasakhir2.adapter.ProdukAdapter;
import com.example.asus.tugasakhir2.models.Produk;
import com.example.asus.tugasakhir2.models.ProdukResponse;
import com.example.asus.tugasakhir2.network.APIService;
import com.example.asus.tugasakhir2.network.RetrofitClient;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HalamanKesehatan extends Fragment {

    //Deklarasi variabel global
    private RecyclerView recyclerView;
    private ProdukAdapter adapter;
    PullToRefreshView swipeRefreshLayout;
    List<Produk> produks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_halaman_kesehatan, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Kesehatan");

        //Pull to refresh
        swipeRefreshLayout = (PullToRefreshView)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadKesehatan();
                    }
                }, 1000);
            }
        });

        //Deklarasi dan konfigurasi recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        adapter = new ProdukAdapter(produks);
        recyclerView.setAdapter(adapter);

        //Memanggil fungsi loadKesehatan
        loadKesehatan();
    }

    //Fungsi ambil data produk kesehatan dari server kemudian tampilkan di recyclerview
    private void loadKesehatan() {
        //Tampilkan loading
        swipeRefreshLayout.setRefreshing(true);

        //Retrofit call
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<ProdukResponse> userCall = service.getKesehatan();

        userCall.enqueue(new Callback<ProdukResponse>() {
            //Fungsi ini akan dieksekusi ketika ada respon dari server
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (isAdded() && response.isSuccessful()) {
                    //Sembunyikan loading
                    swipeRefreshLayout.setRefreshing(false);

                    //Tampilkan data produk kesehatan ke recyclerview
                    produks.clear();
                    produks.addAll(response.body().getProduks());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), R.string.respon_sukses, Toast.LENGTH_SHORT).show();
                }
            }

            //Fungsi ini akan dieksekusi ketika tidak dapat terhubung ke server
            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                if (isAdded()) {
                    //Sembunyikan loading dan tampilkan toast
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}