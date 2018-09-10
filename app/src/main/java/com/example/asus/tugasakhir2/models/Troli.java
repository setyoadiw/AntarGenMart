package com.example.asus.tugasakhir2.models;

import com.orm.SugarRecord;

public class Troli extends SugarRecord {
    String id_produk;
    String nama;
    String kategori;
    String stok;
    String harga;
    String jumlah;
    String foto;

    public Troli() {
    }

    public Troli(String id_produk, String nama, String kategori, String stok, String harga, String jumlah, String foto) {
        this.id_produk = id_produk;
        this.nama = nama;
        this.kategori = kategori;
        this.stok = stok;
        this.harga = harga;
        this.jumlah = jumlah;
        this.foto = foto;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
