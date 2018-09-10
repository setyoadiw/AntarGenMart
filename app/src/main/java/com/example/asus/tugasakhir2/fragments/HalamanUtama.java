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
import com.example.asus.tugasakhir2.adapter.PromoAdapter;
import com.example.asus.tugasakhir2.models.Produk;
import com.example.asus.tugasakhir2.models.ProdukResponse;
import com.example.asus.tugasakhir2.models.ProduksItem;
import com.example.asus.tugasakhir2.models.PromoResponse;
import com.example.asus.tugasakhir2.network.APIService;
import com.example.asus.tugasakhir2.network.RetrofitClient;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanUtama extends Fragment{

    private RecyclerView recyclerView;
    private PromoAdapter promoAdapter;
    PullToRefreshView swipeRefreshLayout;
    List<ProduksItem> produksPromo = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_halaman_utama, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Promo");

        //Pull to refresh
        swipeRefreshLayout = (PullToRefreshView)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadProduk();
                    }
                }, 1000);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        promoAdapter = new PromoAdapter(produksPromo);
        recyclerView.setAdapter(promoAdapter);

        loadProduk();
    }

    private void loadProduk() {
        swipeRefreshLayout.setRefreshing(true);
        APIService service = RetrofitClient.getClient().create(APIService.class);
        Call<PromoResponse> userCall = service.getPromo();

        userCall.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
                if (isAdded() && response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);
                    produksPromo.clear();
                    produksPromo.addAll(response.body().getProduks());
                    promoAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), R.string.respon_sukses, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PromoResponse> call, Throwable t) {
                if (isAdded()) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Gagal Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
