package com.example.asus.tugasakhir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdukResponse {
    @SerializedName("pesan")
    @Expose
    public String pesan;
    @SerializedName("produks")
    @Expose
    public List<Produk> produks = null;

    public String getPesan() {
        return pesan;
    }

    public List<Produk> getProduks() {
        return produks;
    }
}
