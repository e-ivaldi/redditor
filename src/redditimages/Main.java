package redditimages;

import java.util.List;

import redditimages.extractor.factory.ExtractorsFactory;
import redditimages.extractor.jsoup.JsoupExtractorsFactory;
import redditimages.model.RedditPost;
import redditimages.model.section.RedditSection;

public class Main {

  public static void main(String[] args) {
    ExtractorsFactory extractorsFactory = new JsoupExtractorsFactory();
    Redditor redditor = new Redditor(extractorsFactory);
    List<RedditPost> frontPagePosts = redditor.getFirstPagePosts(RedditSection.HOT);
    //redditor.updatePostsWithMoreInfo(frontPagePosts);
  }

}
