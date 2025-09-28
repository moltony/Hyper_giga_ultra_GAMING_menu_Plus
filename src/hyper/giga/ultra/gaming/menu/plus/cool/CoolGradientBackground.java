package hyper.giga.ultra.gaming.menu.plus.cool;

import java.awt.*;
import java.awt.geom.Point2D;

public class CoolGradientBackground implements CoolBackground
{
    private Color[] colors;
    private float[] fractions;
    private double angle;
    
    public CoolGradientBackground(Color[] colors, float[] fractions, double angle)
    {
        if (colors.length != fractions.length) {
            throw new IllegalArgumentException("Colors and fractions must have the same length");
        }
        
        this.colors = colors.clone();
        this.fractions = fractions.clone();
        this.angle = angle;
    }
    
    @Override
    public void render(Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2d = (Graphics2D)g;
        
        double dx = Math.cos(Math.toRadians(angle)) * width;
        double dy = Math.sin(Math.toRadians(angle)) * height;
        
        Point2D start = new Point2D.Float(x, y);
        Point2D end = new Point2D.Float((float)(x + dx), (float)(y + dy));
        
        LinearGradientPaint paint = new LinearGradientPaint(start, end, fractions, colors);
        g2d.setPaint(paint);
        g2d.fillRect(x, y, width, height);
    }
}
