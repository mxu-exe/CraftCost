package com.umnirium.mc.craftcost.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageUtils {
    public static Component component(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}