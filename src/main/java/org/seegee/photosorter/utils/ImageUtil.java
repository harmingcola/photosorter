package org.seegee.photosorter.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtil {

  public static boolean isImage(File file) {
    String fileName = file.getName().toLowerCase();
    return fileName.endsWith("jpg")
        || fileName.endsWith("jpeg")
        || fileName.endsWith("png");
  }

  public static Image scaleImage(Image image, int imageType, int newWidth, int newHeight) {
    double thumbRatio = (double) newWidth / (double) newHeight;
    int imageWidth = image.getWidth(null);
    int imageHeight = image.getHeight(null);
    double aspectRatio = (double) imageWidth / (double) imageHeight;

    if (thumbRatio < aspectRatio) {
      newHeight = (int) (newWidth / aspectRatio);
    } else {
      newWidth = (int) (newHeight * aspectRatio);
    }

    BufferedImage newImage = new BufferedImage(newWidth, newHeight, imageType);
    Graphics2D graphics2D = newImage.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

    return newImage;
  }
}
