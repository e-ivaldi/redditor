package redditimages.extractor.factory;

import redditimages.extractor.RedditPageExtractor;
import redditimages.extractor.RedditPostExtractor;
import redditimages.model.RedditGenericElement;

public interface ExtractorsFactory {
  
  RedditPostExtractor getPostExtractor(RedditGenericElement element);

  RedditPageExtractor getPageExtractor(String url);
  
}
