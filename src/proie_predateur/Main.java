package proie_predateur;

import fr.emse.fayol.maqit.simulator.configuration.IniFile;
import fr.emse.fayol.maqit.simulator.configuration.SimProperties;
import fr.emse.fayol.maqit.simulator.environment.ColorCell;
import fr.emse.fayol.maqit.simulator.environment.GridManagement;
import java.io.FileWriter;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//IniFile file = new IniFile("initialisation.ini");
		IniFile file = new IniFile("fig3.ini");
		SimProperties sp = new SimProperties(file);
		
		sp.simulationParams();
		sp.displayParams();
		
		
		/*System.out.println(sp.nbobstacle);	
		System.out.println(sp.nbrobot);
		System.out.println(sp.display_x);
		System.out.println(sp.debug);*/
		/*System.out.println("hello-2");

		System.out.println("hello-1");
		System.out.println("hello0");*/
		//new GridManagement(1234,10,10,"test",1,1,10,10,0);
		

		// La couleur de background est statique et hardcodée en noir dans le JAR dans une méthode protected.
		// Pour autant, elle n'est pas définie d'entrée de jeu.
		// Du grand art!
		ColorCell.defaultcolor = new int[]{200,150,0};
		//ColorCell.defaultcolor = new int[]{180,135,0};
		

		//GridManagement env = new GridManagement(sp.seed, sp.rows, sp.columns, sp.debug);	
		//GridManagement env = (GridManagement) new GridManagement(sp.seed, sp.rows, sp.columns, sp.display_title, sp.display_x, sp.display_y, sp.display_width, sp.display_height, sp.debug);	
		
		
		//System.out.println("hello");		
		//Simul simulation = new Simul(sp, env);
		//GridManagement env = (GridManagement) new GridManagement(sp.seed, sp.rows, sp.columns, sp.display_title, sp.display_x, sp.display_y, sp.display_width, sp.display_height, sp.debug);	

		
		// STATS PARAMETERS
		int nb_sim = 50;
		int nb_it = 2000;
		//nb_sim = 80;
		nb_sim = 30;
		//nb_sim = 15;
		//nb_sim = 10;
		//nb_it = 10;
		nb_sim = 1;
		nb_it = 2000;
		
		// Write ALL data
		FileWriter fullWriter = new FileWriter("data_avg_s"+sp.stratProie+sp.stratPredateur+"_fp"+sp.fieldProie+"_fa"+sp.field+"_np"+sp.nbproie+"_na"+sp.nbrobot+".txt");
		fullWriter.write("Simulation, Total_Death\r\n");
		

		String dataHeader = "Iteration";
		String[] dataRows = new String[nb_it];
		for(int nSim = 0; nSim < nb_sim; nSim++) {
			dataHeader += ", Death_"+nSim;
		}
		dataHeader += "\r\n";

		FileWriter writer = new FileWriter("data_full_s"+sp.stratProie+sp.stratPredateur+"_fp"+sp.fieldProie+"_fa"+sp.field+"_np"+sp.nbproie+"_na"+sp.nbrobot+".txt");
		writer.write(dataHeader);
		

		for(int nIt = 0; nIt < nb_it; nIt++) {
			dataRows[nIt] = Integer.toString(nIt);
		}
		
		for(int nSim=0; nSim < nb_sim; nSim++)
		{		
			System.out.println("nSim : "+nSim);	
			
			GridManagement env = new GridManagement(sp.seed, sp.rows, sp.columns, sp.display_title, sp.display_x, sp.display_y, sp.display_width, sp.display_height, sp.debug);	
			Simul simulation = new Simul(sp, env);
			
			
			int counter = 1;
			while(true && counter < nb_it+1)
			{
				simulation.schedule();
				Thread.sleep(sp.waittime);
				System.out.println("Sim :" + nSim + "  -  Iteration : " + counter);	
				//System.out.println("Death count : " + simulation.getProieEaten());	
				dataRows[counter-1] += ", " + simulation.getProieEaten();
				//writer.write(counter + ", " + simulation.getProieEaten() + "\r\n");
				counter++;
			}
			
			fullWriter.write(nSim + ", " + simulation.getProieEaten() + "\r\n");
			
		}

		for(int nIt = 0; nIt < nb_it; nIt++) {
			dataRows[nIt] += "\r\n";
			writer.write(dataRows[nIt]);
		}
		
		writer.close();
		fullWriter.close();
		
	}

}