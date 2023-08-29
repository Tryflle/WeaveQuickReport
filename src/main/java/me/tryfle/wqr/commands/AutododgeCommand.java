package me.tryfle.wqr.commands;

import me.tryfle.wqr.WeaveQuickReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class AutododgeCommand extends Command {
    public AutododgeCommand() {
        super("autododge");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveQuickReport.dodgerEnabled = !WeaveQuickReport.dodgerEnabled;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Autododge = " + WeaveQuickReport.dodgerEnabled));
    }
}
