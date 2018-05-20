package org.seegee.photosorter.service;

import static org.seegee.photosorter.context.ApplicationContext.directoryViewerPanel;
import static org.seegee.photosorter.context.ApplicationContext.photoInfoPanel;
import static org.seegee.photosorter.context.ApplicationContext.photoViewerPanel;
import static org.seegee.photosorter.context.ApplicationContext.service;

import java.io.File;
import java.util.Optional;

import javax.swing.*;

import org.seegee.photosorter.context.ApplicationContext;
import org.seegee.photosorter.process.HashIndexProcess;
import org.seegee.photosorter.utils.FileUtil;
import org.seegee.photosorter.utils.ImageUtil;

public class Service {

  private File currentSource;
  private File currentDestination;

  private HashIndexProcess hashIndexProcess;

  /*
   * Public Functions
   */
  public void reload(File source, File destination) {
    this.currentSource = source;
    this.currentDestination = destination;

    File photo = loadPhoto(source);
    if (photo!=null) {
      photoViewerPanel().setPhoto(photo);
      photoInfoPanel().setPhoto(photo);
    } else {
      photoViewerPanel().clearPhoto();
      photoInfoPanel().clear();
    }

    if(hashIndexProcess == null) {
      hashIndexProcess = new HashIndexProcess(currentDestination);
      new Thread(hashIndexProcess).start();
    }
  }

  public void reload() {
    reload(this.currentSource, this.currentDestination);
  }

  public void deletePhoto(File currentPhoto) {
    moveToTrash(currentPhoto, currentSource);
    reload();
  }

  public void moveCurrentPhotoTo(File destinationDirectory) {
    File currentPhoto = photoViewerPanel().getCurrentPhoto();
    File destinationFile = new File(destinationDirectory.getPath() + "/" + currentPhoto.getName());
    currentPhoto.renameTo(destinationFile);
    service().addFileToIndex(destinationFile);
  }

  public Optional<File> checkFileExists(File file) {
    String hash = FileUtil.generateHash(file);
    return Optional.ofNullable(ApplicationContext.hashIndex().get(hash));
  }

  /*
   * Private functions
   */

  private void addFileToIndex(File file) {
    String hash = FileUtil.generateHash(file);
    ApplicationContext.hashIndex().put(hash, file);
  }

  private File loadPhoto(File source) {
    if(source.listFiles().length == 0) {
      return null;
    }
    if(source.listFiles()[0].isDirectory()) {
      this.loadPhoto(source.listFiles()[0]);
    }
    for(File dirItem : source.listFiles()) {
      if(ImageUtil.isImage(dirItem)) {
        return dirItem;
      }
    }
    return null;
  }

  private void moveToTrash(File photo, File destination) {
    File trashLocation = new File(destination.getPath() + "/trash");
    if(!trashLocation.exists()) {
      trashLocation.mkdir();
    }

    File trashedPhoto = new File(trashLocation + "/" + photo.getName());
    photo.renameTo(trashedPhoto);
  }

  public void newFolder() {
    String path = JOptionPane.showInputDialog("Enter new folder name");
    if(path != null && currentDestination != null) {
      File newFolder = new File(currentDestination.getPath() +"/" + path);
      if(!newFolder.exists()) {
        newFolder.mkdir();
      }
    }
    directoryViewerPanel().loadDirectories(currentDestination);
  }
}
