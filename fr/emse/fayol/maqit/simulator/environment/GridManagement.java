/* Decompiler 28ms, total 121ms, lines 149 */
package fr.emse.fayol.maqit.simulator.environment;

import fr.emse.fayol.maqit.simulator.components.GraphicalWindow;
import fr.emse.fayol.maqit.simulator.configuration.Debug;
import java.util.Random;
import fr.emse.fayol.maqit.simulator.environment.ColorCell;

public class GridManagement {
   protected int rows;
   protected int columns;
   protected GridEnvironment env;
   protected Random rnd;
   protected int debug;
   protected GraphicalWindow myframe = null;
   protected LEDTableManagement ledtablemanager = null;

   public GridManagement(int seed, int rows, int columns, int debug) {
      this.rnd = new Random((long)seed);
      this.rows = rows;
      this.columns = columns;
      this.debug = debug;
      this.env = new GridEnvironment(rows, columns);
   }

   public GridManagement(int seed, int rows, int columns, String title, int posx, int posy, int width, int height, int debug) {
      this.rnd = new Random((long)seed);
      this.rows = rows;
      this.debug = debug;
      this.columns = columns;
      this.env = new ColorGridEnvironment(rows, columns);
      this.myframe = new GraphicalWindow(this.env.grid, posx, posy, width, height, title);
      this.myframe.init();
   }

   public int getRows() {
      return this.rows;
   }

   public int getColumns() {
      return this.columns;
   }
   
   public void updateBackgroundCells()
   {
	   int[] bgColor =  ColorCell.defaultcolor;
	   for(int i = 0; i < this.rows; i++) {
		   for(int j = 0; j < this.columns; j++) {
			   if(this.env.grid[i][j].getContent() == 0) {
		            int[] tc = new int[3];
		            System.arraycopy(((ColorCell)this.env.grid[i][j]).getColor(), 0, tc, 0, 3);
		            for(int k = 0; k<3; k++) {
			            if(tc[k]>bgColor[k]) {tc[k]--;}
			            if(tc[k]<bgColor[k]) {tc[k]++;}
		            }
		            ((ColorGridEnvironment)this.env).changeCell(i, j, 0, tc);
			   }
		   }
	   }
       if (this.myframe != null) {
          this.myframe.refresh();
       }
   }
   
   

   public void setGoal(int[] pos, int status) {
      if (this.validPosition(pos[0], pos[1])) {
         this.env.setCellGoal(pos[0], pos[1], status);
         if (this.debug == 1) {
            System.out.println("[Environment] Change log of cell (" + pos[0] + "," + pos[1] + ") to " + status);
         } else if (this.debug == 2) {
            Debug.mainLog("[Environment] Change log of cell (" + pos[0] + "," + pos[1] + ") to " + status);
         }

         if (this.myframe != null) {
            this.myframe.refresh();
         }
      }

   }

   public boolean validPosition(int r, int c) {
      return r >= 0 & c >= 0 & r < this.rows & c < this.columns;
   }

   public int[][] getNeighbor(int r, int c, int dist) {
      return this.env.getNeighbor(r, c, dist);
   }

   public int[] getPlace() {
      int r = -1;
      int c = -1;
      boolean locationNotFound = true;

      while(locationNotFound) {
         r = this.rnd.nextInt(this.rows);
         c = this.rnd.nextInt(this.columns);
         if (this.env.getCellContent(r, c) == 0) {
            locationNotFound = false;
         }
      }

      int[] result = new int[]{r, c};
      return result;
   }

   public void addComponent(int[] pos, int content, int[] color) {
      int p0 = pos[0];
      int p1 = pos[1];
      if (this.validPosition(p0, p1) && this.env.grid[p0][p1].getContent() == 0) {
         if (this.debug == 1) {
            System.out.println("[Environment] Add color component at (" + p0 + "," + p1 + ") with " + content);
         } else if (this.debug == 2) {
            Debug.mainLog("[Environment] Add color component at (" + p0 + "," + p1 + ") with " + content);
         }

         ((ColorGridEnvironment)this.env).changeCell(p0, p1, content, color);
         if (this.myframe != null) {
            this.myframe.refresh();
         }
      }

   }

   public void addComponent(int[] pos, int content) {
      int p0 = pos[0];
      int p1 = pos[1];
      if (this.validPosition(p0, p1) && this.env.grid[p0][p1].getContent() == 0) {
         this.env.changeCell(p0, p1, content);
         if (this.debug == 1) {
            System.out.println("[Environment] Add component at (" + p0 + "," + p1 + ") with " + content);
         } else if (this.debug == 2) {
            Debug.mainLog("[Environment] Add component at (" + p0 + "," + p1 + ") with " + content);
         }
      }

   }

   public void delComponent(int[] pos) {
      int p0 = pos[0];
      int p1 = pos[1];
      this.env.changeCell(p0, p1, 0);
      if (this.debug == 1) {
         System.out.println("[Environment] Add component at (" + p0 + "," + p1 + ") with 0");
      } else if (this.debug == 2) {
         Debug.mainLog("[Environment] Add component at (" + p0 + "," + p1 + ") with 0");
      }

   }
   
   public boolean validMove(int[] from, int[] to, int content) {
	      int fr0 = from[0];
	      int fr1 = from[1];
	      int tr0 = to[0];
	      int tr1 = to[1];
	   return (this.validPosition(fr0, fr1) && this.validPosition(tr0, tr1) && this.env.grid[fr0][fr1].getContent() == content && this.env.grid[tr0][tr1].getContent() == 0);
   }
   
   public boolean eatingMove(int[] from, int[] to, int nbRobot) {
	      int fr0 = from[0];
	      int fr1 = from[1];
	      int tr0 = to[0];
	      int tr1 = to[1];
	   return (this.env.grid[fr0][fr1].getContent() <= nbRobot && this.env.grid[tr0][tr1].getContent() > nbRobot);
   }
   
   public int getContent(int[] from) {
	      int fr0 = from[0];
	      int fr1 = from[1];
	   return (this.env.grid[fr0][fr1].getContent());
   }

   public void moveComponent(int[] from, int[] to, int content) {
      int fr0 = from[0];
      int fr1 = from[1];
      int tr0 = to[0];
      int tr1 = to[1];
      if (this.validPosition(fr0, fr1) && this.validPosition(tr0, tr1) && this.env.grid[fr0][fr1].getContent() == content && this.env.grid[tr0][tr1].getContent() == 0) {
         if (this.env instanceof ColorGridEnvironment) {
            int[] tc = new int[3];
            System.arraycopy(((ColorCell)this.env.grid[fr0][fr1]).getColor(), 0, tc, 0, 3);
            //int[] tb = new int[]{0, 0, 0};
            int[] tb = new int[]{190,145,0};
			int r = new Random().nextInt(10)-5;
			tb[0] = Math.min(Math.max(tb[0] + r,0),200);
			tb[1] = Math.min(Math.max(tb[1] + r,0),150);
			tb[2] = Math.min(Math.max(tb[2] + r,0),255);
            ((ColorGridEnvironment)this.env).changeCell(fr0, fr1, 0, tb);
            ((ColorGridEnvironment)this.env).changeCell(tr0, tr1, content, tc);
            if (this.myframe != null) {
               this.myframe.refresh();
            }

            if (this.debug == 1) {
               System.out.println("[Environment] Move color component from (" + fr0 + "," + fr1 + ") to (" + tr0 + "," + tr1 + ") with " + content);
            } else if (this.debug == 2) {
               Debug.mainLog("[Environment] Move color component from (" + fr0 + "," + fr1 + ") to (" + tr0 + "," + tr1 + ") with " + content);
            }
         } else {
            this.env.changeCell(fr0, fr1, 0);
            this.env.changeCell(tr0, tr1, content);
            if (this.debug == 1) {
               System.out.println("[Environment] Move component from (" + fr0 + "," + fr1 + ") to (" + tr0 + "," + tr1 + ") with " + content);
            } else if (this.debug == 2) {
               Debug.mainLog("[Environment] Move component from (" + fr0 + "," + fr1 + ") to (" + tr0 + "," + tr1 + ") with " + content);
            }
         }
      }else {
    	  System.out.println("[Environment - FAIL] FAIL to move color component from (" + fr0 + "," + fr1 + ") to (" + tr0 + "," + tr1 + ") with " + content);
    	  //System.exit(0); 
    	  System.exit(1); 
      }

   }
}
