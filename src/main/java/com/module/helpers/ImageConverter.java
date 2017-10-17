package com.module.helpers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    public static Image convertBytesToImage(byte[] imageBytes) {
        if (imageBytes != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            return new Image(bis);
        }
        return null;
    }

    public static byte[] convertImageToBytes(Image image) {

        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                BufferedImage originalImage = SwingFXUtils.fromFXImage(image, null);
                BufferedImage bImage = Thumbnails.of(originalImage)
                        .size(150, 200)
                        .outputQuality(0.8)
                        .asBufferedImage();

                ImageIO.write(bImage, "png", byteArrayOutputStream);
                byte[] res = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return res;
            } catch (IOException e) {

            }
        }
        return null;
    }
}
