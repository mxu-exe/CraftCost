package com.umnirium.mc.craftcost.utils;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.umnirium.mc.craftcost.CraftCost.plugin;

@SuppressWarnings("UnstableApiUsage")
public class CommandUtils {
    private static CompletableFuture<Suggestions> materialSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        String input = builder.getRemainingLowerCase();

        for (Material material : Material.values()) {
            if (MaterialUtils.isMaterialValid(material)) {
                if (material.name().toLowerCase().startsWith(input)) {
                    builder.suggest(material.name().toLowerCase());
                }
            }
        }

        return builder.buildFuture();
    }

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("craftcost")
                .requires(ctx -> ctx.getSender().hasPermission("craftcost.command"))
                .then(Commands.literal("normal")
                        .requires(ctx -> ctx.getSender().hasPermission("craftcost.command.normal"))
                        .then(Commands.argument("item", StringArgumentType.word())
                            .suggests(CommandUtils::materialSuggestions)
                            .executes(CommandUtils::executeNormalCalculation)
                        )
                )
                .then(Commands.literal("version")
                        .requires(ctx -> ctx.getSender().hasPermission("craftcost.command.version"))
                        .executes(CommandUtils::executeVersion)
                )
                .build();
    }

    private static int executeNormalCalculation(CommandContext<CommandSourceStack> context) {
        String material = context.getArgument("item", String.class);

        if (MaterialUtils.isMaterialValid(Material.matchMaterial(material.toUpperCase()))) {
            List<Recipe> recipes = RecipeUtils.getRecipes(Material.matchMaterial(material));

            Map<Material, Integer> ingredients = RecipeUtils.ListIngredients(recipes);

            CommandSender sender = context.getSource().getSender();

            sender.sendMessage(MessageUtils.componentReplace("<aqua>[CraftCost]</aqua> <gray>Items needed for <material>:</gray>",
                    "material",
                    Component.text(material, NamedTextColor.GOLD)
                    ));

            StringBuilder message = new StringBuilder();

            for (Material ingredient : ingredients.keySet()) {
                message.append("\n").append("<gold>").append(ingredient.name()).append("</gold> : <gray>").append(ingredients.get(ingredient)).append("</gray>");
            }

            sender.sendMessage(MessageUtils.component(message.toString()));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int executeVersion(CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage(MessageUtils.componentReplace("<gold>[CraftCost]</gold> <aqua>Version <version></aqua>", "version", Component.text(plugin.getPluginMeta().getVersion())));

        return Command.SINGLE_SUCCESS;
    }
}