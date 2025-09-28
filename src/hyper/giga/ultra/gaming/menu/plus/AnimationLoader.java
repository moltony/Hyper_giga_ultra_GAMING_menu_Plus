package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

public class AnimationLoader
{
    public static BufferedImage[] loadGIF(String path)
    {
        try {
            File file = new File(path);
            ImageInputStream stream = ImageIO.createImageInputStream(file);

            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
            if (!readers.hasNext()) {
                throw new IOException("No GIF readers available");
            }

            ImageReader reader = readers.next();
            reader.setInput(stream);

            int numFrames = reader.getNumImages(true);
            ArrayList<BufferedImage> frames = new ArrayList<>();

            for (int i = 0; i < numFrames; i++) {
                frames.add(reader.read(i));
            }

            reader.dispose();

            BufferedImage[] frameArray = frames.toArray(BufferedImage[]::new);

            // convert them to argb images
            for (int i = 0; i < frameArray.length; i++) {
                BufferedImage source = frameArray[i];
                BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = copy.createGraphics();
                g.drawImage(source, 0, 0, null);
                g.dispose();
                frameArray[i] = copy;
            }
            return frameArray;
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(null, String.format("Unable to load GIF file %s: %s", path, exc.toString()), GamingMenu.SOFTWARE_NAME, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return null;
        }
    }
}
