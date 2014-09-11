package redditimages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
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

  public static final String REDDIT_SITE_URL = "http://www.reddit.com/";
  private ExtractorsFactory extractorsFactory;

  public Redditor(ExtractorsFactory extractorsFactory) {
    this.extractorsFactory = extractorsFactory;
  }

  public List<RedditPost> getFirstPagePosts(RedditSection section) {
    RedditPageExtractor pageExtractor = extractorsFactory.getPageExtractor(REDDIT_SITE_URL);
    List<RedditGenericElement> pageElements = pageExtractor.getPagePosts(section.toString());
    return convertGenericElementsToRedditPosts(pageElements);
  }

  public RedditPost getPostByUrl(String url) {
    // TODO: validate url
    RedditPageExtractor pageExtractor = extractorsFactory.getPageExtractor(url);
    RedditGenericElement genericElement = pageExtractor.getPost();
    return convertGenericElementToRedditPost(genericElement);
  }

  private List<RedditPost> convertGenericElementsToRedditPosts(List<RedditGenericElement> elements) {
    List<RedditPost> posts = new ArrayList<>();
    elements.forEach(ele -> {
      posts.add(convertGenericElementToRedditPost(ele));
    });
    return posts;
  }

  private RedditPost convertGenericElementToRedditPost(RedditGenericElement element) {
    RedditPostExtractor extractor = extractorsFactory.getPostExtractor(element);
    RedditPost post = extractor.getRedditPost();
    System.out.println(post);
    Set<String> imageLinks = new HashSet<>();
    getAllLinksFromPost(imageLinks, post);
    getAllImagesFromPost(imageLinks, post);    
    post.setImageLinks(imageLinks);
    imageLinks.stream().forEach( s -> System.out.println(s));
    return post;
  }

  private void getAllImagesFromPost(Set<String> imageLinks, RedditPost p) {
    getAllImagesFromUrl(imageLinks, p.getCommentUrl());
  } 
  
  private void getAllLinksFromPost(Set<String> imageLinks, RedditPost p) {
    getAllLinksFromUrl(imageLinks, p.getCommentUrl());
  }

  private void getAllLinksFromUrl(Set<String> imageLinks, String url) {
    //System.out.println("fetching links from " + url);
    try {
      Document doc = Jsoup.connect(url)
          .data("uh", "")
          .data("over18","yes")
          .method(Method.POST) //remove
          .cookie("over18", "1")
          .followRedirects(true)
          .post();
      
      Elements titles = doc.select("a");
      titles.iterator().forEachRemaining(imgElement -> {
        String linkUrl = imgElement.attr("href");
        if (!linkUrl.startsWith("http://www.reddit.com")) {
          if(isImage(linkUrl)){
            addLinkToSetIfValid(imageLinks, linkUrl);
          }else{
            //System.out.println("\t" + linkUrl);
            getAllImagesFromUrl(imageLinks, linkUrl);     
          }
       
        }
      });
    } catch (IOException e) {
      throw new RuntimeException("unable to get all links from" + url, e);
      // TODO: unable to retrieve
    }
  }

  private boolean isImage(String linkUrl) {
    return linkUrl.contains(".jpg") ||  linkUrl.contains(".jpeg") ||linkUrl.contains(".png") ||linkUrl.contains(".gif");
  }
  
  private void addLinkToSetIfValid(Set<String> links,String linkUrl) {
    //if(linkUrl.startsWith("http")){
      links.add(String.format("<img src=\"%s\"/>", linkUrl)); 
    //}  
  }

  private void getAllImagesFromUrl(Set<String> imageLinks, String url) {
    //System.out.println("fetching images from " + url);
    try {
      Document doc = Jsoup.connect(url).get();
      Elements titles = doc.select("img");
      titles.iterator().forEachRemaining(imgElement -> {
        String linkUrl = imgElement.attr("src");
        if(linkUrl != null && linkUrl.isEmpty()){
          linkUrl = imgElement.attr("data-src");
        }
        //if (linkUrl.startsWith("http") && !linkUrl.contains("reddit")) {
        if(isImage(linkUrl)){
          addLinkToSetIfValid(imageLinks, linkUrl);
        }
        //  System.out.println("\t" + linkUrl);
        //}
      });
    } catch (Exception e) {
      //throw new RuntimeException("unable to get all links from" + url, e);
      // TODO: unable to retrieve
      System.out.println("unable to get images from " + url);
    }
  }

  private List<RedditComment> getComments(RedditPost p) {
    List<RedditComment> comments = new ArrayList<>();
    return comments;
  }

}
