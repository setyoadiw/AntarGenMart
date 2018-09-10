package com.example.asus.tugasakhir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PesananResponse {
    @SerializedName("pesanans")
    @Expose
    public List<Pesanan> pesanans = null;

    public List<Pesanan> getPesanans() {
        return pesanans;
    }
}
