/* Decompiler 25ms, total 123ms, lines 262 */
package fr.emse.fayol.maqit.simulator.robot;

import fr.emse.fayol.maqit.simulator.configuration.RosbridgeClient;
import org.json.simple.JSONObject;

public abstract class RealTurtlebot extends GridTurtlebot {
   protected RosbridgeClient clientRosbridge;
   public static int waitTimeCommunication = 300;
   public static int waitTimeAction = 3000;
   public static String ip = "10.200.3.101";
   public static String port = "9090";
   protected boolean myactionresult = false;

   public RealTurtlebot(int id, String name, int field, int debug, int[] pos, int r, int c, RosbridgeClient rbc) {
      super(id, name, field, debug, pos, r, c);
      this.clientRosbridge = rbc;
   }

   public void moveForward() {
      try {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/burger_move/goal");
         JSONObject msg = new JSONObject();
         JSONObject jos = new JSONObject();
         jos.put("secs", 0);
         jos.put("nsecs", 0);
         JSONObject joh = new JSONObject();
         joh.put("seq", 0);
         joh.put("stamp", jos);
         joh.put("frame_id", "");
         msg.put("header", joh);
         JSONObject joi = new JSONObject();
         joi.put("stamp", jos);
         joi.put("id", "");
         msg.put("goal_id", joi);
         JSONObject jog = new JSONObject();
         jog.put("distance", 30);
         jog.put("direction", "forward");
         msg.put("goal", jog);
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());
         System.out.println("Message ROS" + message.toJSONString());

         while(!this.myactionresult) {
            Thread.sleep((long)waitTimeAction);
         }

         this.myactionresult = false;
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      super.moveForward();
   }

   public void moveBackward() {
      try {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/robot_command");
         JSONObject msg = new JSONObject();
         msg.put("data", "backward");
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());

         while(!this.myactionresult) {
            Thread.sleep((long)waitTimeAction);
         }

         this.myactionresult = false;
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      super.moveBackward();
   }

   public void moveLeft() {
      try {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/burger_move/goal");
         JSONObject msg = new JSONObject();
         JSONObject jos = new JSONObject();
         jos.put("secs", 0);
         jos.put("nsecs", 0);
         JSONObject joh = new JSONObject();
         joh.put("seq", 0);
         joh.put("stamp", jos);
         joh.put("frame_id", "");
         msg.put("header", joh);
         JSONObject joi = new JSONObject();
         joi.put("stamp", jos);
         joi.put("id", "");
         msg.put("goal_id", joi);
         JSONObject jog = new JSONObject();
         jog.put("distance", 155);
         jog.put("direction", "left");
         msg.put("goal", jog);
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());

         while(!this.myactionresult) {
            Thread.sleep((long)waitTimeAction);
         }

         this.myactionresult = false;
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      super.moveLeft();
   }

   public void moveRight() {
      try {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/burger_move/goal");
         JSONObject msg = new JSONObject();
         JSONObject jos = new JSONObject();
         jos.put("secs", 0);
         jos.put("nsecs", 0);
         JSONObject joh = new JSONObject();
         joh.put("seq", 0);
         joh.put("stamp", jos);
         joh.put("frame_id", "");
         msg.put("header", joh);
         JSONObject joi = new JSONObject();
         joi.put("stamp", jos);
         joi.put("id", "");
         msg.put("goal_id", joi);
         JSONObject jog = new JSONObject();
         jog.put("distance", 155);
         jog.put("direction", "right");
         msg.put("goal", jog);
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());

         while(!this.myactionresult) {
            Thread.sleep((long)waitTimeAction);
         }

         this.myactionresult = false;
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      super.moveRight();
   }

   public void stopRobot() {
      JSONObject message = new JSONObject();
      message.put("topic", "/" + this.name + "/cmd_vel");
      JSONObject msg = new JSONObject();
      JSONObject linear = new JSONObject();
      linear.put("x", 0.0D);
      linear.put("y", 0.0D);
      linear.put("z", 0.0D);
      JSONObject angular = new JSONObject();
      angular.put("x", 0.0D);
      angular.put("y", 0.0D);
      angular.put("z", 0.0D);
      msg.put("linear", linear);
      msg.put("angular", angular);
      message.put("msg", msg);
      message.put("op", "publish");
      this.clientRosbridge.getWsc().send(message.toJSONString());
   }

   public void moveRobot() {
      JSONObject message = new JSONObject();
      message.put("topic", "/" + this.name + "/cmd_vel");
      JSONObject msg = new JSONObject();
      JSONObject twist1 = new JSONObject();
      JSONObject linear = new JSONObject();
      linear.put("x", 0.04D);
      linear.put("y", 0.0D);
      linear.put("z", 0.0D);
      JSONObject angular = new JSONObject();
      angular.put("x", 0.0D);
      angular.put("y", 0.0D);
      angular.put("z", 0.0D);
      twist1.put("linear", linear);
      twist1.put("angular", angular);
      msg.put("linear", linear);
      msg.put("angular", angular);
      message.put("msg", msg);
      message.put("op", "publish");
      this.clientRosbridge.getWsc().send(message.toJSONString());

      try {
         Thread.sleep((long)waitTimeAction);
      } catch (InterruptedException var7) {
         var7.printStackTrace();
      }

      this.stopRobot();
   }

   public void rotateLeft(int step) {
      for(int i = 0; i < step; ++i) {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/cmd_vel");
         JSONObject msg = new JSONObject();
         JSONObject linear = new JSONObject();
         linear.put("x", 0.0D);
         linear.put("y", 0.0D);
         linear.put("z", 0.0D);
         JSONObject angular = new JSONObject();
         angular.put("x", 0.0D);
         angular.put("y", 0.0D);
         angular.put("z", 1.0D);
         msg.put("linear", linear);
         msg.put("angular", angular);
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());

         try {
            Thread.sleep((long)waitTimeCommunication);
         } catch (InterruptedException var8) {
            var8.printStackTrace();
         }

         this.stopRobot();
      }

   }

   public void rotateRight(int step) {
      for(int i = 0; i < step; ++i) {
         JSONObject message = new JSONObject();
         message.put("topic", "/" + this.name + "/cmd_vel");
         JSONObject msg = new JSONObject();
         JSONObject linear = new JSONObject();
         linear.put("x", 0.0D);
         linear.put("y", 0.0D);
         linear.put("z", 0.0D);
         JSONObject angular = new JSONObject();
         angular.put("x", 0.0D);
         angular.put("y", 0.0D);
         angular.put("z", -1.0D);
         msg.put("linear", linear);
         msg.put("angular", angular);
         message.put("msg", msg);
         message.put("op", "publish");
         this.clientRosbridge.getWsc().send(message.toJSONString());

         try {
            Thread.sleep((long)waitTimeCommunication);
         } catch (InterruptedException var8) {
            var8.printStackTrace();
         }

         this.stopRobot();
      }

   }
}
