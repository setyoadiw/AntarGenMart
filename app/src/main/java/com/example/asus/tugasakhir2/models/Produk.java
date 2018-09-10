package com.example.asus.tugasakhir2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produk {
    @SerializedName("nama_produk")
    @Expose
    public String namaProduk;
    @SerializedName("kategori")
    @Expose
    public String kategori;
    @SerializedName("stok")
    @Expose
    public String stok;
    @SerializedName("harga")
    @Expose
    public String harga;
    @SerializedName("id")
    @Expose
    public String id_produk;
    @SerializedName("foto")
    @Expose
    public String foto;

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getKategori() {
        return kategori;
    }

    public String getStok() {
        return stok;
    }

    public String getHarga() {
        return harga;
    }

    public String getId_produk() {
        return id_produk;
    }

    public String getFoto() {
        return foto;
    }
}
