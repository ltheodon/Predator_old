/* Decompiler 6ms, total 100ms, lines 66 */
package fr.emse.fayol.maqit.simulator.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RosbridgeClient {
   private WebSocketClient wsc;
   protected Map<String, String> box = new HashMap();

   public WebSocketClient getWsc() {
      return this.wsc;
   }

   public void setWsc(WebSocketClient wsc) {
      this.wsc = wsc;
   }

   public RosbridgeClient(String host, String port) {
      try {
         this.wsc = new WebSocketClient(new URI("ws://" + host + ":" + port)) {
            public void onOpen(ServerHandshake arg0) {
               System.out.println("Connection opened");
            }

            public void onMessage(String arg0) {
               try {
                  JSONParser parser = new JSONParser();
                  JSONObject json = (JSONObject)parser.parse(arg0);
                  String st1 = (String)json.get("topic");
                  String st2 = (String)json.get("msg");
                  String[] tt1 = st1.split("/");
                  RosbridgeClient.this.box.put(st1, st2);
               } catch (ParseException var7) {
                  System.out.println("ParseException: " + var7);
               }

            }

            public void onError(Exception arg0) {
               System.out.println("Error");
               arg0.printStackTrace();
            }

            public void onClose(int arg0, String arg1, boolean arg2) {
               System.out.println("Connection closed");
            }
         };
         this.wsc.connect();
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
      }

   }

   public void close() {
      this.wsc.close();
   }
}
