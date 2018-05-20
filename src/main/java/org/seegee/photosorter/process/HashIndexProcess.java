package org.seegee.photosorter.process;

import static org.seegee.photosorter.context.ApplicationContext.hashIndex;

import java.io.File;

import org.seegee.photosorter.context.ApplicationContext;
import org.seegee.photosorter.utils.FileUtil;
import org.seegee.photosorter.utils.ImageUtil;

public class HashIndexProcess implements Runnable {

  private File startDirectory;

  public HashIndexProcess(File startDirectory) {
    this.startDirectory = startDirectory;
  }

  @Override
  public void run() {
    analyzeDirectory(startDirectory);
    ApplicationContext.photoInfoPanel().refresh();
  }

  private void analyzeDirectory(File startDirectory) {
    for(File file : startDirectory.listFiles()) {
      if(file.isDirectory()) {
        analyzeDirectory(file);
      } else {
        analyzeFile(file);
      }
    }
  }

  private void analyzeFile(File file) {
    if(ImageUtil.isImage(file)) {
      String hash = FileUtil.generateHash(file);
      hashIndex().put(hash, file);
    }
  }


}
