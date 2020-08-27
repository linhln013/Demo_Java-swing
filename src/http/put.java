/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class put {
    public static void main(String[] args) throws MalformedURLException, IOException {
        Thread t = new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();
                try {
                    HttpPut post = new HttpPut ("https://serverstudent.herokuapp.com/student");
                    Map<String,Object>map = new HashMap<>();
                    map.put("id", "PM14303");
                    map.put("name","Phan mem 3");
                    System.out.println(map.toString());
                    json.put("massv", "PC00378");
                    json.put("name", "Sang ngua ba");
                    json.put("age", 98);
                    json.put("clazz", map);
                    System.out.println(json.toString());
                    StringEntity se = new StringEntity(json.toString());
                    post.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if (response != null) {
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }
}
