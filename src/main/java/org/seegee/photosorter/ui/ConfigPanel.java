package org.seegee.photosorter.ui;

import static org.seegee.photosorter.context.ApplicationContext.directoryViewerPanel;
import static org.seegee.photosorter.context.ApplicationContext.service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ConfigPanel extends JPanel implements ActionListener {

  private JLabel labelSource = new JLabel(" Source Directory ");
  private JLabel labelDestination = new JLabel(" Target Directory ");

  private JTextField inputSource = new JTextField("/Users/cgraham/workspace/hubspot/photosorter/src/test/resources/source");
  private JTextField inputDestination = new JTextField("/Users/cgraham/workspace/hubspot/photosorter/src/test/resources/destination");

  private JButton buttonLoad = new JButton("Load");
  private JButton buttonSourcePicker = new JButton("Source");
  private JButton buttonDestinationPicker = new JButton("Destination");

  public ConfigPanel() {

    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());

    JPanel panelLabels = new JPanel(new GridLayout(2,1));
    panelLabels.add(labelSource);
    panelLabels.add(labelDestination);
    this.add(panelLabels, BorderLayout.WEST);

    JPanel panelDirectoryLabels = new JPanel(new GridLayout(2,1));
    panelDirectoryLabels.add(inputSource);
    panelDirectoryLabels.add(inputDestination);
    this.add(panelDirectoryLabels, BorderLayout.CENTER);

    JPanel panelChooseDirectories = new JPanel(new GridLayout(2, 1));
    panelChooseDirectories.add(buttonSourcePicker);
    panelChooseDirectories.add(buttonDestinationPicker);

    JPanel panelEventButtons = new JPanel(new GridLayout(1,1));
    panelEventButtons.add(buttonLoad);

    JPanel panelButtons = new JPanel(new GridLayout(1, 2));
    panelButtons.add(panelChooseDirectories);
    panelButtons.add(panelEventButtons);

    this.add(panelButtons, BorderLayout.EAST);

    buttonLoad.addActionListener(this);
    buttonSourcePicker.addActionListener(this);
    buttonDestinationPicker.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(buttonLoad)) {
      File source = new File(inputSource.getText());
      File destination = new File(inputDestination.getText());
      service().reload(source, destination);
      directoryViewerPanel().loadDirectories(destination);
    }
    if(e.getSource().equals(buttonSourcePicker)) {
      inputSource.setText(pickDirectory(inputSource.getText()));
    }
    if(e.getSource().equals(buttonDestinationPicker)) {
      inputDestination.setText(pickDirectory(inputDestination.getText()));
    }
  }

  private String pickDirectory(String startDirectory) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(startDirectory));
    chooser.setDialogTitle("Select directory");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.showOpenDialog(this);
    return chooser.getSelectedFile().getPath();
  }
}
