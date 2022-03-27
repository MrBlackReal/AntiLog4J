package dev.mrblackreal.antilog4j.event;

import dev.mrblackreal.antilog4j.AntiLog4J;
import dev.mrblackreal.antilog4j.util.Exploiter;
import dev.mrblackreal.antilog4j.util.TimeHelper;
import dev.mrblackreal.antilog4j.util.WebHookUtil;
import dev.mrblackreal.antilog4j.util.logging.Logger;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class EventManager implements Listener {

    private String type, message;

    private final AntiLog4J antiLog4J = AntiLog4J.getInstance();
    private static final ArrayList<Exploiter> exploiter = new ArrayList<Exploiter>();
    public static ArrayList<Exploiter> getExploiter() {
        return exploiter;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().contains("${jndi:ldap:")) {
            if (antiLog4J.getConfigManager().replaceMessage || !(antiLog4J.getConfigManager().replacementMessage == null || antiLog4J.getConfigManager().replacementMessage.equals("")))  {
                event.setMessage(antiLog4J.getConfigManager().replacementMessage);
            } else {
                event.setCancelled(true);
            }

            if (antiLog4J.getConfigManager().useWebHook)
                WebHookUtil.sendMessage(antiLog4J.getConfigManager().webHookMessage.replace("{player}", player.getName()));

            if (!exploiter.contains(player) && antiLog4J.getConfigManager().saveUser) {
                exploiter.add(new Exploiter(player, TimeHelper.getDateAndTime(System.currentTimeMillis()), player.getAddress()));

                String message = "§4Added §c" + player.getName() + " §7(§c"+ player.getAddress().getAddress().toString().replace("/", "") +"§7) §4to the list of exploiters";

                if (antiLog4J.getConfigManager().notify) {
                    Logger.getInstance().logChat(message);
                }

                if (antiLog4J.getConfigManager().notifyConsole) {
                    Logger.getInstance().logConsole(message);
                }
            }

            if (antiLog4J.getConfigManager().closePlayerConn) {
                Bukkit.getScheduler().runTask(antiLog4J, new Runnable() {
                    @Override
                    public void run() {
                        type = antiLog4J.getConfigManager().closePlayerConnType;
                        message = antiLog4J.getConfigManager().closePlayerConnMessage.replace("{player}", player.getName());

                        if (type.equalsIgnoreCase("kick")) {
                            player.kickPlayer(message);
                        } else if (type.equalsIgnoreCase("ban")) {
                            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), message, null, "AntiLog4J by MrBlackReal");
                            player.kickPlayer(message);
                        }

                        message = null;
                        type = null;
                    }
                });
            }
        }
    }

    @EventHandler
    public void onPlayerMsg(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().contains("${jndi:ldap:")) {
            if (antiLog4J.getConfigManager().replaceMessage || !(antiLog4J.getConfigManager().replacementMessage == null || antiLog4J.getConfigManager().replacementMessage.equals("")))  {
                event.setMessage(antiLog4J.getConfigManager().replacementMessage);
            } else {
                event.setCancelled(true);
            }

            if (antiLog4J.getConfigManager().useWebHook)
                WebHookUtil.sendMessage(antiLog4J.getConfigManager().webHookMessage.replace("{player}", player.getName()));

            if (!exploiter.contains(player) && antiLog4J.getConfigManager().saveUser) {
                exploiter.add(new Exploiter(player, TimeHelper.getDateAndTime(System.currentTimeMillis()), player.getAddress()));

                String message = "§fAdded §c" + player.getName() + " §7(§c"+ player.getAddress().getAddress().toString().replace("/", "") +"§7) §fto the list of exploiters";

                if (antiLog4J.getConfigManager().notify) {
                    Logger.getInstance().logChat(message);
                }

                if (antiLog4J.getConfigManager().notifyConsole) {
                    Logger.getInstance().logConsole(message);
                }
            }

            if (antiLog4J.getConfigManager().closePlayerConn) {
                Bukkit.getScheduler().runTask(antiLog4J, new Runnable() {
                    @Override
                    public void run() {
                        type = antiLog4J.getConfigManager().closePlayerConnType;
                        message = antiLog4J.getConfigManager().closePlayerConnMessage.replace("{player}", player.getName());

                        if (type.equalsIgnoreCase("kick")) {
                            player.kickPlayer(message);
                        } else if (type.equalsIgnoreCase("ban")) {
                            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), message, null, "AntiLog4J by MrBlackReal");
                            player.kickPlayer(message);
                        }

                        message = null;
                        type = null;
                    }
                });
            }
        }
    }
}
