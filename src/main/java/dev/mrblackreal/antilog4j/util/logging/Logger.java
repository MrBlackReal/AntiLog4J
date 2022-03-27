package dev.mrblackreal.antilog4j.util.logging;

import dev.mrblackreal.antilog4j.AntiLog4J;

public class Logger {

    private static final Logger instance = new Logger();
    public static Logger getInstance() {
        return instance;
    }

    public void logChat(Object message) {
        AntiLog4J.getInstance().getServer().getConsoleSender().sendMessage("§7[§cAntiLog4J§7] " + message);
    }

    public void logConsole(Object message) {
        AntiLog4J.getInstance().getServer().getOnlinePlayers().forEach(player -> {
            if (player.isOp()) {
                player.sendMessage("§7[§cAntiLog4J§7] " + message);
            }
        });
    }
}
