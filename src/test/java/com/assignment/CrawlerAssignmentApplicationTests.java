package com.assignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.controller.CrawlerController;
import com.assignment.model.Sitemap;
import com.assignment.service.CrawlerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CrawlerAssignmentApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private MockMvc mockMvc;

	@Mock 
	CrawlerService crawlerService;

	@InjectMocks
	private CrawlerController crawlerController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(crawlerController).build();
	}

	@Test
	public void endpointShouldNotAllowPOST() throws Exception {
		ResponseEntity<String> response = this.restTemplate.postForEntity("/api/crawl?url=https://monzo.com",null, String.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(405);
		assertThat(response.getStatusCodeValue()).isNotEqualTo(200);
	}

	@Ignore
	public void tester() throws Exception {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/crawler", String.class, "https://monzo.com");

		assertThat(response.getStatusCodeValue()).isEqualTo(405);
		assertThat(response.getStatusCodeValue()).isNotEqualTo(200);
	}
	
	@Test
	public void testLinks() throws Exception {
		Sitemap sm=new Sitemap();
		Set<String> externalLinks=new HashSet<String>();
		Set<String> internalLinks=new HashSet<String>();
		Set<String> imageLinks=new HashSet<String>();
		externalLinks.add("http://www.google.com");
		internalLinks.add("https://monzo.com/test");
		imageLinks.add("https://monzo.com.test.jpg");
		sm.setExternalLinks(externalLinks);
		sm.setImageLinks(imageLinks);
		sm.setInternalLinks(internalLinks);
		
		Mockito.when(crawlerService.createJson(Mockito.any(String.class))).thenReturn(sm);
		Sitemap sitemap=crawlerService.createJson("https://monzo.com");
		assertEquals(sitemap.getExternalLinks(), sm.getExternalLinks());
		assertEquals(sitemap.getInternalLinks(), sm.getInternalLinks());
		assertEquals(sitemap.getImageLinks(), sm.getImageLinks());
	}
	
	@Test
	public void testLinksController() throws Exception {
		Sitemap sm=new Sitemap();
		Set<String> externalLinks=new HashSet<String>();
		Set<String> internalLinks=new HashSet<String>();
		Set<String> imageLinks=new HashSet<String>();
		externalLinks.add("http://www.google.com");
		externalLinks.add("http://www.facebook.com");
		internalLinks.add("https://monzo.com/test");
		imageLinks.add("https://monzo.com.test.jpg");
		sm.setExternalLinks(externalLinks);
		sm.setImageLinks(imageLinks);
		sm.setInternalLinks(internalLinks);
		Mockito.when(crawlerService.createJson(Mockito.any(String.class))).thenReturn(sm);
		RequestBuilder reqquest = MockMvcRequestBuilders.get("/api/crawl?url=https://monzo.com");
		mockMvc.perform(reqquest)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.externalLinks", IsCollectionWithSize.hasSize(2)))
		.andDo(MockMvcResultHandlers.print());
	}
}
