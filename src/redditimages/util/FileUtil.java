package redditimages.util;

import java.io.InputStream;
import java.util.Scanner;

public class FileUtil {

  public static String getFileContent(String filePath) {
    InputStream in = FileUtil.class.getResourceAsStream(filePath);
    Scanner scanner = new Scanner(in, "UTF-8");
    String result = scanner.useDelimiter("\\A").next();
    scanner.close();
    return result;
  }
}
