package org.seegee.photosorter.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.seegee.photosorter.utils.ImageUtil;

public class PhotoViewerPanel extends JPanel {

  private File currentPhoto;

  public PhotoViewerPanel() {
    this.setLayout(new BorderLayout());
  }

  public void setPhoto(File photoFile) {
    this.currentPhoto = photoFile;
    try {
      Image image = ImageIO.read(photoFile);
      image = ImageUtil.scaleImage(image, BufferedImage.TYPE_INT_RGB, this.getWidth(), this.getHeight());
      ImageIcon imageIcon = new ImageIcon(image);
      JLabel label = new JLabel("", imageIcon, JLabel.CENTER);
      this.removeAll();
      this.add(label, BorderLayout.CENTER);
      this.revalidate();
      this.repaint();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void clearPhoto() {
    this.removeAll();
    this.revalidate();
    this.repaint();
  }

  public File getCurrentPhoto() {
    return currentPhoto;
  }
}
