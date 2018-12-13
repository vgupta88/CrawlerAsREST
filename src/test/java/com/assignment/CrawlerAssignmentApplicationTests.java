package com.assignment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CrawlerAssignmentApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
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
}
