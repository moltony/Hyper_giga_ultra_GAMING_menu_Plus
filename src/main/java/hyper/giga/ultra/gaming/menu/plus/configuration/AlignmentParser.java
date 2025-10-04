package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import java.util.Optional;

public class AlignmentParser
{
    public static Optional<Alignment> parse(JsonElement element)
    {
        if (element == null || !element.isJsonPrimitive()) {
            return Optional.empty();
        }
        
        String alignmentString = element.getAsString();
        
        // this is what they call modern java i think
        return switch (alignmentString) {
            case "center" -> Optional.of(Alignment.Center);
            case "left" -> Optional.of(Alignment.Left);
            case "right" -> Optional.of(Alignment.Right);
            default -> throw new IllegalArgumentException(String.format("Invalid alignment: %s", alignmentString));
        };
    }
}
