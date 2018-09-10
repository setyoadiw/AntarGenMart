package com.example.asus.tugasakhir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kurir {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("pelanggan")
    @Expose
    public String pelanggan;
    @SerializedName("kurir")
    @Expose
    public String kurir;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public String getKurir() {
        return kurir;
    }
}
