package redditimages.extractor.jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.jsoup.nodes.Element;

import redditimages.extractor.RedditPostExtractor;
import redditimages.model.RedditGenericElement;
import redditimages.model.RedditPost;
import redditimages.util.UrlUtil;

public class RedditJsoupPostExtractor implements RedditPostExtractor {

  public Element element;

  protected RedditJsoupPostExtractor(RedditGenericElement element) {
    this.element = (Element) element.getElement();
  }

  @Override
  public RedditPost getRedditPost() {
    RedditPost post = new RedditPost();
    post.setId(getId());
    post.setTitleUrl(getTitleUrl());
    post.setCommentUrl(getCommentUrl());
    post.setCommentsNumber(getCommentsNumber());
    post.setMostRecentPostDate(getMostRecentPostDate());
    return post;
  }

  private String getId() {
    return element.attr("data-fullname");
  }

  private String getTitleUrl() {
    return UrlUtil.convertToAbsUrlIfRelative(element.select(".entry p a").attr("href"));
  }

  private String getCommentUrl() {
    return UrlUtil.convertToAbsUrlIfRelative(element.select(".entry .first a").attr("href"));
  }

  private int getCommentsNumber() {
    int result;
    try {
      result = Integer.parseInt(element.select(".entry .first").text().split(" ")[0]);
    } catch (NumberFormatException e) {
      System.out.println("unable to parse comments number, using default");
      result = 0;
    }
    return result;
  }

  private LocalDateTime getMostRecentPostDate() {
    String date = element.select("time").attr("datetime");
    LocalDateTime ldt;
    try {
      ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    } catch (DateTimeParseException e) {
      ldt = LocalDateTime.of(1972, 1, 1, 0, 0, 0);
    }
    return ldt;
  }

}
