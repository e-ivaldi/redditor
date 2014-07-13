package redditimages.model;

import org.jsoup.nodes.Element;

public class RedditGenericElement {

  private Element element;

  public RedditGenericElement(Element element) {
    this.element = element;
  }

  public Element getJsoupElement() {
    return element;
  }

  public void setElement(Element element) {
    this.element = element;
  }

}
