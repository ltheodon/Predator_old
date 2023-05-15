/* Decompiler 6ms, total 102ms, lines 53 */
package fr.emse.fayol.maqit.simulator.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ComTurtlebot extends Turtlebot {
   protected Map<Integer, List<Message>> messages = new HashMap();
   public List<Message> sentMessages = new ArrayList();

   public ComTurtlebot(int id, String name, int field, int debug, int[] pos) {
      super(id, name, field, debug, pos);
   }

   public abstract void handleMessage(Message var1);

   public void sendMessage(Message msg) {
      this.sentMessages.add(msg);
   }

   public void readMessages() {
      Iterator var1 = this.messages.values().iterator();

      while(var1.hasNext()) {
         List<Message> lmsg = (List)var1.next();
         Iterator var3 = lmsg.iterator();

         while(var3.hasNext()) {
            Message msg = (Message)var3.next();
            this.handleMessage(msg);
         }
      }

   }

   public List<Message> searchMessages(int emitter) {
      return (List)this.messages.get(emitter);
   }

   public void receiveMessage(Message msg) {
      if (this.messages.containsKey(msg.getEmitter())) {
         ((List)this.messages.get(msg.getEmitter())).add(msg);
      } else {
         List<Message> lm = new ArrayList();
         lm.add(msg);
         this.messages.put(msg.getEmitter(), lm);
      }

   }
}
