/* Decompiler 7ms, total 101ms, lines 98 */
package fr.emse.fayol.maqit.simulator.configuration;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt implements MqttCallback {
   private String broker = "tcp://127.0.0.1:1883";
   private MqttClient mqttClient;
   private MemoryPersistence persistence;
   private MqttConnectOptions mqttConnectOptions;
   private String name;
   private int debug;
   public Map<String, String> box;

   public Mqtt(String name, int debug) {
      this.name = name;
      this.debug = debug;
      this.box = new HashMap();
      this.persistence = new MemoryPersistence();
      this.mqttConnectOptions = new MqttConnectOptions();
      this.mqttConnectOptions.setAutomaticReconnect(true);
      this.mqttConnectOptions.setCleanSession(true);
      this.mqttConnectOptions.setKeepAliveInterval(10);
   }

   public void connect() {
      try {
         this.mqttClient = new MqttClient(this.broker, this.name);
         this.mqttClient.setCallback(this);
         this.mqttClient.connect(this.mqttConnectOptions);
         if (this.debug == 1) {
            System.out.println("[Mqtt] Connection done");
         }
      } catch (MqttException var2) {
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public void disconnect() {
      try {
         this.mqttClient.disconnect();
         if (this.debug == 1) {
            System.out.println("[Mqtt] Connection closed");
         }
      } catch (MqttException var2) {
         var2.printStackTrace();
         System.exit(1);
      }

   }

   public void connectionLost(Throwable cause) {
      if (this.debug == 1) {
         System.out.println("[Mqtt] Connection lost " + cause);
      }

   }

   public void messageArrived(String topic, MqttMessage message) throws Exception {
      this.box.put(topic, message.toString());
   }

   public void deliveryComplete(IMqttDeliveryToken token) {
   }

   public void publish(String topic, String publishMessage) {
      int pubQos = 1;
      MqttMessage mqttMessage = new MqttMessage(publishMessage.getBytes());
      mqttMessage.setQos(pubQos);

      try {
         this.mqttClient.publish(topic, mqttMessage);
      } catch (MqttException var6) {
         var6.printStackTrace();
      }

   }

   public void subscribe(String topic) {
      try {
         int subQos = 1;
         this.mqttClient.subscribe(topic, subQos);
      } catch (MqttException var3) {
         var3.printStackTrace();
      }

   }
}
