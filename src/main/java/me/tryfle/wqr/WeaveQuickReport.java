package me.tryfle.wqr;

import me.tryfle.wqr.commands.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;

import java.util.ArrayList;
import java.util.List;

//sry syz for my terrible codebase and your determination to fix it - weave's local furry
public class WeaveQuickReport implements ModInitializer {

    public static List<String> playerList = new ArrayList<>();
    public static String player = "";
    public static boolean dodgerEnabled, joinedNewServer = false;
    public static int dodgeDelayTicks = 40;

    @Override
    public void preInit() {
        EventBus.subscribe(this);
        CommandBus.register(new AutododgeCommand());
        CommandBus.register(new DebugCommand());
        CommandBus.register(new QRClearCommand());
        CommandBus.register(new QRCommand());
        CommandBus.register(new QRPLCommand());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        joinedNewServer = true;
        dodgeDelayTicks = 40;
    }

    @SubscribeEvent
    public void onPreTick(TickEvent.Pre event) {
        if (dodgerEnabled && Minecraft.getMinecraft().thePlayer != null) {
            String playerName = Minecraft.getMinecraft().thePlayer.getName();

            if (playerList.contains(playerName) && dodgeDelayTicks > 0) {
                dodgeDelayTicks--;
            }

            if (dodgeDelayTicks == 0 && joinedNewServer) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[AutoDodge] Attempting to evade your problems."));
                dodgeDelayTicks = 60;
                joinedNewServer = false;
            }
        }
    }

    @SubscribeEvent
    private void handlePlayerListAdd(PlayerListEvent.Add event) {
        String joinedPlayerName = event.getPlayerData().getProfile().getName();

        for (String storedPlayer : playerList) {
            if (storedPlayer.equals(joinedPlayerName)) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[WQR] Reported player " + joinedPlayerName + " detected in your server."));
                break;
            }
        }
    }

}
