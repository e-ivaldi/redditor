package redditimages.extractor;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import redditimages.extractor.factory.ExtractorsFactory;
import redditimages.extractor.jsoup.JsoupExtractorsFactory;
import redditimages.model.RedditGenericElement;
import redditimages.util.FileUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class RedditJsoupPostExtractorTest {

  private final static String POST_FILE_CONTENT_PATH = "/2egk5a.html";
  private final static String POST_URL_TEST = "http://www.reddit.com/r/pics/comments/2egk5a/fff";

  private ExtractorsFactory factory = new JsoupExtractorsFactory();
  private RedditPageExtractor extractor = factory.getPageExtractor(POST_URL_TEST);

  @Before
  public void setup() throws IOException {
    Connection connection = PowerMockito.mock(Connection.class);
    PowerMockito.mockStatic(Jsoup.class);
    PowerMockito.when(Jsoup.connect(POST_URL_TEST)).thenReturn(connection);
    PowerMockito.when(Jsoup.parse(Mockito.anyString())).thenCallRealMethod();
    String testContent = FileUtil.getFileContent(POST_FILE_CONTENT_PATH);
    Document document = Jsoup.parse(testContent);
    PowerMockito.when(connection.get()).thenReturn(document);
  }

  @Test
  public void getPostValidElementTest() {
    RedditGenericElement genericEle = extractor.getPost();
    Assert.assertNotNull(genericEle);
    Assert.assertNotNull(genericEle.getElement());
  }
  
  @Test
  public void getPostValidElementContentTest() {
    Element element = (Element) extractor.getPost().getElement();
    Elements elements = element.select(".commentarea");
    Assert.assertEquals(1, elements.size());
  }

}
