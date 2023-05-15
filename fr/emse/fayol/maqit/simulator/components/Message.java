/* Decompiler 5ms, total 100ms, lines 50 */
package fr.emse.fayol.maqit.simulator.components;

public class Message {
   protected int emitter;
   protected int receiver;
   protected String content;

   public Message(int emitter) {
      this.emitter = emitter;
      this.receiver = 0;
   }

   public Message(int emitter, String content) {
      this(emitter);
      this.content = content;
   }

   public void setReceiver(int receiver) {
      this.receiver = receiver;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public int getEmitter() {
      return this.receiver;
   }

   public int getReceiver() {
      return this.receiver;
   }

   public String getContent() {
      return this.content;
   }

   public String toString() {
      String st = "Message from " + this.emitter;
      if (this.receiver == 0) {
         st = st + " to all: ";
      } else {
         st = st + " to " + this.receiver + ": ";
      }

      st = st + this.content;
      return st;
   }
}
