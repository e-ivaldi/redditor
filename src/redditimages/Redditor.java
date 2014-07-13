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
    List<RedditPost> posts = new ArrayList<>();
    RedditPageExtractor pageExtractor = extractorsFactory.getPageExtractor("http://www.reddit.com/");

    List<RedditGenericElement> pageElements = pageExtractor.getPagePosts(section.toString());
    pageElements.forEach(ele -> {
      RedditPostExtractor extractor = extractorsFactory.getPostExtractor(ele);
      RedditPost post = new RedditPost();
      post.setTitleUrl(extractor.getTitleUrl());
      post.setCommentUrl(extractor.getCommentUrl());
      post.setCommentsNumber(extractor.getCommentsNumber());
      System.out.println("RedditPost:" + post);
      posts.add(post);
    });

    return posts;
  }

  public void updatePostsWithMoreInfo(List<RedditPost> posts) {
    posts.stream().forEach(p -> {
      updatePostWithMoreInfo(p);
    });
  }

  public void updatePostWithMoreInfo(RedditPost p) {
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

  public List<RedditComment> getComments(RedditPost p) {
    List<RedditComment> comments = new ArrayList<>();
    return comments;
  }

}
