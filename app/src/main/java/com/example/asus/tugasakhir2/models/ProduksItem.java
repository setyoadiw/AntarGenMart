package com.example.asus.tugasakhir2.models;


import com.google.gson.annotations.SerializedName;

public class ProduksItem{

	@SerializedName("nama_produk")
	private String namaProduk;

	@SerializedName("harga")
	private String harga;

	@SerializedName("foto")
	private String foto;

	@SerializedName("harga_sebelum")
	private String hargaSebelum;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("stok")
	private int stok;

	@SerializedName("id")
	private int id;

	public void setNamaProduk(String namaProduk){
		this.namaProduk = namaProduk;
	}

	public String getNamaProduk(){
		return namaProduk;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setHargaSebelum(String hargaSebelum){
		this.hargaSebelum = hargaSebelum;
	}

	public String getHargaSebelum(){
		return hargaSebelum;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setStok(int stok){
		this.stok = stok;
	}

	public int getStok(){
		return stok;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ProduksItem{" + 
			"nama_produk = '" + namaProduk + '\'' + 
			",harga = '" + harga + '\'' + 
			",foto = '" + foto + '\'' + 
			",harga_sebelum = '" + hargaSebelum + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",stok = '" + stok + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}