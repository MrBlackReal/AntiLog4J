package dev.mrblackreal.antilog4j.util;

import dev.mrblackreal.antilog4j.AntiLog4J;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WebHookUtil {

    public static void sendMessage(Object message) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            URL realUrl = new URL(AntiLog4J.getInstance().getConfigManager().webHookUrl);
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            out = new PrintWriter(connection.getOutputStream());

            String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode((String) message, "UTF-8");

            out.print(postData);
            out.flush();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("/n").append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
