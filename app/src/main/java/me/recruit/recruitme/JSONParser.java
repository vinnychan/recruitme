package me.recruit.recruitme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			candidate.setTitle( jsonObject.getString(TITLE));
			candidate.setEmail(jsonObject.getString(EMAIL));
			candidate.setLocation(jsonObject.getString(LOCATION));
			candidate.setResume(jsonObject.getString(RESUME));
			candidate.setLinkedIn(jsonObject.getString(LINKEDIN));

			try {
				candidate.setPictureURL(jsonObject.getString(PICTURE_URL));
			}  catch (JSONException e) {
//				candidate.setPictureURL("http://augustyniakteam.com/wp-content/uploads/2015/01/Default_User.png");
				Log.d("JSON_PARSER", "No image URL provided");
			}
			candidate.setComments(jsonObject.getString(COMMENTS));

			JSONArray jsonArray = jsonObject.getJSONArray(PORTFOLIO_URLS);

			for (int i = 0; i < jsonArray.length(); i++) {
				candidate.addPortfolioUrl(jsonArray.getString(i));
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return candidate;
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
