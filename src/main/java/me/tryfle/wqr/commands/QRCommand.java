package me.tryfle.wqr.commands;

import me.tryfle.wqr.WeaveQuickReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class QRCommand extends Command {

    public String reason = " bhop aura scaffold dolphin";

    public QRCommand() {
        super("qr");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveQuickReport.player = String.join(" ", args);
        WeaveQuickReport.playerList.add(WeaveQuickReport.player);
        WeaveQuickReport.joinedNewServer = false;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Reported " + WeaveQuickReport.player));
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/wdr " + WeaveQuickReport.player + reason);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Players reported " + WeaveQuickReport.playerList));
    }
}
