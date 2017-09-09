package com.qihuan.websocket;

import com.ejiaoji.pomelo.websocket.PomeloClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * PomeloService
 * Created by Qi on 2017/7/13.
 */
@Service
public class PomeloService {

    @Value("${pomelo.address}")
    private String address;

    public String login(String phone, String password) {
        JSONObject json = new JSONObject();
        json.put("phone", phone);
        json.put("password", password);
        String route = "connector.entryHandler.login";
        return pomelo(route, json.toString());
    }

    public String user(String gameId) {
        JSONObject json = new JSONObject();
        json.put("gameId", gameId);
        String route = "proxysvr.proxyHandler.exists";
        return pomelo(route, json.toString());
    }

    private String pomelo(String route, String arg) {
        final boolean[] flag = {false};
        final String[] response = new String[1];
        PomeloClient client = null;
        try {
            client = new PomeloClient(new URI(address));
            client.setOnHandshakeSuccessHandler((client1, resp) -> {
                try {
                    client1.request(route, arg, message -> {
                        JSONObject bodyJson = message.getBodyJson();
                        response[0] = bodyJson.toString();
                        flag[0] = true;
                    });
                } catch (Exception e) {
                    response[0] = e.toString();
                    flag[0] = true;
                }
            });
            client.setOnErrorHandler(e -> {
                response[0] = e.getMessage();
                flag[0] = true;
            });
            client.connect();

            while (!flag[0]) {
                Thread.sleep(100);
            }

        } catch (URISyntaxException | InterruptedException e) {
            response[0] = e.toString();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return response[0];
    }

}
