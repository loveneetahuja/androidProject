package com.loveneet.gamerspardise;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

	public static final String SP_NAME = "userDetails";
	
	SharedPreferences userLocalDatabase;
	
	public UserLocalStore(Context context){
		userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
	}
	
	public void storeUserData(user user){
		
		SharedPreferences.Editor spEditor = userLocalDatabase.edit();
		spEditor.putString("name",user.name);
		spEditor.putString("username",user.username);
		spEditor.putString("password",user.password);
		spEditor.commit();
	}
	
	public user geLoggedInUser(){
		
		String name = userLocalDatabase.getString("name","");
		String username = userLocalDatabase.getString("username","");

		String password = userLocalDatabase.getString("password","");
		
		
		user storedUser = new user(name , username , password);
		
		return storedUser;

	}
	
	public void setUserLoggedIn(boolean loggedIn){
		SharedPreferences.Editor spEditor = userLocalDatabase.edit();
		spEditor.putBoolean("loggedIn", loggedIn);
		spEditor.commit();
		
		
	}
	
	public boolean getUserLoggedIn(){
		if(userLocalDatabase.getBoolean("loggedIn", false)){
			
			return true;
		}
		else{
			
			return false;
		}
		
	}
	
	public void clearUserData(){
		SharedPreferences.Editor spEditor = userLocalDatabase.edit();

		spEditor.clear();
		spEditor.commit();
		
		
	}
}
