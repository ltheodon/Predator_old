/* Decompiler 8ms, total 192ms, lines 65 */
package fr.emse.fayol.maqit.simulator.configuration;

import java.awt.Color;

public class SimProperties {
   public int waittime;
   public int mqtt;
   public int display;
   public int debug;
   public int simulation;
   public int display_width;
   public int display_height;
   public int display_x;
   public int display_y;
   public String display_title;
   public int nbrobot;
   public int nbproie;
   public int nbobstacle;
   public int rows;
   public int seed;
   public int step;
   public int field;
   public int fieldProie;
   public int columns;
   public int stratProie;
   public int stratPredateur;   
   public Color colorrobot;
   public Color colorgoal;
   public Color colorobstacle;
   public Color colorother;
   public Color colorunknown;
   public IniFile ifile;

   public SimProperties(IniFile ifile) {
      this.ifile = ifile;
   }

   public void initMQTT() {
      this.mqtt = this.ifile.getIntValue("configuration", "mqtt");
   }

   public void simulationParams() {
      this.waittime = this.ifile.getIntValue("configuration", "waittime");
      this.display = this.ifile.getIntValue("configuration", "display");
      this.debug = this.ifile.getIntValue("configuration", "debug");
      this.simulation = this.ifile.getIntValue("configuration", "simulation");
      this.nbrobot = this.ifile.getIntValue("configuration", "robot");
      this.nbproie = this.ifile.getIntValue("configuration", "proie");
      this.nbobstacle = this.ifile.getIntValue("configuration", "obstacle");
      this.seed = this.ifile.getIntValue("configuration", "seed");
      this.step = this.ifile.getIntValue("configuration", "step");
      this.field = this.ifile.getIntValue("configuration", "field");
      this.fieldProie = this.ifile.getIntValue("configuration", "fieldProie");
      this.stratProie = this.ifile.getIntValue("strategy", "prey");
      this.stratPredateur = this.ifile.getIntValue("strategy", "predator");
      this.rows = this.ifile.getIntValue("environment", "rows");
      this.columns = this.ifile.getIntValue("environment", "columns");
   }

   public void displayParams() {
      this.display_width = this.ifile.getIntValue("display", "width");
      this.display_height = this.ifile.getIntValue("display", "height");
      this.display_x = this.ifile.getIntValue("display", "x");
      this.display_y = this.ifile.getIntValue("display", "y");
      this.display_title = this.ifile.getStringValue("display", "title");
      this.colorrobot = this.ifile.getColorValue("color", "robot");
      this.colorgoal = this.ifile.getColorValue("color", "goal");
      this.colorobstacle = this.ifile.getColorValue("color", "obstacle");
      this.colorother = this.ifile.getColorValue("color", "other");
      this.colorunknown = this.ifile.getColorValue("color", "unknown");
   }
}
