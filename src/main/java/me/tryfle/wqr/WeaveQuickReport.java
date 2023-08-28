package me.tryfle.wqr;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.Command;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.*;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.ArrayList;

//sry syz for my terrible codebase and your determination to fix it
public class WeaveQuickReport implements ModInitializer {
    public List<String> playerList = new ArrayList<>();
    public String player = "";
    public String reason = " bhop aura scaffold dolphin";
    private static boolean dodgerEnabled = false;
    private static int dodgeDelayTicks = 40;
    private boolean joinedNewServer = false;
    @Override
    public void preInit() {
        System.out.println("Initializing WeaveQuickReport");
        CommandBus.register(new Command("qr") {
            @Override
            public void handle(@NotNull String[] args) {
                player = String.join(" ", args);
                playerList.add(player);
                joinedNewServer = false;
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Reported " + player));
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/wdr " + player + reason);
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Players reported " + playerList)
                );
            }
        });
        CommandBus.register(new Command("qrpl") {
            @Override
            public void handle(@NotNull String[] args) {
                player = String.join(" ", args);
                playerList.add(player);
                joinedNewServer = false;
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Added " + player + " to list without reporting. "));
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Players reported list: " + playerList)
                );
            }
        });
        CommandBus.register(new Command("qrdebug") {
            @Override
            public void handle(@NotNull String[] args) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText("joinedNewServer=" + joinedNewServer)
                    );

            }
        });

        CommandBus.register(new Command("qrclear") {
            @Override
            public void handle(@NotNull String[] args) {
                playerList.clear();
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Stored inputs reset, now stored list contains: " + playerList));
            }
        });
        EventBus.subscribe(PlayerListEvent.Add.class, this::handlePlayerListAdd);
        CommandBus.register(new Command("autododge") {

            @Override
            public void handle(@NotNull String[] args) {
                dodgerEnabled = !dodgerEnabled;
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("Autododge = " + dodgerEnabled)
                );
            }
            });
        EventBus.subscribe(TickEvent.Pre.class, this::onPreTick);
        EventBus.subscribe(WorldEvent.Load.class, this::onWorldLoad);
        }
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
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.AQUA + "[AutoDodge] Attempting to evade your problems.")
                );
                dodgeDelayTicks = 60;
                joinedNewServer = false;
            }
        }
    }
    private void handlePlayerListAdd(PlayerListEvent.Add event) {
        String joinedPlayerName = event.getPlayerData().getProfile().getName();
        for (String storedPlayer : playerList) {
            if (storedPlayer.equals(joinedPlayerName)) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.GREEN + "[WQR] Reported player " + joinedPlayerName + " detected in your server."));
                break;
            }

        }
    }
}
