package com.umnirium.mc.craftcost.utils;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;

import static com.umnirium.mc.craftcost.CraftCost.plugin;

@SuppressWarnings("UnstableApiUsage")
public class CommandUtils {

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("craftcost")
                .requires(ctx -> ctx.getSender().hasPermission("craftcost.command"))
                .then(Commands.literal("version")
                        .requires(ctx -> ctx.getSender().hasPermission("craftcost.command.version"))
                        .executes(CommandUtils::executeVersion)
                )
                .build();
    }

    private static int executeVersion(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage(MessageUtils.componentReplace("<gold>[CraftCost]</gold> <aqua>Version <version></aqua>", "version", Component.text(plugin.getPluginMeta().getVersion())));

        return Command.SINGLE_SUCCESS;
    }
}