package com.example.asus.tugasakhir2.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class PromoResponse{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("produks")
	private List<ProduksItem> produks;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setProduks(List<ProduksItem> produks){
		this.produks = produks;
	}

	public List<ProduksItem> getProduks(){
		return produks;
	}

	@Override
 	public String toString(){
		return 
			"PromoResponse{" + 
			"pesan = '" + pesan + '\'' + 
			",produks = '" + produks + '\'' + 
			"}";
		}
}