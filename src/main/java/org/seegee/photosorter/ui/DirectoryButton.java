package org.seegee.photosorter.ui;

import java.io.File;

import javax.swing.*;

public class DirectoryButton extends JButton {

  private final File directory;

  public DirectoryButton(File directory) {
    super(directory.getName());
    this.directory = directory;
    this.setHorizontalAlignment(SwingConstants.LEFT);
  }

  public File getDirectory() {
    return directory;
  }
}
