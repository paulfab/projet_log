package jesaispas;

import java.util.ArrayList;

public class Two_opt {
	double [][] matrice_couts ;
	Chemin chemin;
	
	public Two_opt(ArrayList<Fontaine> nombre_reduit,double [][] matrice_couts_original){
		matrice_couts = new double[200][200];
		for (int i = 0 ; i < nombre_reduit.size();i++){ // copie la matrice des couts
			for (int j = 0 ; j < nombre_reduit.size();j++){
				matrice_couts[i][j]= matrice_couts_original[i][j];
				//System.out.print(matrice_couts[i][j] +  " ");
		}

			//System.out.println("");
		}
		chemin = new Chemin(nombre_reduit);
	}
	
	public void exec()
	{
		double cout_best = 1000000.;
		double cout_best_cent_ago = 100000000;
		boolean amelioration = true;
		int iteration=0;
		while (amelioration == true){
			amelioration = false;
			//chemin.print();
			for (int i = 0 ; i < chemin.size()-1;i++){
				for (int j = 0 ; j < chemin.size()-1;j++){
					//System.out.println(i +  " " + j + " ");
					if (j != i+1 && j != i-1 && j != i){
						if (matrice_couts[chemin.indice(i)][chemin.indice(i+1)] + matrice_couts[chemin.indice(j)][chemin.indice(j+1)] > 
							matrice_couts[chemin.indice(i)][chemin.indice(j)] + matrice_couts[chemin.indice(i+1)][chemin.indice(j+1)] ){
								
							chemin.transform(i,j);
							//chemin.print();
							//System.out.println("i " + i + " j " + j + " ");
							amelioration = true;

						}
					}
				}
			}
			iteration++;
			cout_best = Math.min(cout_best,  cout());
			if (iteration % 100 == 0 ){
				if ((cout_best_cent_ago - cout_best) < 1){
					amelioration = false;
				}
				cout_best_cent_ago = cout_best;
			}
		}
	}
	
	public double cout(){
		double cout =0;
		for (int i =0;i < chemin.size()-1;i++){
			//System.out.print( chemin.indice(i)  + " " );//+matrice_couts[chemin.indice(i)][chemin.indice(i+1) ] +  " " );
			cout += matrice_couts[chemin.indice(i)][chemin.indice(i+1) ];
		}
		//System.out.print( chemin.indice(chemin.size()-1)  + " ");// +matrice_couts[chemin.indice(chemin.size()-1)][chemin.indice(0) ] +  " " );
		cout += matrice_couts[chemin.indice(chemin.size()-1)][chemin.indice(0) ];
		//System.out.println(matrice_couts[1][4]);
		System.out.println("Solution 2 opt : " + cout);
		return cout;
	}
}

class Chemin{
	ArrayList<Fontaine> chemin = new ArrayList<Fontaine>();
	
	public Chemin(ArrayList<Fontaine> nombre_reduit){
		for (int i = 0 ; i < nombre_reduit.size(); i ++){
			chemin.add(nombre_reduit.get(i));
		}
	}
	
	public int size(){
		return chemin.size();
	}
	
	public void permute(int i, int j){
		Fontaine temp = chemin.get(i);
		chemin.set(i,chemin.get(j));
		chemin.set(j, temp);
		
	}
	
	public Fontaine get(int i){
		return chemin.get(i);
	}
	
	public int indice(int i){
		return get(i).indice;
	}
	
	public void transform(int i,int j){
		if (j>i){
			for (int k=i+1;k<j-k+i+1;k++){
				permute(k,j-k+i+1);
			}
		}
		else {
			transform(j,i);
		}
	}
	
	public void print(){

		for (int i = 0 ; i < chemin.size(); i ++){
			System.out.print(indice(i) + " ");
			System.out.println("");
		}
	}
	
}

