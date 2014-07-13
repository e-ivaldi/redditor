package redditimages.model.section;

public enum RedditSection {
  HOT(""),
  NEW("new"),
  RISING("rising"),
  CONTROVERSIAL("controversial"),
  TOP("rising"),
  GILDED("gilded"),
  WIKI("wiki"),
  PROMOTED("promoted");
  
  private String value;
  
  RedditSection(String value){
    this.value = value;
  }
  
  public String toString(){
    return value;
  }
  
}
