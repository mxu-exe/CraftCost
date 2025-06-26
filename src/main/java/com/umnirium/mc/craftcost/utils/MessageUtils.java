package com.umnirium.mc.craftcost.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.intellij.lang.annotations.Subst;

public class MessageUtils {
    public static Component component(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static Component componentReplace(String message, @Subst("") String oldValue, TextComponent newValue) {
        return MiniMessage.miniMessage().deserialize(message, Placeholder.component(oldValue,
                newValue));
    }
}