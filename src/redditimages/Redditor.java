package redditimages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import redditimages.extractor.RedditPageExtractor;
import redditimages.extractor.RedditPostExtractor;
import redditimages.extractor.factory.ExtractorsFactory;
import redditimages.model.RedditComment;
import redditimages.model.RedditGenericElement;
import redditimages.model.RedditPost;
import redditimages.model.section.RedditSection;

public class Redditor {

  private ExtractorsFactory extractorsFactory;

  public Redditor(ExtractorsFactory extractorsFactory) {
    this.extractorsFactory = extractorsFactory;
  }

  public List<RedditPost> getFirstPagePosts(RedditSection section) {
    RedditPageExtractor pageExtractor = extractorsFactory.getPageExtractor("http://www.reddit.com/");
    List<RedditGenericElement> pageElements = pageExtractor.getPagePosts(section.toString());
    return getRedditPosts(pageElements);
  }  
  
  public RedditPost getPost(String url){
    //TODO: validate url
    
    RedditPageExtractor pageExtractor = extractorsFactory.getPageExtractor(url);
    
    RedditPost redditPost =  getRedditPost(pageExtractor.getPost());
    
    return redditPost;
    
    
  }
  
  private List<RedditPost> getRedditPosts(List<RedditGenericElement> elements){
    List<RedditPost> posts = new ArrayList<>();
    elements.forEach(ele -> {
      posts.add(getRedditPost(ele));
    });
    return posts;
  }  
  
  private RedditPost getRedditPost(RedditGenericElement element){
    RedditPostExtractor extractor = extractorsFactory.getPostExtractor(element);
    RedditPost post = new RedditPost();
    post.setId(extractor.getId());
    post.setTitleUrl(extractor.getTitleUrl());
    post.setCommentUrl(extractor.getCommentUrl());
    post.setCommentsNumber(extractor.getCommentsNumber());
    post.setMostRecentPostDate(extractor.getMostRecentPostDate());
    System.out.println(post);
    return post;
  }  
 
  private void searchImagesForPost(RedditPost p) {
    try {
      Document doc = Jsoup.connect(p.getCommentUrl()).get();
      Elements titles = doc.select("a");
      titles.iterator().forEachRemaining(imgElement -> {
        String linkUrl = imgElement.attr("href");
        if (linkUrl.startsWith("http") && !linkUrl.contains("reddit")) {
          // System.out.println("\t" + linkUrl);
        }
      });
    } catch (IOException e) {
      // TODO: unable to retrieve
    }
  }

  private List<RedditComment> getComments(RedditPost p) {
    List<RedditComment> comments = new ArrayList<>();
    return comments;
  }

}
