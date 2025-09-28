package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolGradientBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackgroundMode;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.ExitMenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.LauncherMenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.SwitchScreenMenuItem;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

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
                    new SwitchScreenMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/arknight girl middle finger.gif"), 0.0, 0.4, 0.4, Color.WHITE, 2),
                            Alignment.Left, 0, 0,
                            new CoolText("Click here", new Font("Monospace", Font.PLAIN, 24), Color.WHITE, 0.0),
                            Alignment.Left, 200, 20,
                            1
                    )
                },
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                new CoolGradientBackground(new Color[] {Color.CYAN, Color.MAGENTA, Color.YELLOW}, new float[] {0.0f, 0.5f, 1.0f}, 0)
            ),
            new Screen(
                new MenuItem[] {
                    new LauncherMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/bandori girl hoeh.gif"), 0.0, 0.4, 0.4, Color.WHITE, 1),
                            Alignment.Left, 0, 0,
                            new CoolText("this is the future", new Font("Comic Sans MS", Font.PLAIN, 24), Color.YELLOW, -10.0),
                            Alignment.Left, 100, 20,
                            new String[] {"ristretto", "/home/moltony/Documents/picture"},
                            "/home/moltony",
                            true
                    ),
                    new ExitMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolImage("/home/moltony/Documents/picture/Blue_circled_9.svg.png", 0.0, 0.4, 0.4, Color.WHITE),
                            Alignment.Left, 0, 0,
                            new CoolText("exit", new Font("Comic Sans MS", Font.PLAIN, 24), Color.YELLOW, -10.0),
                            Alignment.Left, 100, 20
                    ),
                    new SwitchScreenMenuItem(
                            MenuItem.BACKGROUND_NORMAL_DEFAULT,
                            MenuItem.BACKGROUND_SELECTED_DEFAULT,
                            new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/arknight girl middle finger.gif"), 0.0, 0.4, 0.4, Color.WHITE, 2),
                            Alignment.Left, 0, 0,
                            new CoolText("Click here", new Font("Monospace", Font.PLAIN, 24), Color.WHITE, 0.0),
                            Alignment.Left, 200, 20,
                            0
                    )
                },
                DEFAULT_WIDTH * 2,
                DEFAULT_HEIGHT * 2,
                new CoolImageBackground(new CoolImage(AnimationLoader.loadGIF("/home/moltony/Documents/picture/nadeshiko eat.gif"), 0.0, 2.0, 2.0, Color.WHITE, 5), CoolImageBackgroundMode.Center)
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
        
        window.dispose();
    }
    
    private void update()
    {
        screenManager.update();
        
        if (screenManager.isCloseRequested()) {
            running = false;
        } else if (screenManager.isSwitchScreenRequested()) {
            int screenID = screenManager.getRequestedScreenID();
            screenManager.setCurrentScreen(screenID);
            window.setSize(new Dimension(screenManager.getScreenWidth(), screenManager.getScreenHeight()));
            screenManager.resetSwitchScreenRequest();
        }
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
}
