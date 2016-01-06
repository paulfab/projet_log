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
			while(ant.get_next_node(graph) == 0){
			}
			ant.produce_pheromone(graph);
			if (ant.current_cost < best_cost){
				best_cost = ant.current_cost;
				best_path = ant.visited;
			}
			
		}
		
	}

}

class Ant{
	ArrayList<Fontaine> visited;
	ArrayList<Fontaine> never_visited;
	double size_path=0;
	int current_node = 0;
	double current_cost = 0;
	double capital_pheromone=10;
	
	public Ant(ArrayList<Fontaine> fontaines){
		for(int i =0; i < fontaines.size() -1; i++){
			never_visited.add(fontaines.get(i));
		}
	}
	
	public int get_next_node(Graph graph){
		
		double total_pheromone =0;
		for (int i=0 ; i< never_visited.size();i++){
			total_pheromone += graph.pheromone[current_node][never_visited.get(i).indice];
		}
		double rando = Math.random()*total_pheromone;
		
		double cost=0;
		int ind = 0;
		while(cost < rando){
			ind++;
			cost+=graph.pheromone[current_node][never_visited.get(ind).indice];
		}
		visited.add(0,never_visited.get(ind));
		never_visited.remove(ind);
		size_path +=  graph.cout[current_node][ind];
		current_node = ind;
		if (never_visited.size() == 0){
			return -1;
		}
		return 0;
	}
	
	public void produce_pheromone(Graph graph){
		visited.add(visited.get(0));
		for(int i = 0; i < graph.graph.size();i++){
			int ind1= visited.get(i).indice;
			int ind2 = visited.get(i+1).indice;
			graph.pheromone[ind1][ind2] += graph.cout[ind1][ind2]*current_cost/capital_pheromone;
		}
	}
	
	
}

class Graph{
	ArrayList<Fontaine> graph;
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
