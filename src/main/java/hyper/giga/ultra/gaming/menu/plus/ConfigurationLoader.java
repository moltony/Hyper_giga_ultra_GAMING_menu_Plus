package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public class ConfigurationLoader
{
    // Default configuration values
    private static final CoolSolidColorBackground DEFAULT_SCREEN_BACKGROUND = new CoolSolidColorBackground(Color.BLACK);
    private static final int DEFAULT_SCREEN_WIDTH = 480;
    private static final int DEFAULT_SCREEN_HEIGHT = 360;
    
    private static void showConfigurationError(String configurationFile, String error)
    {
        JOptionPane.showMessageDialog(null, String.format("Errors found when parsing configuration file %s: %s", configurationFile, error), GamingMenu.SOFTWARE_NAME, JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
    
    private static int parseColorValue(String configurationFile, TomlTable colorTable, String colorKey)
    {
        int value = colorTable.getLong(colorKey).intValue();
        if (value > 255 || value < 0) {
            showConfigurationError(configurationFile, String.format("Invalid color value: %d", value));
            return -1;
        }
        
        return value;
    }
    
    private static CoolBackground loadBackground(String configurationFile, TomlTable screenTable)
    {
        CoolBackground background = DEFAULT_SCREEN_BACKGROUND;
        
        TomlTable backgroundTable = screenTable.getTable("background");
        if (backgroundTable == null) {
            return background;
        }
        
        String backgroundType = backgroundTable.getString("type");
        if (backgroundType == null) {
            showConfigurationError(configurationFile, "Background must have a type");
        }
        
        switch (backgroundType) {
            case "solid" -> {
                TomlTable colorTable = backgroundTable.getTable("color");
                int r = parseColorValue(configurationFile, colorTable, "r");
                int g = parseColorValue(configurationFile, colorTable, "g");
                int b = parseColorValue(configurationFile, colorTable, "b");
                background = new CoolSolidColorBackground(new Color(r, g, b));
            }
            case "gradient" -> {
                
            }
            case "image" -> {
                
            }
            default -> {
                showConfigurationError(configurationFile, String.format("Invalid background type %s", backgroundType));
            }
        }
        
        return background;
    }
    
    public static Screen[] loadConfiguration(String configurationFile)
    {
        try {
            Path source = Paths.get(configurationFile);
            TomlParseResult result = Toml.parse(source);
            
            LinkedList<String> errorStrings = new LinkedList<>();
            result.errors().forEach(error -> {
                errorStrings.add(error.toString());
            });
            if (!errorStrings.isEmpty()) {
                showConfigurationError(configurationFile, String.join(", ", errorStrings));
                return null;
            }
            
            LinkedList<Screen> screens = new LinkedList<>();
            
            for (int i = 0;; i++) {
                TomlTable screenTable = result.getTable(String.format("screen%d", i));
                
                // If the screen table is null, that means there are no more screens.
                if (screenTable == null) {
                    break;
                }
                
                // Parse screen dimensions
                TomlTable sizeTable = screenTable.getTable("size");
                int screenWidth;
                if (sizeTable.get("w") != null) {
                    screenWidth = sizeTable.getLong("w").intValue();
                    if (screenWidth <= 0) {
                        showConfigurationError(configurationFile, "Screen width cannot be negative or zero");
                    }
                } else {
                    screenWidth = DEFAULT_SCREEN_WIDTH;
                }

                int screenHeight;
                if (sizeTable.get("h") != null) {
                    screenHeight = sizeTable.getLong("h").intValue();
                    if (screenHeight <= 0) {
                        showConfigurationError(configurationFile, "Screen height cannot be negative or zero");
                    }
                } else {
                    screenHeight = DEFAULT_SCREEN_HEIGHT;
                }
                
                // Parse sounds
                TomlTable soundTable = screenTable.getTable("sound");
                CoolSound selectionSound = null;
                CoolSound interactionSound = null;
                if (soundTable != null) {
                    String selectionSoundPath = soundTable.getString("select");
                    if (selectionSoundPath != null) {
                        selectionSound = new CoolSound(selectionSoundPath);
                    }
                    
                    String interactionSoundPath = soundTable.getString("interact");
                    if (interactionSoundPath != null) {
                        interactionSound = new CoolSound(interactionSoundPath);
                    }
                }
                
                // Parse screen background
                CoolBackground screenBackground = loadBackground(configurationFile, screenTable);
                
                screens.add(new Screen(
                        new MenuItem[] {},
                        screenWidth,
                        screenHeight,
                        screenBackground,
                        selectionSound,
                        interactionSound
                ));
            }
            
            return screens.toArray(Screen[]::new);
        } catch (IOException exc) {
            ErrorHandler.handleError(exc, String.format("Error when loading configuration file %s", configurationFile));
        }
        return null;
    }
}
