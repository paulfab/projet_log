package jesaispas;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;;



public class maine {

	public static void main(String[] args) throws IOException {
		double [][] cout = new double [109][109];
		System.out.println(distance (40,50,41,52));
		File file = new File("fontaines.csv");
		System.out.println(file.exists());
		ArrayList<Fontaine> fontaines = new ArrayList<Fontaine>();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		br.readLine();
		int ind = 0;
		for (String line = br.readLine();line !=null;line=br.readLine()){
			Fontaine font = new Fontaine(Double.parseDouble(line.split(",")[1]),Double.parseDouble(line.split(",")[2]),line.split(",")[0],ind);
			ind++;
			fontaines.add(font);
		}
		for (int i = 0;i< 109;i++){
			for (int j = 0 ; j< 109;j++){
				cout[i][j] = distance(fontaines.get(i).lat,fontaines.get(i).lon,fontaines.get(j).lat,fontaines.get(j).lon);
				//System.out.print(cout[i][j] + " ");
			}
			//System.out.println("");
		}
		
		ArrayList<Fontaine> nombre_reduit = new ArrayList<Fontaine>();
		for (int i = 0 ; i < 5; i ++){
			nombre_reduit.add(fontaines.get(i));
		}
		
		solution_exact(nombre_reduit, cout);
		solution_2_opt(nombre_reduit,cout);

		

	}
	public static double solution_2_opt(ArrayList<Fontaine> nombre_reduit,double [][] matrice_couts_original){
		/*ArrayList<Arrete> cycle = new ArrayList<Arrete>();
		for (int i = 0 ; i < nombre_reduit.size(); i ++){
			if (i ==0){
				cycle.add(new Arrete(1,nombre_reduit.size()-1,nombre_reduit.get(i)));
			}
			else if(i == nombre_reduit.size()-1) {
				cycle.add(new Arrete(0,i-1,nombre_reduit.get(i)));
			}
			else{
				cycle.add(new Arrete(i+1,i-1,nombre_reduit.get(i)));
			}
		}*/
		ArrayList<Fontaine> cyclee = new ArrayList<Fontaine>();
		for (int i = 0 ; i < nombre_reduit.size(); i ++){
			cyclee.add(nombre_reduit.get(i));
		}
		
		double matrice_couts [][] = new double[200][200];
		for (int i = 0 ; i < nombre_reduit.size();i++){
			for (int j = 0 ; j < nombre_reduit.size();j++){	
				matrice_couts[i][j]= matrice_couts_original[cyclee.get(i).indice][cyclee.get(j).indice];
				//System.out.print(matrice_couts[i][j] +  " ");
			}
			//System.out.println("");
		}
		
		//cycle.add(cycle.get);
	
		boolean amelioration = true;
		while (amelioration == true){
			amelioration = false;
			for (int i = 0 ; i < nombre_reduit.size();i++){
				for (int j = 0 ; j < nombre_reduit.size();j++){
					//System.out.println(i +  " " + j + " "+cycle.get(i).suivant + " " + cycle.get(j).suivant);
					if (j != i+1 && j != i-& && j != i){
						if (matrice_couts[cycle.get(i).index][cycle.get(i+1).index] + matrice_couts[cyclee.get(j).index][cyclee.get(j+1).suivant] >matrice_couts[cyclee.get(i).index][cyclee.get(j).index] + matrice_couts[cycle.get(i).suivant][j] ){
							cycle.get(cycle.get(i).suivant).precedent = j;
							cycle.get(cycle.get(j).suivant).precedent = i;
							int stock = cycle.get(j).suivant;
							cycle.get(j).suivant = cycle.get(i).suivant;
							cycle.get(i).suivant = stock;
							System.out.println("us" + i +  " " + j + " "+cycle.get(i).suivant + " " + cycle.get(j).suivant);

							amelioration = true;

						}
					}
				}
			}
		}
		
		double cout =0;
		int ind = 0;
		for (int i =0;i < nombre_reduit.size();i++){
			System.out.println(ind +  " " + cycle.get(ind).suivant );
			cout += matrice_couts[ind][cycle.get(ind).suivant];
			ind = cycle.get(ind).suivant;
		}
		System.out.println("Solution Approche : " + cout);
		return cout;
	}
	
	
	public static double solution_exact(ArrayList<Fontaine> nombre_reduit,double [][] matrice_couts){
		ArrayList<ArrayList<Fontaine>> list_chemin = new  ArrayList<ArrayList<Fontaine>>();
		for(int i1=0; i1<5;i1++){
			for(int i2=0; i2<5;i2++){
				for(int i3=0; i3<5;i3++){
					for(int i4=0; i4<5;i4++){
						for(int i5=0; i5<5;i5++){
							if (i1!=i2 && i1!=i3 && i1 != i2 && i1!= i5 && i1 !=i4 && i2!=i3 && i2!=i4 && i2!=i5 && i3!= i4 && i3!=i5 && i4!=i5 ){
							ArrayList<Fontaine> temp_arr = new ArrayList<Fontaine>();
							temp_arr.add(nombre_reduit.get(i1));
							temp_arr.add(nombre_reduit.get(i2));
							temp_arr.add(nombre_reduit.get(i3));
							temp_arr.add(nombre_reduit.get(i4));
							temp_arr.add(nombre_reduit.get(i5));
							temp_arr.add(nombre_reduit.get(i1));
								list_chemin .add(temp_arr);
							}
						}
					}
				}
			}
		}
		
		double cout_chemins[] = new double[list_chemin.size()];
		ArrayList<Fontaine> chemin_mini= new ArrayList<Fontaine>();
		double min_cout = 0;
		for(int i = 0 ; i < list_chemin.size();i++){
			cout_chemins[i] = cout_chemin(list_chemin.get(i),matrice_couts);
			if (i == 0) {
				min_cout = cout_chemins[i];
			}
			if (min_cout > cout_chemins[i]){
				chemin_mini = list_chemin.get(i);
			}
			min_cout = Math.min( min_cout, cout_chemins[i]);

		}
		for (int i = 0 ; i< chemin_mini.size();i++){
			System.out.print(chemin_mini.get(i).indice + " ");
		}
		System.out.println("Solution exact : " +min_cout);
		return min_cout;
		
	}
	
	public static double distance(double x, double y, double x2, double y2){ //distance en mettre en fonction latitude et longitude
		double R=6371.;
		return R * Math.acos(Math.sin(x*Math.PI/180)*Math.sin(x2*Math.PI/180)+Math.cos(x*Math.PI/180)*Math.cos(x2*Math.PI/180)*Math.cos((y-y2)*Math.PI/180));
	}
	
	public static double cout_chemin(ArrayList<Fontaine> font,double[][] matrice){
		double cout= 0;
		for (int i = 1 ; i < font.size();i++){
			cout += matrice[font.get(i).indice][font.get(i-1).indice];
		}
		return cout;
	}

}
