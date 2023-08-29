package me.tryfle.wqr.commands;

import me.tryfle.wqr.WeaveQuickReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class QRClearCommand extends Command {
    public QRClearCommand() {
        super("qrclear");
    }

    @Override
    public void handle(@NotNull String[] args) {
        WeaveQuickReport.playerList.clear();
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Stored inputs reset, now stored list contains: " + WeaveQuickReport.playerList));
    }
}
