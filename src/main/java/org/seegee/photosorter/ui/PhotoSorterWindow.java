package org.seegee.photosorter.ui;

import javax.swing.*;

public class PhotoSorterWindow extends JFrame {

  public PhotoSorterWindow() {
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setContentPane(new MainPanel());
    this.pack();
    this.setVisible(true);
    this.setSize(1000, 800);
  }
}
