/* Decompiler 17ms, total 112ms, lines 111 */
package fr.emse.fayol.maqit.simulator.robot;

import fr.emse.fayol.maqit.simulator.components.Orientation;
import fr.emse.fayol.maqit.simulator.components.Turtlebot;
import fr.emse.fayol.maqit.simulator.configuration.MatrixOp;
import java.util.Random;

public abstract class GridTurtlebot extends Turtlebot {
   protected Random rnd;
   protected int rows;
   protected int columns;
   protected int[][] grid;

   public GridTurtlebot(int id, String name, int field, int debug, int[] pos, int r, int c) {
      super(id, name, field, debug, pos);
      this.grid = new int[field * 2 + 1][field * 2 + 1];
      this.rows = r;
      this.columns = c;
   }

   public void updatePerception(int[][] mgrid) {
      int s = mgrid.length;

      for(int x = 0; x < s; ++x) {
         for(int y = 0; y < s; ++y) {
            this.grid[x][y] = mgrid[x][y];
         }
      }

      if (this.orientation == Orientation.left) {
         MatrixOp.rotateRight(this.grid);
      } else if (this.orientation == Orientation.right) {
         MatrixOp.rotateLeft(this.grid);
      } else if (this.orientation == Orientation.down) {
         MatrixOp.rotateLeft(this.grid);
         MatrixOp.rotateLeft(this.grid);
      }

   }

   public int[][] getPerception() {
      return this.grid;
   }

   public void moveLeft() {
      Orientation oldo = this.orientation;
      if (this.orientation == Orientation.up) {
         this.orientation = Orientation.left;
      } else if (this.orientation == Orientation.left) {
         this.orientation = Orientation.down;
      } else if (this.orientation == Orientation.right) {
         this.orientation = Orientation.up;
      } else {
         this.orientation = Orientation.right;
      }

   }

   public void moveRight() {
      Orientation oldo = this.orientation;
      if (this.orientation == Orientation.up) {
         this.orientation = Orientation.right;
      } else if (this.orientation == Orientation.left) {
         this.orientation = Orientation.up;
      } else if (this.orientation == Orientation.right) {
         this.orientation = Orientation.down;
      } else {
         this.orientation = Orientation.left;
      }

   }

   public void moveForward() {
      int xo = this.x;
      int yo = this.y;
      if (this.orientation == Orientation.up) {
         --this.x;
         this.x = Math.max(this.x, 0);
      } else if (this.orientation == Orientation.left) {
         --this.y;
         this.y = Math.max(this.y, 0);
      } else if (this.orientation == Orientation.right) {
         ++this.y;
         this.y = Math.min(this.y, this.columns - 1);
      } else {
         ++this.x;
         this.x = Math.min(this.x, this.rows - 1);
      }

   }

   public void moveBackward() {
      int xo = this.x;
      int yo = this.y;
      if (this.orientation == Orientation.up) {
         --this.x;
         this.x = Math.max(this.x, 0);
      } else if (this.orientation == Orientation.left) {
         ++this.y;
         this.y = Math.min(this.y, this.columns - 1);
      } else if (this.orientation == Orientation.right) {
         --this.y;
         this.y = Math.max(this.y, 0);
      } else {
         ++this.x;
         this.x = Math.min(this.x, this.rows - 1);
      }

   }
}
