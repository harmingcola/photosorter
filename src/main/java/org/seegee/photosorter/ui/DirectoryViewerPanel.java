package org.seegee.photosorter.ui;

import static org.seegee.photosorter.context.ApplicationContext.service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

public class DirectoryViewerPanel extends JScrollPane implements ActionListener {

  private List<DirectoryButton> buttons = new ArrayList();

  public void loadDirectories(File destination) {
    List<File> directories = Arrays.stream(destination.listFiles())
        .filter(File::isDirectory)
        .sorted()
        .collect(Collectors.toList());

    displayDirectories(directories);
  }

  private void displayDirectories(List<File> directories) {
    JPanel buttonsPanel = new JPanel(new GridLayout(directories.size(), 1));

    for(File directory : directories) {
      DirectoryButton button = new DirectoryButton(directory);
      button.addActionListener(this);
      buttonsPanel.add(button);
      buttons.add(button);
    }

    this.setViewportView(buttonsPanel);
    this.repaint();
    this.revalidate();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    DirectoryButton destinationDirectoryButton = (DirectoryButton) e.getSource();
    File destinationDirectory = destinationDirectoryButton.getDirectory();
    service().moveCurrentPhotoTo(destinationDirectory);
    service().reload();
  }

  public void enableMoving() {
    for(DirectoryButton button : buttons) {
      button.setEnabled(true);
    }
  }

  public void disableMoving() {
    for(DirectoryButton button : buttons) {
      button.setEnabled(false);
    }
  }
}
