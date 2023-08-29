package me.tryfle.wqr.commands;

import me.tryfle.wqr.WeaveQuickReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class DebugCommand extends Command {
    public DebugCommand() {
        super("qrdebug");
    }

    @Override
    public void handle(@NotNull String[] args) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("joinedNewServer=" + WeaveQuickReport.joinedNewServer));
    }
}
