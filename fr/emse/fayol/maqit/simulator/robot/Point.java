/* Decompiler 4ms, total 99ms, lines 14 */
package fr.emse.fayol.maqit.simulator.robot;

public class Point {
   int[] value;

   Point(int[] v) {
      this.value = new int[]{v[0], v[1]};
   }

   public boolean equals(Object o) {
      return ((Point)o).value[0] == this.value[0] && ((Point)o).value[1] == this.value[1];
   }
}
