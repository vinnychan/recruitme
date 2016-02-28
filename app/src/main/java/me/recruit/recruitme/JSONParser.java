package me.recruit.recruitme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONParser {


	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String TITLE = "title";
	public static final String EMAIL = "email";
	public static final String LOCATION = "location";
	public static final String RESUME = "resume";
	public static final String LINKEDIN = "linkedin";
	public static final String PICTURE_URL = "picture";
	public static final String PORTFOLIO_URLS = "portfolio";
	public static final String COMMENTS = "comments";

	public static Candidate parse(String input) {

		Candidate candidate = new Candidate();
		try {
			JSONObject jsonObject = new JSONObject(input);
			candidate.setFirstName(jsonObject.getString(FIRST_NAME));
			candidate.setLastName(jsonObject.getString(LAST_NAME));

			try {
				candidate.setTitle(jsonObject.getString(TITLE));
			} catch (JSONException e) {
				Log.d("JSON_PARSER", "No title  provided");
			}

			candidate.setEmail(jsonObject.getString(EMAIL));

			try {
				candidate.setLocation(jsonObject.getString(LOCATION));
			} catch (JSONException e) {
				Log.d("JSON_PARSER", "No location  provided");
			}

			candidate.setResume(jsonObject.getString(RESUME));

			try {
				candidate.setLinkedIn(jsonObject.getString(LINKEDIN));
			} catch (JSONException e) {
				Log.d("JSON_PARSER", "No linkedin URL  provided");
			}

			try {
				candidate.setPictureURL(jsonObject.getString(PICTURE_URL));
			}  catch (JSONException e) {
//				candidate.setPictureURL("http://augustyniakteam.com/wp-content/uploads/2015/01/Default_User.png");
				Log.d("JSON_PARSER", "No image URL provided");
			}

			try {
				candidate.setComments(jsonObject.getString(COMMENTS));
			} catch (JSONException e) {
				Log.d("JSON_PARSER", "No comments in profile");
			}
			JSONArray jsonArray = jsonObject.getJSONArray(PORTFOLIO_URLS);

			for (int i = 0; i < jsonArray.length(); i++) {
				candidate.addPortfolioUrl(jsonArray.getString(i));
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return candidate;
	}

	public static List<String> parseCandidateList(String input) {

		List<String> result = new ArrayList<>();

		try {
			JSONObject candidateListJSON = new JSONObject(input);
			JSONArray candidateArrayJSON = candidateListJSON.getJSONArray("candidates");

			for (int i =0; i < candidateArrayJSON.length(); i++) {
				result.add(candidateArrayJSON.getJSONObject(i).toString());
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static List<String> parseCandidateIdList(String input) {
		List<String> result = new ArrayList<>();

		try {
			JSONObject candidateListIdJSON = new JSONObject(input);
			JSONArray candidateArrayJSON = candidateListIdJSON.getJSONArray("candidates");

			for (int i =0; i < candidateArrayJSON.length(); i++) {
				result.add(candidateArrayJSON.getJSONObject(i).getString("_id"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}


		return result;
	}

	public static String parseCandidateName(String input) {
		String candidateName = "";
		try {
			JSONObject jsonObject = new JSONObject(input);
			candidateName = jsonObject.getString(FIRST_NAME) +
							jsonObject.getString(LAST_NAME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return candidateName;
	}

}
