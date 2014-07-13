package redditimages;

import java.util.List;

import redditimages.model.RedditPost;

public class Main {

  public static class App {

    public void start() {
      Redditor redditor = new Redditor();
      List<RedditPost> frontPagePosts = redditor.getRedditFirstPageElements();
      redditor.updatePostsWithMoreInfo(frontPagePosts);
    }
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
