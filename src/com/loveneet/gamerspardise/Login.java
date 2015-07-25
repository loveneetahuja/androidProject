package com.loveneet.gamerspardise;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener  {

	Button bLogin;
	EditText etUsername, etPassword;
	TextView tvRegisterLink;
	UserLocalStore userLocalStore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		etUsername= (EditText) findViewById(R.id.etUsername);
		etPassword= (EditText) findViewById(R.id.etPassword);
		bLogin= (Button) findViewById(R.id.bLogin);
		tvRegisterLink= (TextView) findViewById(R.id.tvRegisterLink);
		bLogin.setOnClickListener(this);
		tvRegisterLink.setOnClickListener(this);
		userLocalStore = new UserLocalStore(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bLogin:
			
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();
			
			user user = new user(username,password,null);
			
			authenticate(user);
			userLocalStore.storeUserData(user);
			userLocalStore.setUserLoggedIn(true);
			
			break;
			
		case R.id.tvRegisterLink:
		
			Intent regis = new Intent(Login.this,Register.class);
			
			startActivity(regis);
		
			break;
		}
		
	}
	
	private void authenticate(user user){
		
		ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.fetchUserDataInBackground(user,new GetUserCallback(){
			
			@Override
			public void done(user returnedUser){
				if (returnedUser ==null){
					showErrorMessage();
					
				}else{
					logUserIn(returnedUser);
				}
			}
		});
	}

	private void showErrorMessage(){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
		dialogBuilder.setMessage("Incorrect user details");
		dialogBuilder.setPositiveButton("Ok",null);
		dialogBuilder.show();
	}
	
	private void logUserIn(user returnedUser){
		
		userLocalStore.storeUserData(returnedUser);
		userLocalStore.setUserLoggedIn(true);
		
		startActivity(new Intent(this, MainActivity.class));
		
	}
}
