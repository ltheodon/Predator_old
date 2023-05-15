/* Decompiler 5ms, total 95ms, lines 45 */
package fr.emse.fayol.maqit.simulator.environment;

public class ColorCell extends Cell {
   protected int[] color;
   protected ColorGoal goal;
   public static int[] defaultcolor;

   public ColorCell() {
      this.color = new int[3];
      this.color[0] = defaultcolor[0];
      this.color[1] = defaultcolor[1];
      this.color[2] = defaultcolor[2];
   }

   public ColorCell(int content) {
      super(content);
      this.color = new int[3];
      this.color[0] = defaultcolor[0];
      this.color[1] = defaultcolor[1];
      this.color[2] = defaultcolor[2];
   }

   public ColorCell(int content, int[] color) {
      this(content);
      System.arraycopy(color, 0, this.color, 0, 3);
   }

   public void setColorGoal(int g, int[] tc) {
      this.goal = new ColorGoal(g, tc);
   }

   public int[] getColor() {
      return this.color;
   }

   public void change(int content, int[] color) {
      this.content = content;
      System.arraycopy(color, 0, this.color, 0, 3);
   }

   public String toString() {
      return "Cell: " + this.content;
   }
}
