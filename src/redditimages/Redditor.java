package redditimages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import redditimages.model.RedditPost;

public class Redditor {

  public List<RedditPost> getRedditFirstPageElements() {
    List<RedditPost> firstPagePosts = new ArrayList<>();
    try {
      Document doc = Jsoup.connect("http://www.reddit.com").get();
      Elements titles = doc.select(".entry");
      titles.iterator().forEachRemaining(ele -> {
        RedditPost post = new RedditPost();
        post.setTitleUrl(sanitizeUrl(ele.select("p a").attr("href")));
        post.setCommentUrl(sanitizeUrl(ele.select(".first a").attr("href")));
        firstPagePosts.add(post);
      });

    } catch (IOException e) {
      // TODO: unable to retrieve
    }
    return firstPagePosts;
  }

  public void updatePostsWithMoreInfo(List<RedditPost> posts) {
    posts.stream().forEach(p -> {
      updatePostWithMoreInfo(p);
    });
  }

  private void updatePostWithMoreInfo(RedditPost p) {
    try {
      System.out.println("title url:" + p.getTitleUrl());
      System.out.println("comment url:" + p.getCommentUrl());

      Document doc = Jsoup.connect(p.getCommentUrl()).get();
      Elements titles = doc.select("a");
      titles.iterator().forEachRemaining(imgElement -> {
        String linkUrl = imgElement.attr("href");
        if (linkUrl.startsWith("http") && !linkUrl.contains("reddit")) {
          System.out.println("\t" + linkUrl);
        }
      });
    } catch (IOException e) {
      // TODO: unable to retrieve
    }
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
