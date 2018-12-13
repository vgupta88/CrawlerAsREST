package com.assignment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.model.Sitemap;
import com.assignment.service.CrawlerService;

@RestController
@RequestMapping("/api")
public class CrawlerController {
	
	@Autowired
	CrawlerService crawlerService;
	
	@GetMapping("/crawl")
	public ResponseEntity<Sitemap> listFeeds(@RequestParam("url") String url) throws IOException {
		Sitemap sitemap = crawlerService.createJson(url);
		return new ResponseEntity<Sitemap>(sitemap, HttpStatus.OK);
		
	}

}
