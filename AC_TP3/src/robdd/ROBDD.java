package robdd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Représente un ROBDD sous forme de liste de Noeud_ROBDD
public class ROBDD {

	//Constantes pour les numéros des "feuilles" VRAI et FAUX
	public static final int idFalse = 0;
	public static final int idTrue = 1;

	//liste représentant le ROBDD
	private List<Noeud_ROBDD> R;
	
	//construit un ROBDD vide
	public ROBDD(){
		R = new LinkedList<Noeud_ROBDD>();
	}
	//ajoute le Noeud_ROBDD n au ROBDD courant
	public void ajouter(Noeud_ROBDD n) {
		R.add(n);
	}

		
	//renvoie le nombre de noeuds du ROBDD
	public int nb_noeuds() {
		return R.size()+2; // longueur de la liste R + les 2 noeuds correspondants à VRAI et FAUX
	}

	@Override
	public String toString() {
		return R.toString();
	}
	
	// renvoie l'index, dans la liste R,  du noeud BDD associé à la variable nom et dont les fils droit et gauche sont d'indices respectifs fd et fg.
	// Si ce noeud n'existe pas dans le diagramme, la fonction renvoie -1.
    public int obtenirROBDDIndex(String nom, int fg, int fd) {
        for (Noeud_ROBDD noeud : R) {
            if (noeud.getNom().equals(nom) && noeud.getIdFilsGauche() == fg && noeud.getIdFilsDroit() == fd) {
                return noeud.getId();
            }
        }
        return -1;
    }
    
    public String trouve_sat() {
        int indice = 1;
        String result = "";
        for (Noeud_ROBDD noeud : R) {
            if (noeud.getIdFilsDroit() == indice){
               result += noeud.getNom()+": true, ";
               indice = noeud.getId();
            }else if(noeud.getIdFilsGauche() == indice) {
                result += noeud.getNom()+": false, ";
                indice = noeud.getId();
            }
        }
        if(result.equals("")) {
            return "l’expression associée au ROBDD courant n’est pas satisfaisable le cas échéant";
        }
        return result;
    }
    
    public void reines_afficher_sat(int n) {
        int indice = 1;
        Map<String, Integer> plateau = new HashMap<>();
        for (Noeud_ROBDD noeud : R) {
             if (noeud.getIdFilsDroit() == indice){
               plateau.put(noeud.getNom(), 1);
               indice = noeud.getId();
            }else if(noeud.getIdFilsGauche() == indice) {
                plateau.put(noeud.getNom(), 0);
                indice = noeud.getId();
            }
        }
        String barre = " ";
        for(int i=0;i<(3*n+(n-1));i++) {
            barre +="_";
        }
        System.out.println(barre);
        for(int i= 0;i<n;i++) {
            String ligne ="|";
            for(int j = 0; j< n; j++) {
                ligne += " ";
                if(plateau.get("x"+i+j) == 0) {
                    ligne += " ";
                }else {
                    ligne += "x";
                }
                ligne += " |";
            }
            System.out.println(ligne);
            System.out.println(barre);
        }
    }
}
