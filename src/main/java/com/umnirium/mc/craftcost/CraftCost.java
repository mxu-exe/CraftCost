package com.umnirium.mc.craftcost;

import com.umnirium.mc.craftcost.utils.CommandUtils;
import com.umnirium.mc.craftcost.utils.MessageUtils;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("UnstableApiUsage")
public class CraftCost extends JavaPlugin {
    public static CraftCost plugin;
    public static ComponentLogger LOGGER;

    @Override
    public void onEnable() {
        plugin = this;
        LOGGER = this.getComponentLogger();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> commands.registrar().register(CommandUtils.createCommand()));

        LOGGER.info(MessageUtils.component("<aqua>CraftCost successfully enabled!</aqua>"));
    }

    @Override
    public void onDisable() {
        LOGGER.info(MessageUtils.component("<aqua>CraftCost successfully disabled!</aqua>"));
    }
}