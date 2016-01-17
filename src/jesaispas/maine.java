package jesaispas;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;;



public class maine {
	static boolean coordinate = true; // calcul les distance a partir des coordonées GPS si faux, et les vrai longeurs et affiche les instructions si vrai
	static String [][] instructions = new String[109][109];
	public static void main(String[] args) throws IOException {
		double [][] cout = new double [109][109];
		ArrayList<Fontaine> fontaines = new ArrayList<Fontaine>();
		
		File file = new File("fontaines.csv");
		System.out.println(file.exists());
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		br.readLine();
		int ind = 0;
		for (String line = br.readLine();line !=null;line=br.readLine()){
			Fontaine font = new Fontaine(Double.parseDouble(line.split(",")[1]),Double.parseDouble(line.split(",")[2]),line.split(",")[0],ind);
			ind++;
			fontaines.add(font);
		}
		if (coordinate){
			File files = new File("vrai_fontaine.csv");
			System.out.println(files.exists());
			FileReader frs = new FileReader(files);
			BufferedReader brs = new BufferedReader(frs);

			brs.readLine();
			for (String line = brs.readLine();line !=null;line=brs.readLine()){
				cout[Integer.parseInt(line.split(",")[0])-1][Integer.parseInt(line.split(",")[1])-1]= Integer.parseInt(line.split(",")[2]);
				instructions[Integer.parseInt(line.split(",")[0])-1][Integer.parseInt(line.split(",")[1])-1] = line.split(",")[3];
				
			}	
		}
		else{
			for (int i = 0;i< 109;i++){
				for (int j = 0 ; j< 109;j++){
					cout[i][j] = distance(fontaines.get(i).lat,fontaines.get(i).lon,fontaines.get(j).lat,fontaines.get(j).lon);
				}
			}
		}

		
		ArrayList<Fontaine> nombre_reduit = new ArrayList<Fontaine>();
		for (int i = 0 ; i < 109; i ++){
			nombre_reduit.add(fontaines.get(i));
		}

		
		//solution_exact(nombre_reduit, cout);
		//solution_2_opt(nombre_reduit,cout);
		solution_ant(nombre_reduit,cout);
		

		

	}
	public static double solution_ant(ArrayList<Fontaine> nombre_reduit,double [][] matrice_couts_original){
		Ant_solution temp = new Ant_solution(nombre_reduit,matrice_couts_original);
		temp.exec();
		temp.cout();
		System.out.println(temp.getCost());
		if (coordinate){
			rendu_carte(temp.best_path);
			/*for(int i = 0 ; i < temp.best_path.size()-1;i++){
				System.out.println(instructions[temp.best_path.get(i).indice][temp.best_path.get(i+1).indice] + "\n");
			}
			System.out.println(instructions[temp.best_path.get(temp.best_path.size()-1).indice][temp.best_path.get(0).indice] + "\n");
		*/}
		return temp.getCost();
		
	}
	public static double solution_2_opt(ArrayList<Fontaine> nombre_reduit,double [][] matrice_couts_original){
		Two_opt two_opt  = new Two_opt(nombre_reduit,matrice_couts_original);
		two_opt.exec();
		if (coordinate){
			rendu_carte(two_opt.chemin.chemin);
			for(int i = 0 ; i < two_opt.chemin.size()-1;i++){
				System.out.println(instructions[two_opt.chemin.get(i).indice][two_opt.chemin.get(i+1).indice] + "\n");
			}
			System.out.println(instructions[two_opt.chemin.get(two_opt.chemin.size()-1).indice][two_opt.chemin.get(0).indice] + "\n");
		}
		return two_opt.cout();
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
			System.out.print(chemin_mini.get(i).indice + " " + matrice_couts[chemin_mini.get(i).indice][chemin_mini.get((i+1) % (chemin_mini.size()-1)).indice] + " ");
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
	
	public static void rendu_carte(ArrayList<Fontaine> fonts){
		File f  = new File("fontaines.js");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write("		function initialiser() { \n"
				+ "var latlng = new google.maps.LatLng(48.856614, 2.3522219000000177);\n"
				+ "var options = {\n"
				+"	center: latlng,\n"
				+"	zoom: 13,\n"
				+"	mapTypeId: google.maps.MapTypeId.ROADMAP\n"
				+"};\n"
				+	"var carte = new google.maps.Map(document.getElementById(\"carte\"), options);\n"
				+ "var parcoursBus = [");
			for (int i = 0 ; i < fonts.size();i++){
				fw.write("new google.maps.LatLng(" + fonts.get(i).lat + "," + fonts.get(i).lon + "),\n");
			}
			fw.write("new google.maps.LatLng(" + fonts.get(0).lat + "," + fonts.get(0).lon + ")\n];");

			fw.write("var traceParcoursBus = new google.maps.Polyline({\n"
					+ "path: parcoursBus,//chemin du tracé\n"
					+ "strokeColor: \"#FF0000\",//couleur du tracé\n"
					+ "strokeOpacity: 1.0,//opacité du tracé\n"
					+ "strokeWeight: 2//grosseur du tracé\n"
					+ "});"
					+ "traceParcoursBus.setMap(carte);");
			
			fw.write("}");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
