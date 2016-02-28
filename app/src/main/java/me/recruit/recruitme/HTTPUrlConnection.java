package me.recruit.recruitme;

import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class HTTPUrlConnection {

	private static final String POST_URL = "http://justchooseme.azurewebsites.net/candidate/blah@blah.com/add";
	private static final String GET_ALL_URL = "http://justchooseme.azurewebsites.net/candidate/blah@blah.com/listArray";

	private static final String USER_AGENT = "Mozilla/5.0";

	public static void sendJson(final JSONObject json) {
		Thread t = new Thread() {

			public void run() {
				Looper.prepare(); //For Preparing Message Pool for the child Thread
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
				HttpResponse response;

				try {
					HttpPost post = new HttpPost(POST_URL);

					StringEntity se = new StringEntity( json.toString());
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

					post.setEntity(se);
					response = client.execute(post);

					// Check response
					if (response != null){
						Log.d("HTTP_STATUS", String.valueOf(response.getStatusLine()));
						InputStream in = response.getEntity().getContent(); //Get the data in the entity
						String result = convertInputStreamToString(in);
						Log.d("HTTP_RESULT", result);
					}

				} catch(Exception e) {
					e.printStackTrace();
					Log.d("HTTP_URL_Error", "Cannot Estabilish Connection");
				}

				Looper.loop(); //Loop in the message queue
			}
		};

		t.start();
	}

	public static String getAllRequest( ) throws IOException {
		URL url = new URL(GET_ALL_URL);
		HttpURLConnection client = (HttpURLConnection) url.openConnection();
		InputStream in = client.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String returnString = br.readLine();
		client.disconnect();
		return returnString;
	}

	public static String sendGet() throws Exception {

		URL obj = new URL(GET_ALL_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + GET_ALL_URL);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}


	private static String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}


}
