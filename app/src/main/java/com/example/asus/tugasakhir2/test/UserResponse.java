package com.example.asus.tugasakhir2.test;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("user")
	private User user;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"pesan = '" + pesan + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}