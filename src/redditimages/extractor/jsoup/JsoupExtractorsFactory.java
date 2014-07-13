package redditimages.extractor.jsoup;

import redditimages.extractor.RedditPageExtractor;
import redditimages.extractor.RedditPostExtractor;
import redditimages.extractor.factory.ExtractorsFactory;
import redditimages.model.RedditGenericElement;

public class JsoupExtractorsFactory implements ExtractorsFactory {

  @Override
  public RedditPageExtractor getPageExtractor(String section) {
    return new RedditJsoupPageExtractor(section);
  }

  @Override
  public RedditPostExtractor getPostExtractor(RedditGenericElement element) {
    return new RedditJsoupPostExtractor(element);
  }

}
