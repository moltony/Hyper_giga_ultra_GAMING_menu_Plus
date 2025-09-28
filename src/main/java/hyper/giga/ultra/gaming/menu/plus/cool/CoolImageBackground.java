package hyper.giga.ultra.gaming.menu.plus.cool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class CoolImageBackground implements CoolBackground
{
    private CoolImage image;
    private CoolImageBackgroundMode mode;
    private Color backgroundColor;
    
    public CoolImageBackground(CoolImage image, CoolImageBackgroundMode mode, Color backgroundColor)
    {
        this.image = image;
        this.mode = mode;
        this.backgroundColor = backgroundColor;
    }
    
    public void update()
    {
        image.update();
    }
    
    @Override
    public void render(Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setClip(new Rectangle(x, y, width, height));
        
        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);
        
        // since when did they add this syntax to java wtf
        switch (mode) {
            case Center -> {
                double targetX = ((double)x + (double)width / 2.0) - image.getScaledWidth() / 2.0;
                double targetY = ((double)y + (double)height / 2.0) - image.getScaledHeight() / 2.0;
                image.render(g, (int)targetX, (int)targetY);
            }
            case Tile -> {
                double scaledWidth = image.getScaledWidth();
                double scaledHeight = image.getScaledHeight();
                int countX = (int)Math.ceil((double)width / scaledWidth);
                int countY = (int)Math.ceil((double)height / scaledHeight);
                for (int i = 0; i < countX; i++) {
                    double targetX = (double)x + scaledWidth * (double)i;
                    for (int j = 0; j < countY; j++) {
                        double targetY = (double)y + scaledHeight * (double)j;
                        image.render(g, (int)targetX, (int)targetY);
                    }
                }
            }
            case Stretch -> {
                double scaleX = (double)width / image.getWidth();
                double scaleY = (double)height / image.getHeight();
                image.renderScaled(g, x, y, scaleX, scaleY);
            }
            case Zoom -> {
                double scaleX = (double)width / image.getWidth();
                double scaleY = (double)height / image.getHeight();
                double scale = Math.max(scaleX, scaleY);
                double xOverflow = (double)width - image.getWidth() * scale;
                double yOverflow = (double)height - image.getHeight() * scale;
                image.renderScaled(g, x + (int)xOverflow, y + (int)(yOverflow / 2), scale, scale);
            }
        }
        
        g2d.setClip(null);
    }
}
