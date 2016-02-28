package me.recruit.recruitme;

import java.util.ArrayList;
import java.util.List;


public class Candidate {
	private String title;
	private String location;
	private String resume;
	private String linkedIn;
	private List<String> portfolioURLs;
	private String pictureURL;

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
}
