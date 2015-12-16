package jesaispas;

public class Arrete {
	int suivant;
	int precedent;
	Fontaine fontaine;
	
	public Arrete(int suivantt,int precedentt, Fontaine font){
		suivant = suivantt;
		precedent = precedentt;
		fontaine = font;
	}

}
