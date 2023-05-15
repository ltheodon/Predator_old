/* Decompiler 10ms, total 104ms, lines 55 */
package fr.emse.fayol.maqit.simulator.environment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LEDTableManagement {
   protected Set<LEDTable> ledtables;
   protected int debug;

   public LEDTableManagement(int debug) {
      this.debug = debug;
      this.ledtables = new HashSet();
   }

   public void addLEDTable(LEDTable lt) {
      this.ledtables.add(lt);
   }

   public void addLEDTable(String id, int width, int height, int positionX, int positionY) {
      this.ledtables.add(new LEDTable(id, width, height, positionX, positionY, this.debug));
   }

   public void initializeJsonTable(Cell[][] grid) {
      Iterator var2 = this.ledtables.iterator();

      while(var2.hasNext()) {
         LEDTable lt = (LEDTable)var2.next();
         JSONObject jslab = new JSONObject();
         JSONArray jcomps = new JSONArray();

         for(int i = 0; i < lt.getHeight(); ++i) {
            for(int j = 0; j < lt.getWidth(); ++j) {
               JSONObject jcomp = new JSONObject();
               jcomp.put("x", i);
               jcomp.put("y", j);
               Cell c = grid[i + lt.getPositionY()][j + lt.getPositionX()];
               JSONObject jc = new JSONObject();
               jc.put("r", ((ColorCell)c).getColor()[0]);
               jc.put("g", ((ColorCell)c).getColor()[1]);
               jc.put("b", ((ColorCell)c).getColor()[2]);
               jcomp.put("c", jc);
               jcomps.add(jcomp);
            }
         }

         jslab.put("led", jcomps);
         lt.ledControl(jslab.toJSONString());
      }

   }
}
