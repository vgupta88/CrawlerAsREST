# CrawlerAsREST


This project is a basic Spring boot application for crawling any url provided as per GET /api/crawl?url=<url>

## Requirement
  - JDK 1.8
  - Apache Maven 3.x

## Technologies
- Java 8
- Spring boot
- Jsoup

### Executing 
```sh
$ mvn spring-boot:run
```
## API
- GET /api/crawl/url=<url>

## TODO 
- Improve performance for large number of links
- Test all Http connections to internal links and capture status codes
