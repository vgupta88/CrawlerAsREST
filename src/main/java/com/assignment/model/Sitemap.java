package com.assignment.model;

import java.util.Set;

public class Sitemap {
	
	private Set<String> internalLinks;

	private Set<String> externalLinks;
	
	private Set<String> imageLinks;

	public Set<String> getInternalLinks() {
		return internalLinks;
	}

	public void setInternalLinks(Set<String> internalLinks) {
		this.internalLinks = internalLinks;
	}

	public Set<String> getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(Set<String> externalLinks) {
		this.externalLinks = externalLinks;
	}

	public Set<String> getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(Set<String> imageLinks) {
		this.imageLinks = imageLinks;
	}
	

}
