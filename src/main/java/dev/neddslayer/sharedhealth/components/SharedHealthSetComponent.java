package dev.neddslayer.sharedhealth.components;

import org.ladysnake.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

import org.ladysnake.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class SharedHealthSetComponent implements Component {
    private boolean healthSet = false;

    Scoreboard scoreboard;
    MinecraftServer server;

    public SharedHealthSetComponent(Scoreboard scoreboard, MinecraftServer server) {
        this.scoreboard = scoreboard;
        this.server = server;
    }

    public boolean isHealthSet() {
        return healthSet;
    }

    public void setHealthSet(boolean value) {
        this.healthSet = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        healthSet = tag.getBoolean("healthSet");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean("healthSet", healthSet);
    }
}
