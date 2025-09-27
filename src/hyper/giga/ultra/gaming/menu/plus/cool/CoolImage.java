package hyper.giga.ultra.gaming.menu.plus.cool;

import hyper.giga.ultra.gaming.menu.plus.GamingMenu;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class CoolImage
{
    public BufferedImage[] images;
    public double angle;
    public double scaleX, scaleY;
    public Color tint;
    
    private int currentFrame;
    private int tickCounter;
    private int frameDelay;
    
    public CoolImage(BufferedImage[] images, double angle, double scaleX, double scaleY, Color tint, int frameDelay)
    {
        this.images = images;
        this.angle = angle;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.tint = tint;
        this.frameDelay = frameDelay;
        this.tickCounter = 0;
        this.currentFrame = 0;
    }
    
    public CoolImage(BufferedImage image, double angle, double scaleX, double scaleY, Color tint)
    {
        this.images = new BufferedImage[] {image};
        this.angle = angle;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.tint = tint;
        this.frameDelay = 0;
        this.tickCounter = 0;
        this.currentFrame = 0;
    }
    
    // For single images only. See AnimationLoader for loading APNG and GIF files.
    public CoolImage(String imagePath, double angle, double scaleX, double scaleY, Color tint)
    {
        try {
            this.images = new BufferedImage[] {ImageIO.read(new File(imagePath))};
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(null, String.format("Failed to load image %s: %s", imagePath, exc.toString()), GamingMenu.SOFTWARE_NAME, JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        this.angle = angle;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.tint = tint;
        this.frameDelay = 0;
        this.tickCounter = 0;
        this.currentFrame = 0;
    }
    
    public void update()
    {
        tickCounter++;
        if (tickCounter >= frameDelay) {
            tickCounter = 0;
            currentFrame = (currentFrame + 1) % images.length;
        }
    }
    
    public void render(Graphics g, int x, int y)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        float red = tint.getRed() / 255f;
        float green = tint.getGreen() / 255f;
        float blue = tint.getBlue() / 255f;
        float alpha = tint.getAlpha() / 255f;
        
        float[] scales = {red, green, blue, alpha};
        float[] offsets = {0f, 0f, 0f, 0f};
        
        RescaleOp op = new RescaleOp(scales, offsets, null);
        
        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(angle), x + (images[currentFrame].getWidth() * scaleX) / 2.0, y + (images[currentFrame].getHeight() * scaleY) / 2.0);
        g2d.scale(scaleX, scaleY);
        g2d.drawImage(images[currentFrame], op, x, y);
        
        g2d.setTransform(oldTransform);
    }
}
