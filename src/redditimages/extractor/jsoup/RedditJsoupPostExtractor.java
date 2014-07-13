package redditimages.extractor.jsoup;

import org.jsoup.nodes.Element;

import redditimages.extractor.RedditPostExtractor;
import redditimages.model.RedditGenericElement;

public class RedditJsoupPostExtractor implements RedditPostExtractor{

  public Element element;
  
  protected RedditJsoupPostExtractor(RedditGenericElement element) {
    this.element = element.getJsoupElement();
  }

  @Override
  public String getTitleUrl() {
    return sanitizeUrl(element.select("p a").attr("href"));
  }
  
  @Override
  public String getCommentUrl() {
    return sanitizeUrl(element.select(".first a").attr("href"));
  }
  
  @Override
  public int getCommentsNumber(){
    int result = 0;
    try{
      result = Integer.parseInt(element.select(".first").text().split(" ")[0]);
    }catch(Exception e){
      System.out.println("unable to parse comments number, using default");
    }
    return result;
  }
  
  private String sanitizeUrl(String url) {
    String result;
    if (url.startsWith("/")) {
      result = "http://www.reddit.com" + url;
    } else {
      result = url;
    }
    return result;
  }
  
}
