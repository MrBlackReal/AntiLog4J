package dev.mrblackreal.antilog4j.util.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.mrblackreal.antilog4j.event.EventManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

    private final File dir = new File("plugins/AntiLog4J/saves");
    private final File dirLog4J = new File(dir, "log4j.json");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public FileManager() {
        if (!dir.exists())
            dir.mkdirs();
    }

    public void save() throws IOException {
        if (!dirLog4J.exists())
            dirLog4J.createNewFile();

        final JsonObject exploiterObj = new JsonObject();

        EventManager.getExploiter().forEach(exploiter -> {
            final JsonObject exploiterJson = new JsonObject();

            exploiterJson.addProperty("UUID", exploiter.getPlayer().getUniqueId().toString());
            exploiterJson.addProperty("ExploitTime", exploiter.getTime());
            exploiterJson.addProperty("PlayerAddress", exploiter.getIp().getAddress().toString().replace("/", ""));

            exploiterObj.add(exploiter.getPlayer().getName(), exploiterJson);
        });

        final PrintWriter writer = new PrintWriter(dirLog4J);

        writer.println(gson.toJson(exploiterObj));
        writer.flush();
        writer.close();
    }
}
