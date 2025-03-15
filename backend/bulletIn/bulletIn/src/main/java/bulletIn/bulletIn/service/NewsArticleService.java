package bulletIn.bulletIn.service;

import bulletIn.bulletIn.entity.NewsArticle;
import bulletIn.bulletIn.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class NewsArticleService {

    @Autowired
    private NewsArticleRepository newsArticleRepository;

    public NewsArticle saveNewsArticle(String title, Timestamp timestamp, String description, String sourceUrl, String imageUrl) {
        NewsArticle article = new NewsArticle(title, timestamp, description, sourceUrl, imageUrl);
        return newsArticleRepository.save(article);
    }
    //Other methods for getting data from the api, and then calling saveNewsArticle.
}