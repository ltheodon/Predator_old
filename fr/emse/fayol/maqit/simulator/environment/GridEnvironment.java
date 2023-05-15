/* Decompiler 15ms, total 107ms, lines 55 */
package fr.emse.fayol.maqit.simulator.environment;

public class GridEnvironment {
   protected Cell[][] grid;
   protected int rows;
   protected int columns;

   public GridEnvironment(int rows, int columns) {
      this.rows = rows;
      this.columns = columns;
      this.grid = new Cell[rows][columns];

      for(int i = 0; i < rows; ++i) {
         for(int j = 0; j < columns; ++j) {
            this.grid[i][j] = new Cell();
         }
      }

   }

   public void changeCell(int r, int c, int content) {
      this.grid[r][c].change(content);
   }

   public int getCellContent(int r, int c) {
      return this.grid[r][c].getContent();
   }

   public void setCellGoal(int r, int c, int g) {
      this.grid[r][c].setGoal(g);
   }

   protected boolean validCell(int r, int c) {
      return r >= 0 && c >= 0 && r < this.rows && c < this.columns;
   }

   public int[][] getNeighbor(int r, int c, int dist) {
      int[][] res = new int[2 * dist + 1][2 * dist + 1];

      for(int i = -dist; i <= dist; ++i) {
         for(int j = -dist; j <= dist; ++j) {
            if (!(i == 0 & j == 0)) {
               if (this.validCell(r + i, c + j)) {
                  res[i + dist][j + dist] = this.grid[r + i][c + j].getContent();
               } else {
                  res[i + dist][j + dist] = -1;
               }
            }
         }
      }

      return res;
   }
}
