package com.loveneet.gamerspardise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener  {

	Button bRegister;
	EditText etUsername, etPassword,etName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		etName= (EditText) findViewById(R.id.etName);
		etUsername= (EditText) findViewById(R.id.etUsername);
		etPassword= (EditText) findViewById(R.id.etPassword);
		bRegister= (Button) findViewById(R.id.bRegister);
		
		bRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bRegister:
			
			String name = etName.getText().toString();
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();

			user user = new user(name, username, password);
			
			registerUser(user);
			
			break;
		}
	}
		
		private void registerUser(user user){
			
			ServerRequests serverRequests = new ServerRequests(this);
			serverRequests.storeUserDataInBackground(user,new GetUserCallback(){
				@Override
				public void done(user returnedUser){
					
					startActivity(new Intent (Register.this, Login.class));
					
				}
				
			});
		}
	


	

	
}
