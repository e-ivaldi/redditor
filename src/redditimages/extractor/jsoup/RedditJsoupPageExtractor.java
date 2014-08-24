package redditimages.extractor.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import redditimages.extractor.RedditPageExtractor;
import redditimages.model.RedditGenericElement;

public class RedditJsoupPageExtractor implements RedditPageExtractor{

  private final String pageUrl;
  
  protected RedditJsoupPageExtractor(String url) {
    this.pageUrl = url;
  }

  @Override
  public List<RedditGenericElement> getPagePosts(String section){
    List<RedditGenericElement> elements = new ArrayList<>();
    try {
      Document doc = Jsoup.connect(String.format("%s/%s", pageUrl, section)).get();
      Elements entries = doc.select(".thing").not("[style~=display:none]");
      entries.iterator().forEachRemaining(e-> elements.add(new RedditGenericElement(e)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return elements;
  }
  
}
