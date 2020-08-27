/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clazz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class getClazz {
    public static void main(String[] args) {
        try {
            String url = "https://serverstudent.herokuapp.com/getAllClazz";
//            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL: " + url);
            System.out.println("Respon Code: " + responCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            System.out.println(response.toString());
            JSONArray myresponse = new JSONArray(response.toString());
            for(int i = 0; i<myresponse.length();i++){
                String massv = myresponse.getJSONObject(i).getString("id");
                String name = myresponse.getJSONObject(i).getString("name");
//                System.out.println("Json : "+ json);
//                String nameClazz = json.getString("name");
                System.out.println("id : "+massv);
                System.out.println("name : "+name);
                System.out.println("###################");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

