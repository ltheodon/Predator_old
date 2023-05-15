package proie_predateur;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.emse.fayol.maqit.simulator.SimFactory;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;
import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;
import fr.emse.fayol.maqit.simulator.components.Obstacle;
import fr.emse.fayol.maqit.simulator.components.Orientation;
import fr.emse.fayol.maqit.simulator.components.Turtlebot;

public class Simul extends SimFactory {
	
	public Simul(SimProperties sp, GridManagement env) {
		super(sp,env);
		
		
		
		

		// Génération du contour (c'est plus joli et ça évite certains bugs durant les déplacement (mais il ne faut pas le dire :3)
		int[] place = new int[] {1,0};
		int[] obsColor = getIntFromColor(sp.colorobstacle);
		int[] agentColor = getIntFromColor(sp.colorrobot);
		int[] proieColor = getIntFromColor(sp.colorother);
		int[] obsColorFrame = getIntFromColor(sp.colorobstacle);
		obsColorFrame[0] = obsColorFrame[0]/2;
		obsColorFrame[1] = obsColorFrame[1]/2;
		obsColorFrame[2] = obsColorFrame[2]/2;
		
		for (int i = 0; i < sp.rows; i++) {
			place = new int[] {i,0};
			this.createObstacleInPlace(place,obsColor);
			place = new int[] {i,sp.columns-1};
			this.createObstacleInPlace(place,obsColor);
		}
		for (int i = 1; i < sp.columns-1; i++) {
			place = new int[] {0,i};
			this.createObstacleInPlace(place,obsColor);
			place = new int[] {sp.rows-1,i};
			this.createObstacleInPlace(place,obsColor);
		}
		
		
		
		// Generation des obstacles
		for (int i = 0; i < sp.nbobstacle; i++) {
			this.createObstacle(obsColor);
		}

		// Generation des agents
		int[] agentColor2 = {0,0,0};
		for (int i = 0; i < sp.nbrobot; i++) {
			int r = new Random().nextInt(60)-20;
			agentColor2[0] = Math.min(Math.max(agentColor[0] + r,0),255);
			agentColor2[1] = Math.min(Math.max(agentColor[1] + r,0),255);
			agentColor2[2] = Math.min(Math.max(agentColor[2] + r,0),255);
			this.createTurtlebot(agentColor2);
		}
		
		// Generation des proies
		int[] proieColor2 = {0,0,0};
		for (int i = 0; i < nbProie; i++) {
			int r = new Random().nextInt(80)-20;
			proieColor2[0] = Math.min(Math.max(proieColor[0] + r,0),255);
			proieColor2[1] = Math.min(Math.max(proieColor[1] + r,0),255);
			proieColor2[2] = Math.min(Math.max(proieColor[2] + r,0),255);
			this.createProie(proieColor2);
		}
		
		// Update des FoV
		for (int i = 0; i < sp.nbrobot; i++) {
			int[][] FoV = env.getNeighbor(lrobot.get(i).getX(), lrobot.get(i).getY(), lrobot.get(i).getField());
			((GridTurtlebot) lrobot.get(i)).updatePerception(FoV);
		}
		
		/*
		// Mouvement des robots
		for (int i = 0; i < sp.nbrobot; i++) {
			final int[] oldloc = lrobot.get(i).getLocation();
			lrobot.get(i).move(step);
			updateEnvironment(oldloc ,lrobot.get(i).getLocation(), lrobot.get(i).getId());
			System.out.println(Arrays.toString(oldloc));	
			System.out.println(Arrays.toString(lrobot.get(i).getLocation()));	
		}
		*/
		

		//System.out.println("field : " + field);
		
	}

	public int nbProie = sp.nbproie;
	//public int nbProie = 2;
	int step = 1;
	int field = sp.field;
	int fieldproie = sp.fieldProie;
	int debug = sp.debug;
	String agentName = "Agent";
	String proieName = "Proie";
	
	public int nbIteration = 0;
	private int proieEaten = 0;
	
	public int getProieEaten() {
		return this.proieEaten;
	}
	
	
	
	/*
	public void schedule() {
		foreach (Turtlebot bot : lrobot) {
			bot.getPerception();
			int [] place = bot.pos;
			int content = grid.getCellContent(place[0],place[1]);
			bot.move(sp.step);
			int [] nv_place = bot.getLocation();
			this.updateEnvironment(place, nv_place, content);
		}
	}*/
	
	
	
	// Parce que les types de couleurs sont codées avec les pieds...
	// Je dis ça, je ne dis rien...
	public int[] getIntFromColor(Color c){
		int[] C = new int[]{c.getRed(), c.getGreen(), c.getBlue()};
	    return C;
	}
	
	
	@Override
	public void createObstacle() {
		int[] place = environment.getPlace();
		Obstacle obs = new Obstacle(idObstacle, place);
		addNewComponent(obs);
		lobstacle.add(obs);
		idObstacle--;
	}
	
	@Override
	public void createObstacle(int[] color) {
		int[] place = environment.getPlace();
		Obstacle obs = new Obstacle(idObstacle, place);
		addNewComponent(obs,color);
		lobstacle.add(obs);
		idObstacle--;
	}
	
	public void createObstacleInPlace(int[] place, int[] color) {
		Obstacle obs = new Obstacle(idObstacle, place);
		addNewComponent(obs,color);
		lobstacle.add(obs);
		idObstacle--;
	}

			
	@Override
	public void createTurtlebot() {
		int[] place = environment.getPlace();
		Agent bot = new Agent(idRobot,agentName,field,debug,place,environment.getRows(),environment.getColumns(), sp.nbrobot, sp.stratPredateur);
		addNewComponent(bot);
		lrobot.add(bot);
		idRobot++;
	}

	@Override
	public void createTurtlebot(int[] color) {
		int[] place = environment.getPlace();
		Agent bot = new Agent(idRobot,agentName,field,debug,place,environment.getRows(),environment.getColumns(),sp.nbrobot, sp.stratPredateur);
		addNewComponent(bot,color);
		lrobot.add(bot);
		//System.out.println(Arrays.toString(place));
		idRobot++;
	}

	public void createProie(int[] color) {
		int[] place = environment.getPlace();
		Proie bot = new Proie(idRobot,proieName,fieldproie,debug,place,environment.getRows(),environment.getColumns(), sp.nbrobot, sp.stratProie);
		addNewComponent(bot,color);
		lrobot.add(bot);
		//System.out.println(Arrays.toString(place));
		idRobot++;
	}


	@Override
	public void schedule() {

		//System.out.println(idRobot);	
		//System.out.println("SCHEDULE");	
		
		// Calcul des variables locales pour les prédateurs
		for (int i = 0; i < sp.nbrobot; i++) {
			((Agent) lrobot.get(i)).checkMealPosition();
		}
			
		// Calcul des matrices de score pour les prédateurs
		float[][] TScores = MiscUtils.computeTravelingScores(lrobot.subList(0, sp.nbrobot));
	
		
		// Nouvelle mise à jour des états des prédateurs si nécessaire
		if(sp.stratPredateur == 2) {
			for (int i = 0; i < sp.nbrobot; i++) {
				int rowId = MiscUtils.getMaxIdFromRow(TScores, i);
				int state = ((Agent) lrobot.get(i)).getState();
				if(rowId != i) {
					((Agent) lrobot.get(i)).setState(2);
					int[] friendLocation = MiscUtils.getFieldTargetFromPositions(lrobot.get(i).getLocation(), lrobot.get(rowId).getLocation(), field);
					//System.out.println("friendLocation : " + Arrays.toString(friendLocation));	
					((Agent) lrobot.get(i)).setFriendLocation(friendLocation);
					((Agent) lrobot.get(i)).setCurrentOrientation(Orientation.up);
					int[][] FoV = environment.getNeighbor(lrobot.get(i).getX(), lrobot.get(i).getY(), lrobot.get(i).getField());
					((GridTurtlebot) lrobot.get(i)).updatePerception(FoV);
					((Agent) lrobot.get(i)).checkMealPosition();
				}else if(state == 2){
					((Agent) lrobot.get(i)).setState(0);
				}
			}
		}
		
		
		
		
		// Mouvement des robots
		for (int i = 0; i < idRobot-1; i++) {
			if (i < sp.nbrobot || ((Proie) lrobot.get(i)).getActif())
			{
				//System.out.println(":3 :3 :3");	
				final int[] oldloc = lrobot.get(i).getLocation();
				lrobot.get(i).move(step);
				
				if(!environment.validMove(oldloc, lrobot.get(i).getLocation(), lrobot.get(i).getId())) {
					//System.out.println("NOT VALID!");	
					if(!environment.eatingMove(oldloc, lrobot.get(i).getLocation(), sp.nbrobot)) {
						//System.out.println("NOT EATING VALID!");	
						lrobot.get(i).setLocation(oldloc);
						continue;
					}else {
						//System.out.println("EATING VALID!");							
					}
				}

				//System.out.println("Check 1");	
				int[][] FoV = environment.getNeighbor(oldloc[0], oldloc[1], 1);
				/*System.out.println("Check 2");	
				System.out.println("i : "+i);	
				System.out.println("sp.nbrobot : " + sp.nbrobot);	
				System.out.println("(Agent) lrobot.get(i)).getMeal() : " + (lrobot.get(i)).getMeal());	
				System.out.println("Content oldloc : " + environment.getContent(oldloc));	
				System.out.println("Content new : " + environment.getContent(lrobot.get(i).getLocation()));	*/
				
				if(i < sp.nbrobot && ((Agent) lrobot.get(i)).getMeal()>0)
				{
					//System.out.println("Check 3");	
					int mealId = ((Agent) lrobot.get(i)).getMeal()-1;
					/*System.out.println("MIAM!!!!");	
					System.out.println("MIAM!!!!");	
					System.out.println("MIAM!!!!");	
					System.out.println(i);	
					System.out.println(mealId);	*/
					((Proie) lrobot.get(mealId)).setActif(false);
					final int[] mealLoc = lrobot.get(mealId).getLocation();
					environment.delComponent(mealLoc);
					proieEaten++; // Update du nombre de proies mangées.
					

					FoV = environment.getNeighbor(oldloc[0], oldloc[1], 1);
					//System.out.println(Arrays.deepToString(FoV));
					
					/*try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
				}
				if(!(oldloc[0] == lrobot.get(i).getX() && oldloc[1] == lrobot.get(i).getY())){
					//System.out.println("Tried to move " + lrobot.get(i));
					//System.out.println(Arrays.deepToString(FoV));
					updateEnvironment(oldloc ,lrobot.get(i).getLocation(), lrobot.get(i).getId());
				}
				for (int j = 0; j < idRobot-1; j++) {
					//if (j < sp.nbrobot || ((Proie) lrobot.get(j)).getActif())
					//{
						FoV = environment.getNeighbor(lrobot.get(j).getX(), lrobot.get(j).getY(), lrobot.get(j).getField());
						((GridTurtlebot) lrobot.get(j)).updatePerception(FoV);
					//}
				}
			}
		}
		
		// Rechresh des cellules de background
		if(nbIteration % 10 == 0) {
			environment.updateBackgroundCells();
		}
		
		nbIteration++;
		
	}
	
}
