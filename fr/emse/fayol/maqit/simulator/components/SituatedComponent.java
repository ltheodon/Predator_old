/* Decompiler 9ms, total 106ms, lines 54 */
package fr.emse.fayol.maqit.simulator.components;

public abstract class SituatedComponent {
   protected int x;
   protected int y;
   protected int id;

   public SituatedComponent(int id) {
      this.id = id;
   }

   public SituatedComponent(int id, int[] location) {
      this(id);
      this.x = location[0];
      this.y = location[1];
   }

   public abstract ComponentType getComponentType();

   public int getId() {
      return this.id;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setLocation(int[] loc) {
      this.x = loc[0];
      this.y = loc[1];
   }

   public int[] getLocation() {
      int[] loc = new int[]{this.x, this.y};
      return loc;
   }

   public String toString() {
      return "{type: " + this.getComponentType() + ", id: " + this.id + ", x: " + this.x + ", y: " + this.y + "}";
   }
}
