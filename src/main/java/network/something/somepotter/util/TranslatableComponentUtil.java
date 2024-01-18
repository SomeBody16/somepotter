package network.something.somepotter.util;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class TranslatableComponentUtil {

    public static TranslatableComponent advancement(ResourceLocation advancementId) {
        var result = new StringBuilder();
        if (!advancementId.getNamespace().equals("minecraft")) {
            result.append(advancementId.getNamespace()).append(".");
        }
        result.append("advancements.");
        var path = Arrays.stream(advancementId.getPath().split("/"))
                .reduce((a, b) -> a + "." + b)
                .orElse("");
        result.append(path);

        return new TranslatableComponent(result + ".title");
    }

    public static TranslatableComponent item(ResourceLocation itemId) {
        var result = "item.%s.%s".formatted(itemId.getNamespace(), itemId.getPath());
        return new TranslatableComponent(result);
    }

    public static TranslatableComponent block(ResourceLocation blockId) {
        var result = "block.%s.%s".formatted(blockId.getNamespace(), blockId.getPath());
        return new TranslatableComponent(result);
    }

}
