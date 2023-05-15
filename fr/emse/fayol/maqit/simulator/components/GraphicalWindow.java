/* Decompiler 23ms, total 118ms, lines 67 */
package fr.emse.fayol.maqit.simulator.components;

import fr.emse.fayol.maqit.simulator.environment.Cell;
import fr.emse.fayol.maqit.simulator.environment.ColorCell;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicalWindow {
   private int width;
   private int height;
   private int x;
   private int y;
   private int xratio;
   private int yratio;
   private String title;
   private JFrame window;
   private JPanel panel;
   private Cell[][] grid;

   public GraphicalWindow(Cell[][] grid, int posx, int posy, int width, int height, String title) {
      this.grid = grid;
      this.x = this.x;
      this.y = this.y;
      this.width = width;
      this.height = height;
      this.title = title;
      this.xratio = width / this.grid.length;
      this.yratio = height / this.grid[0].length;
   }

   public void init() {
      this.panel = new JPanel() {
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for(int row = 0; row < GraphicalWindow.this.grid.length; ++row) {
               for(int col = 0; col < GraphicalWindow.this.grid[0].length; ++col) {
                  int[] tc = ((ColorCell)GraphicalWindow.this.grid[row][col]).getColor();
                  Color block = new Color(tc[0], tc[1], tc[2]);
                  g.setColor(block);
                  g.fillRect(col * GraphicalWindow.this.xratio, row * GraphicalWindow.this.yratio, GraphicalWindow.this.xratio, GraphicalWindow.this.yratio);
               }
            }

         }

         public Dimension getPreferredSize() {
            return new Dimension(GraphicalWindow.this.width, GraphicalWindow.this.height);
         }
      };
      this.window = new JFrame(this.title);
      this.window.setSize(this.width, this.height + this.yratio + 22);
      this.window.setLocation(this.x, this.y);
      this.window.setDefaultCloseOperation(3);
      this.window.add(this.panel);
      this.window.setVisible(true);
   }

   public void refresh() {
      this.window.validate();
      this.window.repaint();
   }
}
