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

public class WeaveQuickReport implements ModInitializer {

    public static List<String> playerList = new ArrayList<>();
    public static String player = "";
    public static boolean dodgerEnabled, joinedNewServer = false;
    public static long lastDodgeTime = 200;
    public static final long DODGE_DELAY = 2000;


    @Override
    public void preInit() {
        EventBus.subscribe(this);
        EventBus.subscribe(PlayerListEvent.Add.class, this::handlePlayerListAdd);
        CommandBus.register(new AutododgeCommand());
        CommandBus.register(new DebugCommand());
        CommandBus.register(new QRClearCommand());
        CommandBus.register(new QRCommand());
        CommandBus.register(new QRPLCommand());
    }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        joinedNewServer = true;
    }

    @SubscribeEvent
    public void onRenderLivingPost(RenderLivingEvent.Post event) {
        if (dodgerEnabled && event.getEntity() != null) {
            String entityName = event.getEntity().getName();
            if (playerList.contains(entityName) && shouldDodge() && joinedNewServer) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[AutoDodge] Attempting to evade your problems."));
                lastDodgeTime = System.currentTimeMillis();
                joinedNewServer = false;

            }
        }
    }

    private boolean shouldDodge() {
        return System.currentTimeMillis() - lastDodgeTime >= DODGE_DELAY;
    }

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
