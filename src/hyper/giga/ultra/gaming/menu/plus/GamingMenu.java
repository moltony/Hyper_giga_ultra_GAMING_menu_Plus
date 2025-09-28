package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackgroundMode;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.ImageMenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.SeparatorMenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.TextMenuItem;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class GamingMenu
{
    private JFrame window;
    private Canvas canvas;
    private boolean running = true;
    
    public static final String SOFTWARE_NAME = "Hyper giga ultra GAMING menu Plus";
    
    private final double TARGET_FPS = 60.0;
    private final int DEFAULT_WIDTH = 640;
    private final int DEFAULT_HEIGHT = 480;
    
    private ScreenManager screenManager;
    
    public GamingMenu()
    {
        window = new JFrame(SOFTWARE_NAME);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
        window.add(canvas);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        canvas.createBufferStrategy(2);
        
        Screen[] screens = new Screen[] {
            new Screen(
                new MenuItem[] {
                    new SeparatorMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            Color.LIGHT_GRAY,
                            1,
                            80,
                            Alignment.Right, 10, 0
                    ),
                    new SeparatorMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            Color.LIGHT_GRAY,
                            1,
                            80,
                            Alignment.Left, 10, 0
                    ),
                    new SeparatorMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            Color.LIGHT_GRAY,
                            1,
                            80,
                            Alignment.Center, 0, 0
                    ),
                    new TextMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolText("this is the future", new Font("Comic Sans MS", Font.PLAIN, 24), Color.YELLOW, 25.0),
                            Alignment.Center, 0, 0
                    ),
                    new ImageMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/nadeshiko eat.gif"), 0.0, 0.4, 0.4, Color.WHITE, 5),
                            Alignment.Center, 0, 0
                    )
                },
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                new CoolImageBackground(new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/arknight girl middle finger.gif"), 0.0, 0.4, 0.4, Color.WHITE, 1), CoolImageBackgroundMode.Zoom)
            )
        };
        screenManager = new ScreenManager(screens);
        canvas.addMouseMotionListener(screenManager);
        canvas.addKeyListener(screenManager);
        canvas.addMouseListener(screenManager);
    }
    
    public void start()
    {
        // Sure brings back memories.

        double delta = 0;
        long lastTime = System.nanoTime();
        
        window.requestFocus();
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (1000000000 / TARGET_FPS);
            lastTime = now;
            
            while (delta >= 1) {
                update();
                delta--;
            }
            
            render();
        }
    }
    
    private void update()
    {
        screenManager.update();
    }
    
    private void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        screenManager.render(g);
        
        g.dispose();
        bufferStrategy.show();
    }

    private CoolImageBackgroundMode CoolImageBackgroundMode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
