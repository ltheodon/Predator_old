/* Decompiler 5ms, total 100ms, lines 56 */
package fr.emse.fayol.maqit.simulator.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Debug {
   public static String sttime;

   public static void init() {
      sttime = "log-" + LocalDateTime.now();
      File f = new File(sttime);
      f.mkdir();
   }

   public static void mainLog(String message) {
      BufferedWriter writer = null;

      try {
         writer = new BufferedWriter(new FileWriter(sttime + "/main.log", true));
      } catch (IOException var4) {
         System.out.println(var4);
         System.exit(1);
      }

      try {
         writer.write(message + "\n");
         writer.close();
      } catch (IOException var3) {
         System.out.println(var3);
      }

   }

   public static void setRobotLog(String name, String message) {
      BufferedWriter writer = null;

      try {
         writer = new BufferedWriter(new FileWriter(sttime + "/" + name + ".log", true));
      } catch (IOException var5) {
         System.out.println(var5);
         System.exit(1);
      }

      try {
         writer.write(message + "\n");
         writer.close();
      } catch (IOException var4) {
         System.out.println(var4);
      }

   }
}
