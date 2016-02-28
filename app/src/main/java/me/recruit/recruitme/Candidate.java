package me.recruit.recruitme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Candidate {

	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String location;
	private String resume;
	private String linkedIn;
	private List<String> portfolioURLs;
	private String pictureURL;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private String comments = "";

	public Candidate() {
		portfolioURLs = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public List<String> getPortfolioURLs() {
		return portfolioURLs;
	}

	public void setPortfolioURLs(List<String> profolioURLs) {
		this.portfolioURLs = profolioURLs;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public void addPortfolioUrl(String portfolioURL) {
		this.portfolioURLs.add(portfolioURL);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String toBaseString() {
		JSONObject candidateJSON = new JSONObject();
		JSONArray portfolioJSONArray = new JSONArray(portfolioURLs);



		try {
			candidateJSON.put(JSONParser.FIRST_NAME, firstName);
			candidateJSON.put(JSONParser.LAST_NAME, lastName);
			candidateJSON.put(JSONParser.TITLE, title);
			candidateJSON.put(JSONParser.EMAIL, email);
			candidateJSON.put(JSONParser.LOCATION, location);
			candidateJSON.put(JSONParser.RESUME, resume);
			candidateJSON.put(JSONParser.LINKEDIN, linkedIn);
			candidateJSON.put(JSONParser.PORTFOLIO_URLS, portfolioJSONArray);
			candidateJSON.put(JSONParser.PICTURE_URL, pictureURL);
			candidateJSON.put(JSONParser.COMMENTS, comments);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return candidateJSON.toString();
	}

	public String getName() {
		return firstName + " " + lastName;
	}
}
