package redditimages.util;

public class UrlUtil {

  public static String convertToAbsUrlIfRelative(String url) {
    String result = url;
    if (url.startsWith("/")) {
      result = "http://www.reddit.com" + url;
    } 
    return result;
  }
}
