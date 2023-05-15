/* Decompiler 6ms, total 102ms, lines 66 */
package fr.emse.fayol.maqit.simulator.environment;

import fr.emse.fayol.maqit.simulator.configuration.Mqtt;

public class LEDTable {
   protected Mqtt mqttClient;
   protected String id;
   protected int width;
   protected int height;
   protected int positionX;
   protected int positionY;
   protected int debug;

   public LEDTable(String id, int width, int height, int positionX, int positionY, int debug) {
      this(id, width, height, debug);
      this.positionX = positionX;
      this.positionY = positionY;
   }

   public LEDTable(String id, int width, int height, int debug) {
      this.id = id;
      this.debug = debug;
      this.width = width;
      this.height = height;
      this.mqttClient = new Mqtt("SimulatorMQTT", debug);
   }

   public void setPositionX(int positionX) {
      this.positionX = positionX;
   }

   public void setPositionY(int positionY) {
      this.positionY = positionY;
   }

   public String getId() {
      return this.id;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getPositionX() {
      return this.positionX;
   }

   public int getPositionY() {
      return this.positionY;
   }

   public void ledControl(String st) {
      this.mqttClient.connect();
      this.mqttClient.publish(this.id + "/leds", st);
      this.mqttClient.disconnect();
   }

   public String toString() {
      return "[LEDTable] id: " + this.id + " - width: " + this.width + " - height: " + this.height + " - position: " + this.positionX + "," + this.positionY;
   }
}
