package org.piotrwyrw.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Tools {

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage grabImage(String path) {
        File f = new File("assets/" + path);
        if (!f.exists()) {
            System.out.println("Required image not found: 'assets/" + path + "'");
            System.exit(0);
        }
        Image i = null;
        try {
            i = ImageIO.read(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toBufferedImage(i);
    }

    public static BufferedImage grabScaledImage(String path, int w, int h) {
        BufferedImage img = grabImage(path);
        return toBufferedImage(img.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH));
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean premul = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, premul, null);
    }

    public static double clamp(double min, double max, double x) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

}
