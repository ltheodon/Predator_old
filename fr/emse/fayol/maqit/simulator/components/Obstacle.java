/* Decompiler 2ms, total 96ms, lines 12 */
package fr.emse.fayol.maqit.simulator.components;

public class Obstacle extends SituatedComponent {
   public Obstacle(int id, int[] location) {
      super(id, location);
   }

   public ComponentType getComponentType() {
      return ComponentType.obstacle;
   }
}
