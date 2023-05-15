package proie_predateur;


import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import astar.AStar;
import astar.Node;

public class Proie extends GridTurtlebot {

	public Proie(int id, String name, int field, int debug, int[] pos, int r, int c, int nbrobot, int strat) {
		super(id, name, field, debug, pos, r, c);
		nbRobot = nbrobot;
		strategy = strat;
	}

	public int nbRobot;
	public boolean moving = true;
	public boolean actif = true;
	public boolean isLeader = false;
	public boolean isRunning = false;
	List<int[]> predatorList = new ArrayList<int[]>();
	public int[] leaderPosition = {0,0};
	public int[] killerPosition = {0,0};
	public int strategy = 0;
	

	public boolean getActif()
	{
		return actif;
	}
	public boolean getMoving()
	{
		return moving;
	}
	
	

	@Override
	public void move(int arg0) {
		this.moving = false;
		checkLeaderAndChassed();
		// RANDOM WALK SI LEADER
		int R = new Random().nextInt(25);
		int Rstill = new Random().nextInt(10);
		if(!isRunning && (isLeader || R > 20) && Rstill > 1) {
			int[][] positions = positionsScores();
			int[] pos = getBestPosition(positions);
			
			if(pos[2] == 0) {
				return;
			}
			switch(pos[3]) {	
			
				case 0:
					int r = new Random().nextInt(12);
					if(positions[1][2] > 0 && r > 10) {
						moveLeft();
					}else if(positions[2][2] > 0 && r < 2) {
						moveRight();
					}
					moveForward();
					this.moving = true;
					break;		
					
				case 1:
					moveLeft();
					moveForward();
					this.moving = true;
					break;
					
				case 2:
					moveRight();
					moveForward();
					this.moving = true;
					break;
					
				case 3:
					moveBackward();
					this.moving = true;
					break;
			}
		}
		else {
			int[][] FoV = this.getPerception();
			int[] target = new int[] {0,0};
			if(!isRunning) {
				target = shortestNode(FoV, leaderPosition);
			}else {
				//System.out.println("I'm running! :)");
				
				/*********************************************************
				DEUX STRATS DEVITEMENT POUR LES PROIES
				giveValidRunningPosition:	 	 calcule la meilleure destination en fonction du prédateur le plus proche.
				giveBetterValidRunningPosition:	 calcule la meilleure destination en fonction de tous les prédateurs visibles.
				*********************************************************/
				int[] runningPosition = {0,0};
				if(strategy == 0) {
					runningPosition = giveValidRunningPosition();
				}else {
					runningPosition = giveBetterValidRunningPosition();
				}
				
				target = shortestNode(FoV, runningPosition);
			}

			// Mouvements diag
			if(target[0] == field-1 && target[1] == field-1) {
				moveForward();
				moveLeft();
				moveForward();
				this.moving = true;
				return;
			}
			if(target[0] == field-1 && target[1] == field+1) {
				moveForward();
				moveRight();
				moveForward();
				this.moving = true;
				return;
			}
			if(target[0] == field+1 && target[1] == field-1) {
				moveLeft();
				moveForward();
				moveLeft();
				moveForward();
				this.moving = true;
				return;
			}
			if(target[0] == field+1 && target[1] == field+1) {
				moveRight();
				moveForward();
				moveRight();
				moveForward();
				this.moving = true;
				return;
			}
			
			// Mouvements N4
			if(target[0] == field-1) {
				moveForward();
				this.moving = true;
				return;
			}
			if(target[0] == field+1) {
				moveBackward();
				this.moving = true;
				return;
			}
			if(target[1] == field-1) {
				moveLeft();
				moveForward();
				this.moving = true;
				return;
			}
			if(target[1] == field+1) {
				moveRight();
				moveForward();
				this.moving = true;
				return;
			}
			
		}
		return;
	}
	
	
	public int[] shortestNode(int[][] FoV, int[] target){
        Node initialNode = new Node(field, field);
        Node finalNode = new Node(target[0], target[1]);
        int rows_ = 2*field + 1;
        int cols_ = 2*field + 1;
        AStar aStar = new AStar(rows_, cols_, initialNode, finalNode);
        
        List<int[]> blockList = new ArrayList<int[]>();
        //System.out.println("Original block size : " + blockList.size());
		for (int i = 0; i < 2*field + 1; i++) {
			for (int j = 0; j < 2*field + 1; j++) {

		        //System.out.println("FoV[i][j] != 0 : " + (FoV[i][j] != 0));
				if(FoV[i][j] != 0 && (i != target[0] || j!= target[1])) {
					//System.out.println("FoV["+i+"]["+j+"] : " + FoV[i][j]);
					int[] block = new int[] {i, j};
					blockList.add(block);
			        //System.out.println("Plus one block size : " + blockList.size());
				}
			}
		}
        //System.out.println("Block size : " + blockList.size());
		
		int[][] blocksArray;
		if(blockList.size() > 0) {
			blocksArray = new int[blockList.size()][2];
			int count = 0;
			for (int[] block_ : blockList) {
				blocksArray[count] = block_;
				count++;
	        }
		}else {
			blocksArray = new int[][] {{0,0}};
		}

        //System.out.println("blocksArray length : " + blocksArray.length);
        //System.out.println(Arrays.deepToString(blocksArray));
        //printGrid(FoV);
		
        
		aStar.setBlocks(blocksArray);
		// On aurotise les mouvements diagonaux si isRunning = true
        List<Node> path = aStar.findPath(isRunning);
        
        /*for (Node node : path) {
            System.out.println(node);
        }*/

        //System.out.println(path.size());
        int[] lastNode = {field, field};
		if(path.size() > 1) {
			lastNode = new int[] {path.get(1).getRow(), path.get(1).getCol()};
		}
        //System.out.println(Arrays.toString(lastNode));
        
		return lastNode;
	}
	
	
	
	public int[] getBestPosition(int[][] positions)
	{
		int score = 0;
		int[] pos = {field,field,0,-1};
		for(int i = 0; i < 4; i++) {
			if(score < positions[i][2]) {
				pos = positions[i];
				score = positions[i][2];
			}
		}
		return pos;
	}
	
	
	public int[][] positionsScores() {
		int[][] positions = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		int[][] FoV = this.getPerception();

		positions[0] = checkPosition(FoV, field-1, field,0);
		positions[1] = checkPosition(FoV, field, field-1,1);
		positions[2] = checkPosition(FoV, field, field+1,2);
		positions[3] = checkPosition(FoV, field+1, field,3);
		
		return positions;
	}
	
	public int[] checkPosition(int[][] FoV, int i, int j, int a) {
		int[] pos = {0,0,0,a};
		if(FoV[i][j] != 0) {
			if(FoV[i][j] < 0) {
				pos[0] = i;
				pos[1] = j;
				pos[2] = 0;
			}else {
				pos[0] = i;
				pos[1] = j;
				pos[2] = 0;		
			}
		}else {
			pos[0] = i;
			pos[1] = j;
			pos[2] = 100;
		}
		return pos;
	}
	
	
	

	public void setActif(boolean actif_) {
		actif = actif_;
	}

	
	public void checkLeaderAndChassed() {
		int[][] FoV = this.getPerception();
		this.isLeader = true;
		this.isRunning = false;
		this.predatorList.clear();
		this.leaderPosition = new int[] {0,0};
		this.killerPosition = new int[] {0,0};
		int maxLeader = this.id;
		int[] relPosition = new int[] {field, field};
		for (int i = 0; i < FoV.length; i++) {
			for (int j = 0; j < FoV[i].length; j++) {
				if(FoV[i][j] > maxLeader) {
					isLeader = false;
					this.leaderPosition[0] = i;
					this.leaderPosition[1] = j;
					maxLeader = FoV[i][j];
				}
				if(FoV[i][j] > 0 && FoV[i][j] <= nbRobot) {
					isRunning = true;
					this.predatorList.add(new int[] {i,j});
					if(MiscUtils.distanceN1(this.killerPosition, relPosition) > MiscUtils.distanceN1(new int[] {i, j}, relPosition)) {
						this.killerPosition[0] = i;
						this.killerPosition[1] = j;
					}
				}
			}
		}
		return;
	}

	public int[] giveBetterValidRunningPosition() {
		if(this.predatorList.size() == 0) {
			return new int[] {field, field};
		}
		
		int[][] FoV = this.getPerception();
		int[][] distanceMap = new int[FoV.length][FoV[0].length];
		
		int[] position = new int[] {0,0};
		int dMax = 0;
		
		for(int i = 0; i < FoV.length; i++) {
			for(int j = 0; j < FoV[0].length; j++) {
				if(FoV[i][j] == 0) {
					distanceMap[i][j] = getShortestDistanceFromKillerList(new int[] {i,j});
					if(distanceMap[i][j] > dMax) {
						dMax = distanceMap[i][j];
						position = new int[] {i,j};
					}
				}else {
					distanceMap[i][j] = 0;
				}
			}
		}
		return position;
	}
	
	public int getShortestDistanceFromKillerList(int[] position) {
		int D = 4*field*field;
		for(int[] posistion_: this.predatorList) {
			int D_ = MiscUtils.distanceN1(position,posistion_);
			if(D_ < D) {
				D = D_;
			}
		}
		return D;
	}
	
	
	public int[] giveValidRunningPosition() {
		int[][] FoV = this.getPerception();
		//System.out.println("---");
		//System.out.println(Arrays.toString(killerPosition));
		int[] runningPosition = new int[] {2*field - killerPosition[0], 2*field - killerPosition[1]};
		//System.out.println(Arrays.toString(runningPosition));
		if(this.killerPosition[0] > field) {
			runningPosition[0] = 0;
		}else if(this.killerPosition[0] <= field) {
			runningPosition[0] = 2*field;
		}
		if(this.killerPosition[1] > field) {
			runningPosition[1] = 0;
		}else if(this.killerPosition[1] <= field) {
			runningPosition[1] = 2*field;
		}
		//System.out.println(Arrays.toString(runningPosition));
		//System.out.println("---");
		
		if(FoV[runningPosition[0]][runningPosition[1]] == 0) {
			return runningPosition;
		}
		
		int[] tempRunningPosition = runningPosition;
		int maxValid = 4*field*field;
		outerloop:
		for (int i = 0; i < FoV.length; i++) {
			for (int j = 0; j < FoV[i].length; j++) {
				if((FoV[i][j] == 0 || FoV[i][j] > nbRobot) && (MiscUtils.distanceN1(runningPosition, new int[] {i, j}) < maxValid)) {
					tempRunningPosition[0] = i;
					tempRunningPosition[1] = j;
					maxValid = MiscUtils.distanceN1(runningPosition, new int[] {i, j});
					if(maxValid == 0) {
						break outerloop;
					}
					//System.out.println((maxValid));
				}
			}
		}
		return tempRunningPosition;
	}
	
	
	
		
	public boolean isLeader() {
		int[][] FoV = this.getPerception();
		boolean isLeader_ = true;
		this.leaderPosition = new int[] {0,0};
		outerloop:
		for (int i = 0; i < FoV.length; i++) {
			for (int j = 0; j < FoV[i].length; j++) {
				if(FoV[i][j] > this.id) {
					isLeader_ = false;
					this.leaderPosition[0] = i;
					this.leaderPosition[1] = j;
					break outerloop;
				}
			}
		}
		return isLeader_;
	}
		
		
	@Override
	public int getMeal() {
		return 0;
	}
	
}
