package bulletIn.bulletIn.controller;

import bulletIn.bulletIn.entity.NewsArticle;
import bulletIn.bulletIn.service.NewsArticleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
public class NewsApiController {

    private final RestTemplate restTemplate;
    private final NewsArticleService newsArticleService;
    private final ObjectMapper objectMapper;

    public NewsApiController(RestTemplate restTemplate, NewsArticleService newsArticleService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.newsArticleService = newsArticleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/news")
    public ResponseEntity<String> getNews() {
        String url = "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com&apiKey=f852f05712bb4617a6727fc9b01eb708";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode articles = root.path("articles");

            if (articles.isArray()) {
                for (JsonNode article : articles) {
                    String title = article.path("title").asText();
                    String description = article.path("description").asText();
                    String sourceUrl = article.path("url").asText();
                    String imageUrl = article.path("urlToImage").asText();
                    String publishedAt = article.path("publishedAt").asText();

                    Timestamp timestamp = Timestamp.from(Instant.parse(publishedAt));

                    newsArticleService.saveNewsArticle(title, timestamp, description, sourceUrl, imageUrl);
                }
            }

            return ResponseEntity.ok(response); // Or a success message
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}