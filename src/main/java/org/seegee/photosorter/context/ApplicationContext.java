package org.seegee.photosorter.context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.seegee.photosorter.service.Service;
import org.seegee.photosorter.ui.ConfigPanel;
import org.seegee.photosorter.ui.DirectoryViewerPanel;
import org.seegee.photosorter.ui.PhotoInfoPanel;
import org.seegee.photosorter.ui.PhotoViewerPanel;

public class ApplicationContext {

  /*
   * Panels
   */
  private static PhotoViewerPanel photoViewerPanel;
  public static PhotoViewerPanel photoViewerPanel() {
    if(photoViewerPanel == null) {
      photoViewerPanel = new PhotoViewerPanel();
    }
    return photoViewerPanel;
  }

  private static PhotoInfoPanel photoInfoPanel;
  public static PhotoInfoPanel photoInfoPanel() {
    if(photoInfoPanel == null) {
      photoInfoPanel = new PhotoInfoPanel();
    }
    return photoInfoPanel;
  }

  private static ConfigPanel configPanel;
  public static ConfigPanel configPanel() {
    if(configPanel == null) {
      configPanel = new ConfigPanel();
    }
    return configPanel;
  }

  private static DirectoryViewerPanel directoryViewerPanel;
  public static DirectoryViewerPanel directoryViewerPanel() {
    if(directoryViewerPanel == null) {
      directoryViewerPanel = new DirectoryViewerPanel();
    }
    return directoryViewerPanel;
  }

  /*
   * Service
   */
  private static Service service;
  public static Service service() {
    if(service == null) {
      service = new Service();
    }
    return service;
  }

  /*
   * Shared memory
   */
  private static Map<String, File> hashIndex;
  public static Map<String, File> hashIndex() {
    if(hashIndex == null) {
      hashIndex = new HashMap();
    }
    return hashIndex;
  }
}
