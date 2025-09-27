package hyper.giga.ultra.gaming.menu.plus.cool;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class CoolText
{
    public String text;
    public Font font;
    public Color color;
    public double angle;
    
    public CoolText(String text, Font font, Color color, double angle)
    {
        this.text = text;
        this.font = font;
        this.color = color;
        this.angle = angle;
    }
    
    public void render(Graphics g, int x, int y)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setFont(font);
        g2d.setColor(color);
        
        AffineTransform oldTransform = g2d.getTransform();
        
        g2d.rotate(Math.toRadians(angle), x, y);
        g2d.drawString(text, x, y);
        
        g2d.setTransform(oldTransform);
    }
}
