package jesaispas;

import java.util.ArrayList;

public class Ant_solution {
	
	double NOMBRE_FOURMIS = 100;
	Graph graph;
	double best_cost = 100000000.;
	ArrayList<Fontaine> best_path;
	
	public Ant_solution(ArrayList<Fontaine> fontaines,double[][] couts){
		graph = new Graph(fontaines,couts);
	}
	
	public void exec(){
		for(int i = 0 ; i<NOMBRE_FOURMIS ; i++){
			Ant ant  = new Ant(graph.graph);
			while (ant.get_next_node(graph)) { //determination d'un chemin
			}
			ant.produce_pheromone(graph); // deposer les pheromone sur le chemin
			if (ant.current_cost < best_cost) {
				best_cost = ant.current_cost;
				best_path = ant.visited;

			}
			
		}
		
	}
	
	public void cout(){
		System.out.print("Ant_solution ");
		for(int i =0;i< best_path.size();i++){
			System.out.print(best_path.get(i).indice + " ");
		}
		System.out.println("");
	}
	
	public double getCost() {
		return best_cost;
	}
}

class Ant{
	ArrayList<Fontaine> visited=new ArrayList<Fontaine>();
	ArrayList<Fontaine> never_visited=new ArrayList<Fontaine>();
	int current_node = 0;
	double current_cost = 0;
	double capital_pheromone=10;
	
	public Ant(ArrayList<Fontaine> fontaines){
		for(int i =1; i < fontaines.size() ; i++){
			never_visited.add(fontaines.get(i));

		}
		//System.out.println("");
		visited.add(fontaines.get(0));
	}
	
	public Boolean get_next_node(Graph graph){
		
		double total_pheromone =0;
		for (int i=0 ; i< never_visited.size();i++){
			total_pheromone += graph.pheromone[current_node][never_visited.get(i).indice];
		}
		double rando = Math.random()*total_pheromone; // rando est un nombre entre 0 et la totalité des pheromones qui partent de cet arc
		
		double cost=graph.pheromone[current_node][never_visited.get(0).indice];
		int ind = 0;
		while(cost < rando){
			ind++;
			cost+=graph.pheromone[current_node][never_visited.get(ind).indice];
		}
		visited.add(0,never_visited.get(ind));
		never_visited.remove(ind);
		current_cost +=  graph.cout[current_node][visited.get(0).indice];
		//System.out.print(current_cost +  " ");
		current_node = visited.get(0).indice;
		if (never_visited.size() == 0){
			current_cost += graph.cout[current_node][0];
			visited.add(0,visited.get(visited.size()-1));
			return false;
		}
		return true;
	}
	
	public void produce_pheromone(Graph graph){
		//visited.add(visited.get(0));
		for(int i = 0; i < graph.graph.size()-1;i++){
			int ind1= visited.get(i).indice;
			int ind2 = visited.get(i+1).indice;
			graph.pheromone[ind1][ind2] += graph.cout[ind1][ind2]*current_cost/capital_pheromone;
		}
	}
	
	
}

class Graph{
	ArrayList<Fontaine> graph=new ArrayList<Fontaine>();
	double cout[][];
	double pheromone[][];

	
	
	public Graph(ArrayList<Fontaine> fontaines, double[][] tab_couts){
		cout = new double[200][200];
		pheromone = new double[200][200];
		for (int i = 0 ; i < fontaines.size();i++){ // copie la matrice des couts
			for (int j = 0 ; j < fontaines.size();j++){
				cout[i][j]= tab_couts[i][j];
				pheromone[i][j] = 1;
				//System.out.print(matrice_couts[i][j] +  " ");
			}
			graph.add(fontaines.get(i));
			//System.out.println("");
		}
	}
}
