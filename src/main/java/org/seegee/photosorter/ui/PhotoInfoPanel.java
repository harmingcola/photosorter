package org.seegee.photosorter.ui;

import static org.seegee.photosorter.context.ApplicationContext.directoryViewerPanel;
import static org.seegee.photosorter.context.ApplicationContext.service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PhotoInfoPanel extends JPanel implements ActionListener {

  public static final String NONE = "None";
  private File currentPhoto;

  private JLabel labelFileCreatedText = new JLabel(" File Created : ");
  private JLabel labelFileCreated = new JLabel("");

  private JLabel labelSimilarFileText = new JLabel(" Match by Hash : ");
  private JLabel labelSimilarFile = new JLabel(NONE);

  private JLabel labelFileLocationText = new JLabel(" Location : ");
  private JLabel labelFileLocation = new JLabel("");

  private JLabel labelFileSizeText = new JLabel(" Size : ");
  private JLabel labelFileSize = new JLabel("");

  private JButton buttonDelete = new JButton("Delete");
  private JButton buttonNewFolder = new JButton("New Folder");

  public PhotoInfoPanel() {
    this.setBackground(Color.GREEN);
    this.setLayout(new BorderLayout());

    JPanel infoPanelOuter = new JPanel(new BorderLayout());
    JPanel infoPanelInner = new JPanel(new GridLayout(4, 1));

    JPanel panelFileSize = new JPanel(new BorderLayout());
    panelFileSize.add(labelFileSizeText, BorderLayout.WEST);
    panelFileSize.add(labelFileSize, BorderLayout.CENTER);
    infoPanelInner.add(panelFileSize);

    JPanel panelCreatedAt = new JPanel(new BorderLayout());
    panelCreatedAt.add(labelFileCreatedText, BorderLayout.WEST);
    panelCreatedAt.add(labelFileCreated, BorderLayout.CENTER);
    infoPanelInner.add(panelCreatedAt);

    JPanel panelFileLocation = new JPanel(new BorderLayout());
    panelFileLocation.add(labelFileLocationText, BorderLayout.WEST);
    panelFileLocation.add(labelFileLocation, BorderLayout.CENTER);
    infoPanelInner.add(panelFileLocation);

    JPanel panelMatchByHash = new JPanel(new BorderLayout());
    panelMatchByHash.add(labelSimilarFileText, BorderLayout.WEST);
    panelMatchByHash.add(labelSimilarFile, BorderLayout.CENTER);
    infoPanelInner.add(panelMatchByHash);

    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    buttonPanel.add(buttonDelete);
    buttonPanel.add(buttonNewFolder);
    buttonDelete.addActionListener(this);
    buttonNewFolder.addActionListener(this);

    infoPanelOuter.add(buttonPanel, BorderLayout.EAST);
    infoPanelOuter.add(infoPanelInner, BorderLayout.CENTER);
    this.add(infoPanelOuter);

  }

  public void setPhoto(File photo) {
    this.currentPhoto = photo;

    // Display file created time
    try {
      BasicFileAttributes attr = Files.readAttributes(photo.toPath(), BasicFileAttributes.class);
      labelFileCreated.setText(attr.creationTime().toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Display file path
    labelFileLocation.setText(photo.getPath());

    // Display file size
    labelFileSize.setText(getFileSizeText(photo));

    // Check file already exists at destination
    Optional<File> maybeFile = service().checkFileExists(photo);
    if (maybeFile.isPresent()) {
      labelSimilarFile.setText(maybeFile.get().getPath());
      directoryViewerPanel().disableMoving();
    } else {
      labelSimilarFile.setText(NONE);
      directoryViewerPanel().enableMoving();
    }
  }

  private String getFileSizeText(File file) {
    try {
      /*
       * Get size on disk
       */
      String sizeOnDisk;
      if (file.length() / 1000 < 1000) {
        sizeOnDisk = String.format("%dkb", file.length() / 1000);
      } else {
        sizeOnDisk = String.format("%dmb", file.length() / 1000000);
      }

      /*
       * Build image for sizes
       */
      BufferedImage image = ImageIO.read(file);

      /*
       * Format text
       */
      return String.format("%dpx X %dpx : %s", image.getHeight(), image.getWidth(), sizeOnDisk);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Unable to determine";
  }

  public void clear() {
    labelFileCreated.setText("NO FILE SELECTED");
    labelSimilarFile.setText("");
    labelFileSize.setText("");
    labelFileLocation.setText("");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(buttonDelete)) {
      service().deletePhoto(currentPhoto);
    }
    if(e.getSource().equals(buttonNewFolder)) {
      service().newFolder();
    }
  }

  public void refresh() {
    this.setPhoto(this.currentPhoto);
  }
}
