package me.tryfle.wqr.commands;

import me.tryfle.wqr.WeaveQuickReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class QRPLCommand extends Command {
    public QRPLCommand() {
        super("qrpl");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveQuickReport.player = String.join(" ", args);
        WeaveQuickReport.playerList.add(WeaveQuickReport.player);
        WeaveQuickReport.joinedNewServer = false;
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText("Added " + WeaveQuickReport.player + " to list without reporting. "));
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText("Players reported list: " + WeaveQuickReport.playerList)
        );
    }
}
