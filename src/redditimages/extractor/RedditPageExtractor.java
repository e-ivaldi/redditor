package redditimages.extractor;

import java.util.List;

import redditimages.model.RedditGenericElement;

public interface RedditPageExtractor {

  List<RedditGenericElement> getPagePosts(String section);
  
}
