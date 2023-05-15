/* Decompiler 3ms, total 104ms, lines 48 */
package fr.emse.fayol.maqit.simulator;

import fr.emse.fayol.maqit.simulator.components.Obstacle;
import fr.emse.fayol.maqit.simulator.components.SituatedComponent;
import fr.emse.fayol.maqit.simulator.components.Turtlebot;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;
import java.util.ArrayList;
import java.util.List;

public abstract class SimFactory {
   protected SimProperties sp;
   protected List<Turtlebot> lrobot;
   protected List<Obstacle> lobstacle;
   protected GridManagement environment;
   protected static int idObstacle = -2;
   protected static int idRobot = 1;

   public SimFactory(SimProperties sp, GridManagement env) {
      this.sp = sp;
      this.environment = env;
      this.lrobot = new ArrayList();
      this.lobstacle = new ArrayList();
      idObstacle = -2;
      idRobot = 1;
   }

   public abstract void createObstacle();

   public abstract void createObstacle(int[] var1);

   public abstract void createTurtlebot();

   public abstract void createTurtlebot(int[] var1);

   public void addNewComponent(SituatedComponent sc) {
      this.environment.addComponent(sc.getLocation(), sc.getId());
   }

   public void addNewComponent(SituatedComponent sc, int[] colorC) {
      this.environment.addComponent(sc.getLocation(), sc.getId(), colorC);
   }

   public void updateEnvironment(int[] from, int[] to, int content) {
      this.environment.moveComponent(from, to, content);
   }

   public abstract void schedule();
}
