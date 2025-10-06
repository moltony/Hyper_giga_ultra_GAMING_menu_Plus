package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Graphics;
import java.util.Optional;

public abstract class ImageWithTextMenuItem extends MenuItem
{
    protected Optional<ImageMenuItem> image, selectedImage;
    protected Optional<TextMenuItem> text, selectedText;

    public abstract class Builder
    {
        protected Optional<CoolImage> image, selectedImage;
        protected Optional<CoolText> text, selectedText;
        protected Alignment imageAlignment, textAlignment;
        protected Optional<Alignment> selectedImageAlignment, selectedTextAlignment;
        protected int imageOffsetX, imageOffsetY;
        protected Optional<Integer> selectedImageOffsetX, selectedImageOffsetY;
        protected int textOffsetX, textOffsetY;
        protected Optional<Integer> selectedTextOffsetX, selectedTextOffsetY;

        public Builder()
        {
            image = Optional.empty();
            selectedImage = Optional.empty();
            text = Optional.empty();
            selectedText = Optional.empty();
            selectedImageAlignment = Optional.empty();
            selectedTextAlignment = Optional.empty();
            selectedImageOffsetX = Optional.empty();
            selectedImageOffsetY = Optional.empty();
            selectedTextOffsetX = Optional.empty();
            selectedTextOffsetY = Optional.empty();
            
            imageAlignment = Alignment.Center;
            textAlignment = Alignment.Center;
            
            imageOffsetX = 0;
            imageOffsetY = 0;
            textOffsetX = 0;
            textOffsetY = 0;
        }
        
        public void addText(CoolText text, int offsetX, int offsetY, Alignment alignment)
        {
            this.text = Optional.of(text);
            textOffsetX = offsetX;
            textOffsetY = offsetY;
            textAlignment = alignment;
        }
        
        public void addSelectedText(CoolText text, Integer offsetX, Integer offsetY, Alignment alignment)
        {
            selectedText = Optional.of(text);
            selectedTextOffsetX = Optional.ofNullable(offsetX);
            selectedTextOffsetY = Optional.ofNullable(offsetY);
            selectedTextAlignment = Optional.ofNullable(alignment);
        }
        
        public void addImage(CoolImage image, int offsetX, int offsetY, Alignment alignment)
        {
            this.image = Optional.of(image);
            imageOffsetX = offsetX;
            imageOffsetY = offsetY;
            imageAlignment = alignment;
        }
        
        public void addSelectedImage(CoolImage image, Integer offsetX, Integer offsetY, Alignment alignment)
        {
            selectedImage = Optional.of(image);
            selectedImageOffsetX = Optional.ofNullable(offsetX);
            selectedImageOffsetY = Optional.ofNullable(offsetY);
            selectedImageAlignment = Optional.ofNullable(alignment);
        }
        
        protected ImageWithTextMenuItemArgs buildCommonArgs(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
        {
            return new ImageWithTextMenuItemArgs(
                    backgroundNormal,
                    backgroundSelected,
                    selectionSound,
                    interactionSound,
                    image,
                    selectedImage,
                    imageAlignment,
                    selectedImageAlignment,
                    imageOffsetX,
                    selectedImageOffsetX,
                    imageOffsetY,
                    selectedImageOffsetY,
                    text,
                    selectedText,
                    textAlignment,
                    selectedTextAlignment,
                    textOffsetX,
                    selectedTextOffsetX,
                    textOffsetY,
                    selectedTextOffsetY
            );
        }
    }
    
    protected ImageWithTextMenuItem(ImageWithTextMenuItemArgs args)
    {
        super(args.backgroundNormal, args.backgroundSelected, args.selectionSound, args.interactionSound);
        
        this.text = args.text.map(t -> new TextMenuItem(
                null, null, null, null,
                t, args.selectedText.orElse(null),
                args.textAlignment, args.selectedTextAlignment.orElse(null),
                args.textOffsetX, args.selectedTextOffsetX.orElse(null),
                args.textOffsetY, args.selectedTextOffsetY.orElse(null)));
        this.image =
                args.image.map(i -> new ImageMenuItem(
                        null, null, null, null,
                        i, args.selectedImage.orElse(null),
                        args.imageAlignment, args.selectedImageAlignment.orElse(null),
                        args.imageOffsetX, args.selectedImageOffsetX.orElse(null),
                        args.imageOffsetY, args.selectedImageOffsetY.orElse(null)));
    }
    
    @Override
    public void update()
    {
        this.text.ifPresent(t -> t.update());
        this.selectedText.ifPresent(t -> t.update());
        this.image.ifPresent(i -> i.update());
        this.selectedImage.ifPresent(i -> i.update());
        
        image.ifPresentOrElse(
                i -> height = i.getHeight(),
                () -> height = MenuItem.DEFAULT_HEIGHT
        );
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);
        if (selected) {
            selectedText.ifPresentOrElse(
                    t -> t.render(g, y, width, selected),
                    () -> text.ifPresent(t -> t.render(g, y, width, selected))
            );
            selectedImage.ifPresentOrElse(
                    i -> i.render(g, y, width, selected),
                    () -> image.ifPresent(i -> i.render(g, y, width, selected))
            );
        }
    }
}
