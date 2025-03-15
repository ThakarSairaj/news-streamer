package bulletIn.bulletIn.repository;

import bulletIn.bulletIn.entity.NewsArticle;
import bulletIn.bulletIn.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Adjust origin as needed
public class NewsDataController {

    @Autowired
    private NewsArticleRepository newsArticleRepository;

    @GetMapping("/news-data")
    public List<NewsArticle> getNewsData() {
        return newsArticleRepository.findAll();
    }
}