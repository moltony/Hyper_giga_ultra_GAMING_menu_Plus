package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.ErrorHandler;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.io.File;
import java.io.IOException;

public class LauncherMenuItem extends ImageWithTextMenuItem
{
    private String[] arguments;
    private String workingDirectory;
    boolean closeOnLaunch;
    
    public LauncherMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound, CoolImage image, Alignment imageAlignment, int imageOffsetX, int imageOffsetY, CoolText text, Alignment textAlignment, int textOffsetX, int textOffsetY, String[] arguments, String workingDirectory, boolean closeOnLaunch)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound, image, imageAlignment, imageOffsetX, imageOffsetY, text, textAlignment, textOffsetX, textOffsetY);
        this.arguments = arguments;
        this.workingDirectory = workingDirectory;
        this.closeOnLaunch = closeOnLaunch;
    }
    
    @Override
    public MenuItemInteractionResult interact()
    {
        ProcessBuilder builder = new ProcessBuilder(arguments);
        builder.directory(new File(workingDirectory));
        try {
            builder.start();
        } catch (IOException exc) {
            ErrorHandler.handleError(exc, String.format("Could not start process %s", String.join(" ", arguments)));
        }
        
        return closeOnLaunch ? MenuItemInteractionResult.EXIT : MenuItemInteractionResult.NONE;
    }
}
