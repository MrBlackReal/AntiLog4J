package dev.mrblackreal.antilog4j.util.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    //Discord
    public boolean useWebHook;
    public String webHookUrl, webHookMessage;

    //Message
    public boolean replaceMessage;
    public String replacementMessage;

    //Saving
    public boolean saveUser;
    public Long saveDelay;

    //Kick
    public boolean closePlayerConn;
    public String closePlayerConnType, closePlayerConnMessage;

    private final File mainDir = new File("plugins/AntiLog4J");
    private final File config = new File(mainDir, "config.yml");

    private final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(config);
    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void save() throws IOException {
        configuration.save(config);
    }

    public void load() throws IOException {
        if (configuration.getString("AntiLog4J.") == null)  {
            create();
        }

        useWebHook = configuration.getBoolean("AntiLog4J.Discord.WebHook.enabled");
        webHookUrl = configuration.getString("AntiLog4J.Discord.WebHook.WebHookUrl");
        webHookMessage = configuration.getString("AntiLog4J.Discord.WebHook.WebHookMessage");

        replaceMessage = configuration.getBoolean("AntiLog4J.Message.ReplaceMessage.enabled");
        replacementMessage = configuration.getString("AntiLog4J.Message.ReplaceMessage.Message");

        saveUser = configuration.getBoolean("AntiLog4J.Save.enabled");
        saveDelay = configuration.getLong("AntiLog4J.Save.Delay.Ticks");

        closePlayerConn = configuration.getBoolean("AntiLog4J.Player.CloseConnection.enabled");
        closePlayerConnType = configuration.getString("AntiLog4J.Player.CloseConnection.Type");
        closePlayerConnMessage = configuration.getString("AntiLog4J.Player.CloseConnection.Message");
    }

    public void create() throws IOException {
        configuration.set("AntiLog4J.Discord.WebHook.enabled", false);
        configuration.set("AntiLog4J.Discord.WebHook.WebHookUrl", "");
        configuration.set("AntiLog4J.Discord.WebHook.WebHookMessage", "{player} has tried to Log4J shame on you!");

        configuration.set("AntiLog4J.Message.ReplaceMessage.enabled", true);
        configuration.set("AntiLog4J.Message.ReplaceMessage.Message", "I just tried Log4J but the server cancelled me :(");

        configuration.set("AntiLog4J.Save.enabled", true);
        configuration.set("AntiLog4J.Save.Delay.Ticks", 12000L);

        configuration.set("AntiLog4J.Player.CloseConnection.enabled", true);
        configuration.set("AntiLog4J.Player.CloseConnection.Type", "kick");
        configuration.set("AntiLog4J.Player.CloseConnection.Message", "§c{player} no Log4J in here my dude...");
        save();
    }
}
