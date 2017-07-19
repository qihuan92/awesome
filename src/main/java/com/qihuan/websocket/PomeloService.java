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

    private boolean flag;

    public String login(String phone, String password) {
        JSONObject json = new JSONObject();
        json.put("phone", phone);
        json.put("password", password);
        String route = "connector.entryHandler.login";
        return pomelo(route, json.toString());
    }

    private String pomelo(String route, String arg) {
        final String[] response = new String[1];
        try {
            PomeloClient client = new PomeloClient(new URI(address));
            client.setOnHandshakeSuccessHandler((client1, resp) -> {
                try {
                    client1.request(route, arg, message -> {
                        try {
                            JSONObject bodyJson = message.getBodyJson();
                            response[0] = bodyJson.toString();
                            flag = true;
                        } catch (Exception e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            flag = true;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    flag = true;
                }
            });
            client.setOnErrorHandler(e -> {
                e.printStackTrace();
                response[0] = e.getMessage();
                flag = true;
            });
            client.connect();
            while (!flag) {
                Thread.sleep(100);
            }
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response[0];
    }

}
