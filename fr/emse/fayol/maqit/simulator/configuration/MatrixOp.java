/* Decompiler 7ms, total 98ms, lines 38 */
package fr.emse.fayol.maqit.simulator.configuration;

public class MatrixOp {
   public static void transpose(int[][] m) {
      int s = m.length;

      for(int i = 0; i < s; ++i) {
         for(int j = i + 1; j < s; ++j) {
            int tmp = m[i][j];
            m[i][j] = m[j][i];
            m[j][i] = tmp;
         }
      }

   }

   public static void reverse(int[][] m) {
      int s = m.length;

      for(int i = 0; (double)i < Math.floor((double)(s / 2)); ++i) {
         int[] tmp = m[i];
         m[i] = m[s - 1 - i];
         m[s - 1 - i] = tmp;
      }

   }

   public static void rotateRight(int[][] m) {
      reverse(m);
      transpose(m);
   }

   public static void rotateLeft(int[][] m) {
      transpose(m);
      reverse(m);
   }
}
