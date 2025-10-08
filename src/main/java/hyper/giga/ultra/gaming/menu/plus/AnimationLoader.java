package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AnimationLoader
{
    private static BufferedImage copyImage(BufferedImage source)
    {
        // https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
        ColorModel colorModel = source.getColorModel();
        boolean isAlphaPremultiplied = source.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }
    
    // finally, a use for my PhD in graphics interchange format
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
            
            int maxWidth = 0, maxHeight = 0;
            for (int i = 0; i < numFrames; i++) {
                maxWidth = Math.max(reader.getWidth(i), maxWidth);
                maxHeight = Math.max(reader.getHeight(i), maxHeight);
            }
            BufferedImage image = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            
            BufferedImage[] converted = new BufferedImage[frameArray.length];
            for (int i = 0; i < frameArray.length; i++) {
                BufferedImage src = frameArray[i];

                IIOMetadata meta = reader.getImageMetadata(i);
                String formatName = meta.getNativeMetadataFormatName();
                Node root = meta.getAsTree(formatName);
                NodeList children = root.getChildNodes();
                
                // Graphics control extension
                // TODO pretty sure it handles these automatically.
                boolean transparentColorFlag = false;
                int transparentColorIndex = 0;
                
                // Image descriptor
                int imageLeftPosition = 0;
                int imageTopPosition = 0;
                int imageWidth = 0;
                int imageHeight = 0;
                
                // Loop through metadata nodes. This is so Java I can't.
                for (int j = 0; j < children.getLength(); j++) {
                    Node node = children.item(j);
                    if (node != null) {
                        if (node.hasAttributes()) {
                            NamedNodeMap map = node.getAttributes();
                            for (int k = 0; k < map.getLength(); k++) {
                                Node attribute = map.item(k);
                                String attributeName = attribute.getNodeName();
                                String attributeValue = attribute.getNodeValue();
                                
                                switch (node.getNodeName()) {
                                    case "GraphicsControlExtension" -> {
                                        switch (attributeName) {
                                            // This thing controls how to dispose the frame
                                            // See also: https://webreference.com.cach3.com/content/studio/disposal.html
                                            case "disposalMethod" -> {
                                                // TODO i don't have a gif with non-doNotDispose method tehepero
                                            }
                                            
                                            // userInputFlag is supposed to be 'user interact with image, advance to next frame'
                                            // but no one cared lmao
                                            
                                            // TODO handle delayTime
                                            // Currently in CoolImage it's constant and, worst of all, set by the user.
                                            // We don't trust the user btw.
                                            
                                            // Whether the frame has transparency.
                                            case "transparentColorFlag" -> {
                                                transparentColorFlag = "TRUE".equals(attributeValue);
                                            }
                                            
                                            // Which color in the palette is transparent.
                                            case "transparentColorIndex" -> {
                                                transparentColorIndex = Integer.parseInt(attributeValue);
                                            }
                                        }
                                    }
                                    case "ImageDescriptor" -> {
                                        switch (attributeName) {
                                            case "imageLeftPosition" -> {
                                                imageLeftPosition = Integer.parseInt(attributeValue);
                                            }
                                            case "imageTopPosition" -> {
                                                imageTopPosition = Integer.parseInt(attributeValue);
                                            }
                                            case "imageWidth" -> {
                                                imageWidth = Integer.parseInt(attributeValue);
                                            }
                                            case "imageHeight" -> {
                                                imageHeight = Integer.parseInt(attributeValue);
                                            }
                                            
                                            // interlaceFlag controls whether this frame is interlaced or not.
                                            // IIO handles this so I don't even have to do anything.
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                g.drawImage(src, imageLeftPosition, imageTopPosition, null);
                converted[i] = copyImage(image);
            }

            g.dispose();
            return converted;
        } catch (IOException exc) {
            ErrorHandler.handleError(exc, exc.toString());
            return null;
        }
    }
}
