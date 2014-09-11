package redditimages.model;

import java.time.LocalDateTime;
import java.util.Set;

public class RedditPost {

  private String id;
  private String titleUrl;
  private String commentUrl;
  private LocalDateTime mostRecentPostDate;
  private int commentsNumber;
  private Set<String> imageLinks;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitleUrl() {
    return titleUrl;
  }

  public void setTitleUrl(String titleUrl) {
    this.titleUrl = titleUrl;
  }

  public String getCommentUrl() {
    return commentUrl;
  }

  public void setCommentUrl(String commentUrl) {
    this.commentUrl = commentUrl;
  }

  public LocalDateTime getMostRecentPostDate() {
    return mostRecentPostDate;
  }

  public void setMostRecentPostDate(LocalDateTime mostRecentPostDate) {
    this.mostRecentPostDate = mostRecentPostDate;
  }

  public int getCommentsNumber() {
    return commentsNumber;
  }

  public void setCommentsNumber(int commentsNumber) {
    this.commentsNumber = commentsNumber;
  }

  public Set<String> getImageLinks() {
    return imageLinks;
  }

  public void setImageLinks(Set<String> imageLinks) {
    this.imageLinks = imageLinks;
  }

  @Override
  public String toString() {
    return "RedditPost [id=" + id + ", titleUrl=" + titleUrl + ", commentUrl=" + commentUrl + ", mostRecentPostDate="
        + mostRecentPostDate + ", commentsNumber=" + commentsNumber + "]";
  }

}
