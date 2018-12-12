package com.assignment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.assignment.model.Sitemap;

@Service
public class CrawlerServiceImpl implements CrawlerService {

	private Set<String> internalLinks = new TreeSet<>();
	private Set<String> externalLinks = new TreeSet<>();
	private Set<String> imageLinks = new TreeSet<>();
	private Set<String> crawledLinks = new TreeSet<>();
	private int pagesCrawled = 1;
	private List<String> fileRegEx = new ArrayList<>();
	private String noPageRegEx = ".*?\\#.*";

	@Override
	public Sitemap createJson(String url) throws IOException {
		//String url = "http://web.mit.edu/";
		fileRegEx.add(".*?\\.png");
		fileRegEx.add(".*?\\.jpg");
		fileRegEx.add(".*?\\.jpeg");
		fileRegEx.add(".*?\\.gif");
		fileRegEx.add(".*?\\.zip");
		fileRegEx.add(".*?\\.7z");
		fileRegEx.add(".*?\\.rar");
		fileRegEx.add(".*?\\.css.*");
		fileRegEx.add(".*?\\.js.*");		
		process(url);
		Sitemap sitemap = new Sitemap();
		sitemap.setInternalLinks(internalLinks);
		sitemap.setExternalLinks(externalLinks);
		sitemap.setImageLinks(imageLinks);		
		return sitemap;
	}

	private void process(String url) throws IOException {		
		if(!crawledLinks.contains(url)) {
			System.out.println("Crawling "+	pagesCrawled);
			pagesCrawled++;
			crawledLinks.add(url);
			System.out.println(url);
			Document doc = Jsoup.connect(url).get();
			Elements images = doc.select("img");
			for (Element image : images) {
				String imageUrl = image.attr("abs:data-src");
				if(imageUrl.equals(""))
					imageUrl = image.attr("abs:src");
				// It is an image --> add to list
				if (!imageLinks.contains(url) && !imageUrl.equals("")) 
					imageLinks.add(imageUrl);

			}

			Elements links = doc.select("a[href]");
			for (Element link : links) {

				if(link.attr("href").equals("/")) 
					continue;
				System.out.println("Link: "+link.attr("abs:href"));
				String linkUrl = link.absUrl("href");
				if(linkUrl.contains(url)) {
					if (!linkUrl.matches(noPageRegEx)) { 
						for (String regex : fileRegEx) {
							if (linkUrl.matches(regex)) {
								internalLinks.add(linkUrl);
								return;
							}
						}

						if (!internalLinks.contains(linkUrl) && !linkUrl.equals("")) 
							process(linkUrl);
					}
				} else { 
					if (!externalLinks.contains(linkUrl) && !linkUrl.equals("")) 
						externalLinks.add(linkUrl);
				}
			}
		}

	}
}
