package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.util.Optional;

public class ImageWithTextMenuItemArgs
{
    public CoolBackground backgroundNormal;
    public CoolBackground backgroundSelected;
    public CoolSound selectionSound;
    public CoolSound interactionSound;
    public Optional<CoolImage> image;
    public Optional<CoolImage> selectedImage;
    public Alignment imageAlignment;
    public Optional<Alignment> selectedImageAlignment;
    public int imageOffsetX;
    public Optional<Integer> selectedImageOffsetX;
    public int imageOffsetY;
    public Optional<Integer> selectedImageOffsetY;
    public Optional<CoolText> text;
    public Optional<CoolText> selectedText;
    public Alignment textAlignment;
    public Optional<Alignment> selectedTextAlignment;
    public int textOffsetX;
    public Optional<Integer> selectedTextOffsetX;
    public int textOffsetY;
    public Optional<Integer> selectedTextOffsetY;

    public ImageWithTextMenuItemArgs(
            CoolBackground backgroundNormal,
            CoolBackground backgroundSelected,
            CoolSound selectionSound,
            CoolSound interactionSound,
            Optional<CoolImage> image,
            Optional<CoolImage> selectedImage,
            Alignment imageAlignment,
            Optional<Alignment> selectedImageAlignment,
            int imageOffsetX,
            Optional<Integer> selectedImageOffsetX,
            int imageOffsetY,
            Optional<Integer> selectedImageOffsetY,
            Optional<CoolText> text,
            Optional<CoolText> selectedText,
            Alignment textAlignment,
            Optional<Alignment> selectedTextAlignment,
            int textOffsetX,
            Optional<Integer> selectedTextOffsetX,
            int textOffsetY,
            Optional<Integer> selectedTextOffsetY) {
        this.backgroundNormal = backgroundNormal;
        this.backgroundSelected = backgroundSelected;
        this.selectionSound = selectionSound;
        this.interactionSound = interactionSound;
        this.image = image;
        this.selectedImage = selectedImage;
        this.imageAlignment = imageAlignment;
        this.selectedImageAlignment = selectedImageAlignment;
        this.imageOffsetX = imageOffsetX;
        this.selectedImageOffsetX = selectedImageOffsetX;
        this.imageOffsetY = imageOffsetY;
        this.selectedImageOffsetY = selectedImageOffsetY;
        this.text = text;
        this.selectedText = selectedText;
        this.textAlignment = textAlignment;
        this.selectedTextAlignment = selectedTextAlignment;
        this.textOffsetX = textOffsetX;
        this.selectedTextOffsetX = selectedTextOffsetX;
        this.textOffsetY = textOffsetY;
        this.selectedTextOffsetY = selectedTextOffsetY;
    }
}
