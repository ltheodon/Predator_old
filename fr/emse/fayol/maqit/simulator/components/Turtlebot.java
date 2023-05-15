/* Decompiler 9ms, total 103ms, lines 107 */
package fr.emse.fayol.maqit.simulator.components;

import fr.emse.fayol.maqit.simulator.configuration.Debug;

public abstract class Turtlebot extends SituatedComponent {
   protected String name;
   protected int debug;
   protected Orientation orientation;
   protected boolean goalReached;
   protected int field;

   protected Turtlebot(int id, String name, int field, int debug, int[] pos) {
      super(id, pos);
      this.name = name;
      this.debug = debug;
      this.orientation = Orientation.left;
      this.goalReached = false;
      this.field = field;
   }

   public boolean isGoalReached() {
      return this.goalReached;
   }

   public void setGoalReached(boolean gr) {
      this.goalReached = gr;
      if (this.debug == 2) {
         Debug.setRobotLog(this.name, "goal reached");
      }

   }

   public ComponentType getComponentType() {
      return ComponentType.robot;
   }

   public int getField() {
      return this.field;
   }

   public Orientation getCurrentOrientation() {
      return this.orientation;
   }

   public void setCurrentOrientation(Orientation or) {
      this.orientation = or;
   }

   public abstract void moveRight();

   public abstract void moveLeft();

   public abstract void moveForward();

   public abstract void moveBackward();

   public abstract void move(int var1);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean equals(Object o) {
      if (o instanceof Turtlebot) {
         return this.id == ((Turtlebot)o).getId();
      } else {
         return false;
      }
   }

   public void randomOrientation() {
      double d = Math.random();
      if (d < 0.25D) {
         if (this.orientation != Orientation.up) {
            this.orientation = Orientation.up;
         } else {
            this.orientation = Orientation.down;
         }
      } else if (d < 0.5D) {
         if (this.orientation != Orientation.down) {
            this.orientation = Orientation.down;
         } else {
            this.orientation = Orientation.up;
         }
      } else if (d < 0.75D) {
         if (this.orientation != Orientation.left) {
            this.orientation = Orientation.left;
         } else {
            this.orientation = Orientation.right;
         }
      } else if (this.orientation != Orientation.right) {
         this.orientation = Orientation.right;
      } else {
         this.orientation = Orientation.left;
      }

   }

   public String toString() {
      return "{type: " + this.getComponentType() + ", id: " + this.id + ", name: " + this.name + ", x: " + this.x + ", y: " + this.y + ", orientation:" + this.orientation + "}";
   }

public abstract boolean getMoving();

public abstract int getMeal();
}
