package com.loveneet.gamerspardise;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener  {

	Button bLogout;
	EditText etUsername, etPassword;
	UserLocalStore userLocalStore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		etUsername= (EditText) findViewById(R.id.etUsername);
		etPassword= (EditText) findViewById(R.id.etPassword);
		bLogout= (Button) findViewById(R.id.bLogout);
		
		bLogout.setOnClickListener(this);
		
		userLocalStore = new UserLocalStore(this);
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(authentication()==true){
			
			displayUserDetails();
		}else{
			startActivity(new Intent(MainActivity.this, Login.class));
		}
		
		
	}

	private boolean authentication(){
		
	return userLocalStore.getUserLoggedIn();
	}
	
	private void displayUserDetails(){
		
		user user = userLocalStore.geLoggedInUser();
		etUsername.setText(user.username);
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bLogout:
			
			userLocalStore.clearUserData();
			userLocalStore.setUserLoggedIn(false);
			
			Intent loveneet = new Intent(MainActivity.this,Login.class);
			
		startActivity(loveneet);
			break;
		}
		
	}
	

	
}


	

	


	
	






	

