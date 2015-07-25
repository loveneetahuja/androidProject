package com.loveneet.gamerspardise;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ServerRequests {

	ProgressDialog progressDialog;
	public static final int CONNECTION_TIMEOUT = 1000 * 15;
	public static final String SERVER_ADDRESS = "127.0.0.1";
	
	public ServerRequests(Context context){
		
		progressDialog = new ProgressDialog(context);
		
		progressDialog = setCancelable(false);
		progressDialog = setTitle("Processing");
		progressDialog.setMessage("Please wait...");
		
		
	}
	
	private ProgressDialog setCancelable(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	private ProgressDialog setTitle(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void storeUserDataInBackground(user user, GetUserCallback userCallBack){
		
		progressDialog.show();
		
		new StoreUserDataAsynctask(user, userCallBack).execute();
	}
	
	public void fetchUserDataInBackground(user user,GetUserCallback getUserCallBack){
		progressDialog.show();
		new fetchUserDataAsynctask(user, getUserCallBack).execute();
	}
	
	public class StoreUserDataAsynctask extends AsyncTask<Void, Void, Void>{

		user user;
		GetUserCallback userCallback;
		
		public StoreUserDataAsynctask(user user2, GetUserCallback userCallBack2) {
			// TODO Auto-generated constructor stub
		}
		public void StoreUserDataAsyncTask(user user, GetUserCallback userCallBack){
			
			this.user =user;
			this.userCallback = userCallBack;
			
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			ArrayList<NameValuePair> dataToSend = new ArrayList<>();
			dataToSend.add(new BasicNameValuePair("name",user.name));
			dataToSend.add(new BasicNameValuePair("usernam",user.username));
			dataToSend.add(new BasicNameValuePair("password",user.password));
			
			HttpParams httpRequestsParam = new BasicHttpParams();
			
			HttpConnectionParams.setConnectionTimeout(httpRequestsParam, CONNECTION_TIMEOUT);
			
			HttpConnectionParams.setSoTimeout(httpRequestsParam, CONNECTION_TIMEOUT);
			
			HttpClient client = new DefaultHttpClient(httpRequestsParam);
			
			HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
			try{
			post.setEntity(new UrlEncodedFormEntity(dataToSend));
			client.execute(post)
;			}
			catch(Exception e){
					e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
				progressDialog.dismiss();
				userCallback.done(null);
			super.onPostExecute(result);
			
		}
		
	
		
	}
	public class fetchUserDataAsynctask extends AsyncTask<Void, Void, user>{

		user user;
		GetUserCallback userCallback;
		
		public fetchUserDataAsynctask(user user2, GetUserCallback userCallBack2) {
			// TODO Auto-generated constructor stub
		}
		public void StoreUserDataAsynctask(user user2, GetUserCallback userCallBack2) {
			// TODO Auto-generated constructor stub
		}
		public void fetchUserDataAsyncTask(user user, GetUserCallback userCallBack){
			
			this.user =user;
			this.userCallback = userCallBack;
			
		}
		@Override
		protected user doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			ArrayList<NameValuePair> dataToSend = new ArrayList<>();
		
			dataToSend.add(new BasicNameValuePair("usernam",user.username));
			dataToSend.add(new BasicNameValuePair("password",user.password));
			

			HttpParams httpRequestsParam = new BasicHttpParams();
			
			HttpConnectionParams.setConnectionTimeout(httpRequestsParam, CONNECTION_TIMEOUT);
			
			HttpConnectionParams.setSoTimeout(httpRequestsParam, CONNECTION_TIMEOUT);
			
			HttpClient client = new DefaultHttpClient(httpRequestsParam);
			
			HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");
			user returnedUser= null;
			try{
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);
				
						
						HttpEntity entity  = httpResponse.getEntity();
						
						String result = EntityUtils.toString(entity);
						JSONObject jObject = new JSONObject(result);
						
						if(jObject.length() == 0){
						
							returnedUser=null;
						}else{
							String name = jObject.getString("name");
						
							
							returnedUser = new user(name, user.username, user.password);
						}
	;			}
				catch(Exception e){
						e.printStackTrace();
				}
			return returnedUser;
		}
		@Override
		protected void onPostExecute(user returnedUser) {
			// TODO Auto-generated method stub
				progressDialog.dismiss();
				userCallback.done(returnedUser);
			super.onPostExecute(returnedUser);
			
		}
	}
	
}