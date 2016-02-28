package me.recruit.recruitme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {



	public static Candidate parse(String input) {

		Candidate candidate = new Candidate();
		try {
			JSONObject jsonObject = new JSONObject(input);
			candidate.setFirstName(jsonObject.getString("firstName"));
			candidate.setLastName(jsonObject.getString("lastName"));
			candidate.setTitle( jsonObject.getString("title"));
			candidate.setEmail(jsonObject.getString("email"));
			candidate.setLocation(jsonObject.getString("location"));
			candidate.setResume(jsonObject.getString("resume"));
			candidate.setLinkedIn(jsonObject.getString("linkedin"));
			candidate.setPictureURL(jsonObject.getString("picture"));

			JSONArray jsonArray = jsonObject.getJSONArray("portfolio");

			for (int i = 0; i < jsonArray.length(); i++) {
				candidate.addPortfolioUrl(jsonArray.getString(i));
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return candidate;

	}

}
