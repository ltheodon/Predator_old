/* Decompiler 9ms, total 108ms, lines 39 */
package fr.emse.fayol.maqit.simulator.configuration;

import java.awt.Color;
import java.io.File;
import java.util.prefs.Preferences;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public class IniFile {
   private String filename;
   private Ini ini;
   Preferences prefs;

   public IniFile(String name) throws Exception {
      this.filename = name;
      this.ini = new Ini(new File(this.filename));
      this.prefs = new IniPreferences(this.ini);
   }

   public int getIntValue(String section, String prop) {
      return this.prefs.node(section).getInt(prop, 0);
   }

   public double getDoubleValue(String section, String prop) {
      return this.prefs.node(section).getDouble(prop, 0.0D);
   }

   public String getStringValue(String section, String prop) {
      return this.prefs.node(section).get(prop, (String)null);
   }

   public Color getColorValue(String section, String prop) {
      String c = this.prefs.node(section).get(prop, (String)null);
      String[] ccolrgb = c.split(",");
      Color mycolor = new Color(Integer.parseInt(ccolrgb[0]), Integer.parseInt(ccolrgb[1]), Integer.parseInt(ccolrgb[2]));
      return mycolor;
   }
}
