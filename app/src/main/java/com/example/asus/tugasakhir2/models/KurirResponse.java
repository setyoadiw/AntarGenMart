package com.example.asus.tugasakhir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KurirResponse {
    @SerializedName("data")
    @Expose
    public List<Kurir> data = null;

    public List<Kurir> getData() {
        return data;
    }
}
