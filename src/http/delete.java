 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class delete {

    public static void main(String[] args) throws IOException {

        Thread t = new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
                HttpResponse response = null;
                JSONObject json = new JSONObject();

                try {
                    String url = "https://serverstudent.herokuapp.com/student"+ "PC001";
                    HttpDelete delete = new HttpDelete(url);

//                    Header headers[] = {
//                        new BasicHeader("massv", "PC00128")
//                    };
//                    delete.setHeaders(headers);

                    response = client.execute(delete);
                    int responseCode = response.getStatusLine().getStatusCode();
                    String statusPhrase = response.getStatusLine().getReasonPhrase();
                    response.getEntity().getContent().close();
                    
                    System.out.println("response "+ responseCode);
                    System.out.println("status "+ statusPhrase);
//                    /*Checking response */
//                    if (response != null) {
//                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }
}
