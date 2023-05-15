/* Decompiler 1ms, total 96ms, lines 15 */
package fr.emse.fayol.maqit.simulator.environment;

public class ColorGoal extends Goal {
   protected int[] color;

   public ColorGoal(int goal, int[] color) {
      super(goal);
      System.arraycopy(color, 0, this.color, 0, 3);
   }

   public int[] getColor() {
      return this.color;
   }
}
