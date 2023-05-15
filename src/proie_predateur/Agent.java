package proie_predateur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import astar.AStar;
import astar.Node;
import fr.emse.fayol.maqit.simulator.robot.GridTurtlebot;



public class Agent extends GridTurtlebot {


	public Agent(int id,String name,int field,int debug, int[] pos, int r, int c, int nbrobot, int strat) {
		super(id,name,field,debug,pos,r,c);
		nbRobot = nbrobot;
		strategy = strat;
		
		//System.out.println(Arrays.toString(pos));
		//System.out.println(Arrays.toString(this.getLocation()));
	}

	public int nbRobot;
	public int nbLocalProie = 0;
	public int distLocalProie = 10000;
	
	public boolean moving = true;
	public boolean actif = true;
	
	public int[] mealPosition = {0,0};
	List<int[]> mealList = new ArrayList<int[]>();
	private float localMealScore = 0f;
	public boolean targetFound = false;
	public int meal = 0;
	
	public int strategy = 0;
	
	private int state = 0; // 0 = wandering, 1 = hunting, 2 = looking for a friend.
	private int[] friendLocation = {0,0};
	
	
	public boolean getActif()
	{
		return actif;
	}

	public boolean getMoving()
	{
		return moving;
	}
	
	public int getMeal()
	{
		return meal;
	}

	@Override
	public void move(int a) {
		
		if(strategy == 2) {
			if(state < 2) {
				if(nbLocalProie > 0 && distLocalProie < 2) {
					moveBasicStrategy();
				}else {
					moveNormalStrategy();
				}
			}else {
				if(nbLocalProie > 0 && distLocalProie < 2) {
					moveBasicStrategy();
				}else {
					moveToFriend();
				}
				this.state = 0;
			}
		}else if(strategy == 1) {
			if(nbLocalProie > 0 && distLocalProie < 2) {
				moveBasicStrategy();
			}else {
				moveNormalStrategy();
			}
		} else {
			moveBasicStrategy();
		}
	}
	
	
	public void moveToFriend() {
		// GOTO FRIEND IF ALONE
		// SE DIRIGE VERS LA PROIE LA PLUS PROCHE SI DANS LE VOISINAGE (A*)
		int[][] FoV = this.getPerception();
		this.moving = false;

		int[] target = new int[] {0,0};
		//System.out.println("friend BEFORE : " + Arrays.toString(friendLocation));
		target = shortestNode(FoV, getClostestValidTarget(FoV, friendLocation));
		//System.out.println("friend AFTER: " + Arrays.toString(target));
		//MiscUtils.printGrid(FoV);

		if(target[0] == field-1) {
			moveForward();
			meal = FoV[field-1][field];
			this.moving = true;
			return;
		}
		if(target[0] == field+1) {
			//System.out.println("field+1 - (9,8) ?");
			moveLeft();
			moveLeft();
			moveForward();
			meal = FoV[field+1][field];
			this.moving = true;
			return;
		}
		if(target[1] == field-1) {
			moveLeft();
			moveForward();
			meal = FoV[field][field-1];
			this.moving = true;
			return;
		}
		if(target[1] == field+1) {
			moveRight();
			moveForward();
			meal = FoV[field][field+1];
			this.moving = true;
			return;
		}
		return;
	}
	
	
	public void moveNormalStrategy() {
		// RANDOM WALK SI ALONE
		// SE DIRIGE VERS LA PROIE LA PLUS PROCHE (A*)
		int[][] FoV = this.getPerception();
		this.moving = false;
		int Rstill = new Random().nextInt(10);
		
		if(targetFound) {
			state = 1;
		}
		if(!targetFound && Rstill > 2) {
			state = 0;
		}
		
		
		if(state == 0) {
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
						meal = FoV[field][field-1];
					}else if(positions[2][2] > 0 && r < 2) {
						moveRight();
						meal = FoV[field][field+1];
					}else {
						meal = FoV[field-1][field];
					}
					moveForward();
					this.moving = true;
					break;		
					
				case 1:
					moveLeft();
					meal = FoV[field][field-1];
					moveForward();
					this.moving = true;
					break;
					
				case 2:
					moveRight();
					meal = FoV[field][field+1];
					moveForward();
					this.moving = true;
					break;
					
				case 3:
					moveRight();
					moveRight();
					moveForward();
					meal = FoV[field+1][field];
					this.moving = true;
					break;
			}
		}
		else if(state == 1){
			int[] target = new int[] {0,0};
			target = shortestNode(FoV, mealPosition);

			if(target[0] == field-1) {
				moveForward();
				meal = FoV[field-1][field];
				this.moving = true;
				return;
			}
			if(target[0] == field+1) {
				moveBackward();
				meal = FoV[field+1][field];
				this.moving = true;
				return;
			}
			if(target[1] == field-1) {
				moveLeft();
				moveForward();
				meal = FoV[field][field-1];
				this.moving = true;
				return;
			}
			if(target[1] == field+1) {
				moveRight();
				moveForward();
				meal = FoV[field][field+1];
				this.moving = true;
				return;
			}
			
		}
		return;
	}
	
	
	public void moveBasicStrategy() {
		// RANDOM WALK SI ALONE
		// L'agent va tenter de manger une proie qui passe juste à côté de lui.
		this.moving = false;
		meal = 0;
		int[][] FoV = this.getPerception();
		int R = new Random().nextInt(25);
		if(R > -1) {
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
						meal = FoV[field][field-1];
					}else if(positions[2][2] > 0 && r < 2) {
						moveRight();
						meal = FoV[field][field+1];
					}else {
						meal = FoV[field-1][field];
					}
					moveForward();
					this.moving = true;
					break;		
				case 1:
					moveLeft();
					moveForward();
					meal = FoV[field][field-1];
					this.moving = true;
					break;
					
				case 2:
					moveRight();
					moveForward();
					meal = FoV[field][field+1];
					this.moving = true;
					break;
					
				case 3:
					moveRight();
					moveRight();
					moveForward();
					meal = FoV[field+1][field];
					this.moving = true;
					break;
			}
		}
	}
	
	
	public void checkMealPosition() {
		this.localMealScore = 0;
		this.mealList.clear();
		this.nbLocalProie = 0;
		this.distLocalProie = 10000;
		int[][] FoV = this.getPerception();
		this.targetFound = false;
		this.mealPosition = new int[] {0,0};
		int minMeal = field*field;
		int[] currentPosition = new int[] {field,field};
		for (int i = 0; i < FoV.length; i++) {
			for (int j = 0; j < FoV[i].length; j++) {
				if(FoV[i][j] > nbRobot) {
					int d = MiscUtils.distanceN1(new int[] {i,j}, currentPosition);
					this.nbLocalProie++;
					this.mealList.add(new int[] {i,j,d});
					localMealScore = getLocalMealScore() + 1f/d;
					if(d < minMeal) {
						this.targetFound = true;
						this.mealPosition[0] = i;
						this.mealPosition[1] = j;
						minMeal = MiscUtils.distanceN1(new int[] {i,j}, currentPosition);
					}
				}
				
			}
		}
		this.distLocalProie = minMeal;
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

				if(FoV[i][j] != 0 &&  FoV[i][j] <= nbRobot) {
					int[] block = new int[] {i, j};
					blockList.add(block);
				}
			}
		}
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

        
		aStar.setBlocks(blocksArray);
        List<Node> path = aStar.findPath();
        
        if(state == 2) {
    		/*System.out.println("path target : " + Arrays.toString(target));
    		System.out.println("path : " + path.size());
    		System.out.println("field : " + field);*/
    		//System.out.println("target VALUE : " + FoV[target[0]][target[1]]);
    		//System.out.println("path : " + path.size());
        }
        
        int[] lastNode = {field, field};
		if(path.size() > 1) {
			lastNode = new int[] {path.get(1).getRow(), path.get(1).getCol()};
		}
        
		return lastNode;
	}
	
	public int[] getClostestValidTarget(int[][] FoV, int[] target) {
		int dMin = 4*FoV.length*FoV.length;
		int[] target_ = {0,0};
		
		if(FoV[target[0]][target[1]] == 0) {
			return target;
		}
		
		
		for (int i = 0; i < FoV.length; i++) {
			for (int j = 0; j < FoV[0].length; j++) {
				if(FoV[i][j] == 0) {
					int d = MiscUtils.distanceN1(new int[] {i,j}, target);
					if(d < dMin) {
						dMin = d;
						target_[0] = i;
						target_[1] = j;
					}
				}
				
			}
		}
		
		return target_;
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
			}else if(FoV[i][j] > nbRobot){
				pos[0] = i;
				pos[1] = j;
				pos[2] = 1000;		
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getLocalMealScore() {
		return localMealScore;
	}

	public int[] getFriendLocation() {
		return friendLocation;
	}

	public void setFriendLocation(int[] friendLocation) {
		this.friendLocation = friendLocation;
	}
	
	
	
	
	
	
	

	
	


}
