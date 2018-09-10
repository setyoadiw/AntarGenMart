package com.example.asus.tugasakhir2.test;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("admintransaksi")
	private int admintransaksi;

	@SerializedName("kurir")
	private int kurir;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("token")
	private Object token;

	@SerializedName("admintoko")
	private int admintoko;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("manajer")
	private int manajer;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("no_telp")
	private String noTelp;

	@SerializedName("pelanggan")
	private int pelanggan;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setAdmintransaksi(int admintransaksi){
		this.admintransaksi = admintransaksi;
	}

	public int getAdmintransaksi(){
		return admintransaksi;
	}

	public void setKurir(int kurir){
		this.kurir = kurir;
	}

	public int getKurir(){
		return kurir;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setToken(Object token){
		this.token = token;
	}

	public Object getToken(){
		return token;
	}

	public void setAdmintoko(int admintoko){
		this.admintoko = admintoko;
	}

	public int getAdmintoko(){
		return admintoko;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setManajer(int manajer){
		this.manajer = manajer;
	}

	public int getManajer(){
		return manajer;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setPelanggan(int pelanggan){
		this.pelanggan = pelanggan;
	}

	public int getPelanggan(){
		return pelanggan;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"created_at = '" + createdAt + '\'' + 
			",admintransaksi = '" + admintransaksi + '\'' + 
			",kurir = '" + kurir + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",token = '" + token + '\'' + 
			",admintoko = '" + admintoko + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",manajer = '" + manajer + '\'' + 
			",id = '" + id + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",no_telp = '" + noTelp + '\'' + 
			",pelanggan = '" + pelanggan + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}