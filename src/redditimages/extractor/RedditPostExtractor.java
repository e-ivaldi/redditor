package redditimages.extractor;

import java.time.LocalDateTime;

public interface RedditPostExtractor {

  String getTitleUrl();

  int getCommentsNumber();

  String getCommentUrl();

  LocalDateTime getMostRecentPostDate();

  String getId();

}
