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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class findName {
    public static void main(String[] args) {
        try {
            String url = "https://serverstudent.herokuapp.com/findClazzByName/"+"Phan%20mem%202";
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject myresponse = new JSONObject(response.toString());
            String a = response.toString();
            if(a.contains(myresponse.toString())){
            System.out.println(a);
            }
            
//            DefaultComboBoxModel model = (DefaultComboBoxModel) cboClazz.getModel();
////            model.removeAllElements();
//            for(int i = 0; i<myresponse.length();i++){
//                String name = myresponse.getJSONObject(i).getString("id");
//                System.out.println(name);
//            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
