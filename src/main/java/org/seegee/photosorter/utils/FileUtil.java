package org.seegee.photosorter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {

  public static String generateHash(File file) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      return org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
