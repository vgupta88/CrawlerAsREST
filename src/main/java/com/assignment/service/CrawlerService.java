package com.assignment.service;

import java.io.IOException;

import com.assignment.model.Sitemap;

public interface CrawlerService {
	
	public Sitemap createJson(String url) throws IOException;

}
