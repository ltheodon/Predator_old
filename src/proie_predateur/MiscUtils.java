package proie_predateur;

import java.util.List;

import fr.emse.fayol.maqit.simulator.components.Turtlebot;

public final class MiscUtils {
	
	private  MiscUtils() {}
	

	public static int[][] getAgentDistance(List<Turtlebot> lrobot){
		if(lrobot.size()==0) {
			return null;
		}
		int[][] M = new int[lrobot.size()][lrobot.size()];
		for(int i = 0; i < lrobot.size(); i++) {
			for(int j = 0; j < lrobot.size(); j++) {
				M[i][j] = distanceN1(new int[] {lrobot.get(i).getX(),lrobot.get(i).getY()},new int[] {lrobot.get(j).getX(),lrobot.get(j).getY()});
			}
		}
		return M;
	}
	
	public static float[][] computeTravelingScores(List<Turtlebot> lrobot){
		if(lrobot.size()==0) {
			return null;
		}
		//System.out.println("lrobot.size() : " + lrobot.size());	
		
		int[][] D = getAgentDistance(lrobot);
		float[][] M = new float[lrobot.size()][lrobot.size()];
		for(int i = 0; i < lrobot.size(); i++) {
			for(int j = 0; j < lrobot.size(); j++) {
				int d = D[i][j];
				float score = ((Agent) lrobot.get(j)).getLocalMealScore();
				if(d > 0) {
					//M[i][j] = (float) (score/Math.sqrt(d));		
					M[i][j] = (float) (score/d);					
				}else {
					M[i][j] = ((Agent) lrobot.get(i)).getLocalMealScore();
				}
			}
		}
		return M;
	}
	

	
	public static int getMaxIdFromRow(float[][] a, int row) {
		float sMax = a[row][row];
		int id = row;
		
		for(int j = 0; j < a.length; j++) {
			if(a[row][j] > sMax) {
				sMax = a[row][j];
				id = j;
			}
		}
		return id;
	}
	
	public static int[] getFieldTargetFromPositions(int[] from, int[] to, int field) {
		int f0 = from[0];
		int f1 = from[1];
		int t0 = to[0];
		int t1 = to[1];
		int x_ = field;
		int y_ = field;

		if(t0 < f0) {
			x_ = 0;
		}
		if(t0 > f0) {
			x_ = 2*field;
		}

		if(t1 < f1) {
			y_ = 0;
		}
		if(t1 > f1) {
			y_ = 2*field;
		}
		
		return new int[] {x_, y_};
	}
	
	
	
	

	public static int distanceN1(int[] a, int[] b) {
		int d = Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
		return d;
	}

	
	public static void printGrid(int[][] a)
	{
	   for(int i = 0; i < a.length; i++)
	   {
	      for(int j = 0; j < a[0].length; j++)
	      {
	         System.out.printf("%5d ", a[i][j]);
	      }
	      System.out.println();
	   }
	}
	
	public static void printGridFloat(float[][] a)
	{
	   for(int i = 0; i < a.length; i++)
	   {
	      for(int j = 0; j < a[0].length; j++)
	      {
	         System.out.printf("%5f ", a[i][j]);
	      }
	      System.out.println();
	   }
	}
	
}















