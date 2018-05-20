package org.seegee.photosorter.ui;

import static org.seegee.photosorter.context.ApplicationContext.configPanel;
import static org.seegee.photosorter.context.ApplicationContext.directoryViewerPanel;
import static org.seegee.photosorter.context.ApplicationContext.photoInfoPanel;
import static org.seegee.photosorter.context.ApplicationContext.photoViewerPanel;

import java.awt.*;

import javax.swing.*;

public class MainPanel extends JPanel {

  public MainPanel() {
    this.setBackground(Color.RED);
    this.setLayout(new BorderLayout());
    this.add(configPanel(), BorderLayout.NORTH);
    this.add(photoViewerPanel(), BorderLayout.CENTER);
    this.add(directoryViewerPanel(), BorderLayout.EAST);
    this.add(photoInfoPanel(), BorderLayout.SOUTH);
  }
}
