package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.configuration.ConfigurationLoader;
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
    
    private final ScreenManager screenManager;
    
    public GamingMenu(String[] args)
    {
        String configFile;
        try {
            configFile = args[0];
        } catch (ArrayIndexOutOfBoundsException exc) {
            System.err.println("Please specify a configuration file.");
            System.exit(1);
            throw new RuntimeException();
        }
        screenManager = new ScreenManager(ConfigurationLoader.loadConfiguration(configFile));
        
        createWindow(screenManager.getScreenWidth(), screenManager.getScreenHeight());
    }
    
    private void createWindow(int width, int height)
    {
        window = new JFrame(SOFTWARE_NAME);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        
        window.add(canvas);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        canvas.createBufferStrategy(2);
        
        canvas.addMouseMotionListener(screenManager);
        canvas.addKeyListener(screenManager);
        canvas.addMouseListener(screenManager);
    }
    
    public void start()
    {
        // Sure brings back memories.

        double delta = 0;
        long lastTime = System.nanoTime();
        
        canvas.requestFocus();
        
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
            screenManager.resetSwitchScreenRequest();
            
            window.dispose();
            createWindow(screenManager.getScreenWidth(), screenManager.getScreenHeight());
            canvas.requestFocus();
        }
    }
    
    private void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenManager.getScreenWidth(), screenManager.getScreenHeight());
        
        screenManager.render(g);
        
        g.dispose();
        bufferStrategy.show();
    }
}
