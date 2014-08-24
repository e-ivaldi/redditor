package redditimages.extractor.jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.jsoup.nodes.Element;

import redditimages.extractor.RedditPostExtractor;
import redditimages.model.RedditGenericElement;
import redditimages.util.UrlUtil;

public class RedditJsoupPostExtractor implements RedditPostExtractor {

  public Element element;

  protected RedditJsoupPostExtractor(RedditGenericElement element) {
    this.element = (Element) element.getElement();
  }

  @Override
  public String getId() {
    return element.attr("data-fullname");
  }

  @Override
  public String getTitleUrl() {
    return UrlUtil.convertToAbsUrlIfRelative(element.select(".entry p a").attr("href"));
  }

  @Override
  public String getCommentUrl() {
    return UrlUtil.convertToAbsUrlIfRelative(element.select(".entry .first a").attr("href"));
  }

  @Override
  public int getCommentsNumber() {
    int result;
    try {
      result = Integer.parseInt(element.select(".entry .first").text().split(" ")[0]);
    } catch (NumberFormatException e) {
      System.out.println("unable to parse comments number, using default");
      result = 0;
    }
    return result;
  }

  @Override
  public LocalDateTime getMostRecentPostDate() {
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
