package dev.neddslayer.sharedhealth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.neddslayer.sharedhealth.SharedHealth;
import dev.neddslayer.sharedhealth.components.SharedHealthComponent;
import dev.neddslayer.sharedhealth.components.SharedHealthSetComponent;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static dev.neddslayer.sharedhealth.components.SharedComponentsInitializer.*;

public class SetSharedHealthCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("setsharedhealth")
                .then(CommandManager.argument("health", IntegerArgumentType.integer(1, 1024))
                    .executes(SetSharedHealthCommand::execute))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int health = IntegerArgumentType.getInteger(context, "health");

        // Hämta component för att kolla om det redan körts
        SharedHealthSetComponent setComponent = SHARED_HEALTH_SET.get(source.getWorld().getScoreboard());

        if (setComponent.isHealthSet()) {
            source.sendError(Text.literal("Shared health already set in this world!")
                .formatted(Formatting.RED));
            return 0;
        }

        // 1. Sätt limitHealth till false
        source.getWorld().getGameRules().get(SharedHealth.LIMIT_HEALTH).set(false, source.getServer());

        // 2. Sätt max health och heala alla spelare
        for (ServerPlayerEntity player : source.getServer().getPlayerManager().getPlayerList()) {
            // Sätt max health
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(health);
            // Heala till full HP
            player.setHealth(health);
        }

        // 3. Uppdatera shared health component
        SharedHealthComponent healthComponent = SHARED_HEALTH.get(source.getWorld().getScoreboard());
        healthComponent.setHealth(health);

        // 4. Markera som använt
        setComponent.setHealthSet(true);

        // Skicka bekräftelse till alla
        source.getServer().getPlayerManager().getPlayerList().forEach(player ->
            player.sendMessage(Text.literal("Shared Health set to " + health + " HP!")
                .formatted(Formatting.GOLD, Formatting.BOLD))
        );

        source.sendFeedback(() -> Text.literal("Shared health configured!")
            .formatted(Formatting.GREEN), true);

        return 1;
    }
}
