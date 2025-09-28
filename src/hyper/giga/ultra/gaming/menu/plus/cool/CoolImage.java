package hyper.giga.ultra.gaming.menu.plus.cool;

import hyper.giga.ultra.gaming.menu.plus.ErrorHandler;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CoolImage
{
    private BufferedImage[] images;
    private double angle;
    private double scaleX, scaleY;
    private Color tint;
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
            ErrorHandler.handleError(exc, String.format("Failed to load image %s", imagePath));
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
        renderScaled(g, x, y, scaleX, scaleY);
    }
    
    public void renderScaled(Graphics g, int x, int y, double scaleX, double scaleY)
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
        g2d.drawImage(images[currentFrame], op, (int)((double)x / scaleX), (int)((double)y / scaleY)); // something something scaling
        
        g2d.setTransform(oldTransform);
    }
    
    public double getWidth()
    {
        return (double)images[currentFrame].getWidth();
    }
    
    public double getHeight()
    {
        return (double)images[currentFrame].getHeight();
    }
    
    public double getScaledWidth()
    {
        return getWidth() * scaleX;
    }
    
    public double getScaledHeight()
    {
        return getHeight() * scaleY;
    }
    
    public double getScaleX()
    {
        return scaleX;
    }
    
    public double getScaleY()
    {
        return scaleY;
    }
}
