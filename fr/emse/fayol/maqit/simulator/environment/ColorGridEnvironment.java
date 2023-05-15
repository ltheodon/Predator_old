/* Decompiler 14ms, total 108ms, lines 28 */
package fr.emse.fayol.maqit.simulator.environment;

public class ColorGridEnvironment extends GridEnvironment {
   public ColorGridEnvironment(int rows, int columns) {
      super(rows, columns);
      this.grid = new ColorCell[rows][columns];

      for(int i = 0; i < rows; ++i) {
         for(int j = 0; j < columns; ++j) {
            this.grid[i][j] = new ColorCell();
         }
      }

   }

   public void changeCell(int r, int c, int content, int[] color) {
      ((ColorCell)this.grid[r][c]).change(content, color);
   }

   public int[] getCellColor(int r, int c) {
      return ((ColorCell)this.grid[r][c]).getColor();
   }

   public void setCellColorGoal(int r, int c, int g, int[] color) {
      ((ColorCell)this.grid[r][c]).setColorGoal(g, color);
   }
}
