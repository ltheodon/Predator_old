/* Decompiler 4ms, total 101ms, lines 35 */
package fr.emse.fayol.maqit.simulator.environment;

public class Cell {
   int content;
   Goal goal = null;

   protected Cell() {
      this.content = 0;
   }

   protected Cell(int content) {
      this.content = content;
   }

   protected void setGoal(int g) {
      this.goal = new Goal(g);
   }

   protected int getContent() {
      return this.content;
   }

   protected Goal getGoal() {
      return this.goal;
   }

   protected void change(int content) {
      this.content = content;
   }

   public String toString() {
      return "Cell: " + this.content;
   }
}
