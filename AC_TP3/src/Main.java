import java.util.LinkedList;
import java.util.List;
import robdd.ROBDD;
import expression.*;

public class Main {

	public static void main(String[] args) {
//        exercice2();
//        exercice5();
//        exercice6();
        rennes(2);
	}
	
	public static void exercice2() {
		//(x1 , y1) ^ (x2 , y2):
        Expression ex2 = new Et(new Equiv(new Atome("x1"), new Atome("y1")), new Equiv(new Atome("x2"), new Atome("y2")));
        System.out.println(ex2.atomes()); // affiche la liste des atomes (=variables booléennes) présents dans exp
        ex2 = ex2.remplace("x1",false); // exp vaut maintenant (true ^ y)
        ex2 = ex2.remplace("y1",true); // exp vaut maintenant (true ^ y)
        ex2 = ex2.remplace("x2",false); // exp vaut maintenant (true ^ y)
        ex2 = ex2.remplace("y2",true); // exp vaut maintenant (true ^ y)
        System.out.println(ex2.evalue()); // affiche la liste des atomes (=variables booléennes) présents dans exp
	}
	
	public static void  exercice5() {
        	Expression ex2 = new Et(new Equiv(new Atome("x1"), new Atome("y1")), new Equiv(new Atome("x2"), new Atome("y2")));
			Expression ex10 = new Ou(new Equiv(new Atome("x"), new Atome("y")), new Et(new Atome("z"), new Atome("y")));
	        List<String> ordre_atomes = new LinkedList<String>();
	        ordre_atomes.add("x");
	        ordre_atomes.add("y");
	        ordre_atomes.add("z");
	        System.out.println("\n Arbre de ex2 du TP : \n" + ex10.robdd(ordre_atomes));
	        System.out.println("\n Arbre de ex10 du TD : \n" + ex10.robdd(ordre_atomes));
	}
	
	public static void exercice6() {
        Expression ex6 = new Et(new Equiv(new Atome("x1"), new Atome("y1")), new Equiv(new Atome("x2"), new Atome("y2")));
        List<String> atomes_ordonnes = new LinkedList<String>();
        atomes_ordonnes.add("x1");
        atomes_ordonnes.add("y1");
        atomes_ordonnes.add("x2");
        atomes_ordonnes.add("y2");
        ROBDD ex6_robdd = ex6.robdd(atomes_ordonnes);
        System.out.println(ex6_robdd);
        System.out.println(ex6_robdd.trouve_sat());
	}
	
	public static void rennes(int n) {
		String[][] board = new String[n][n];
		List<String> ordre_atomes = new LinkedList<String>();
		for(int i = 0; i<n;i++) {
			for(int j = 0; j<n;j++) {
				ordre_atomes.add("x"+i+j);
				board[i][j] = "x"+i+j;
			}
		}
		
		Expression partie1 = moinsLigne(n);
		Expression partie2 = auplusligne(n);
		Expression partie3 = aupluscolonne(n);
		Expression partie4 = auplusdiagonale(n);
		Expression finale = new Et(new Et( new Et(partie1, partie2), partie3), partie4);
	
		System.out.println(finale.atomes());
		ROBDD robdd = finale.robdd(ordre_atomes);
		System.out.println(robdd);
		System.out.println(robdd.trouve_sat());
		robdd.reines_afficher_sat(n);
	}
		
    public static Expression moinsLigne(int n) {
        Expression exp_reines = new Constante(true);
        for (int i = 0; i < n; i++) {
            Expression lignes = new Constante(false);
            for (int j = 0; j < n; j++) {
                lignes = new Ou(lignes, new Atome(("x" + i) + j));
            }
            exp_reines = new Et(exp_reines, lignes);
        }
        return exp_reines;
    }
    
    public static Expression auplusligne(int n) {
        Expression exp_ligne = new Constante(true);
        for(int i=0; i<n;i++) {
        	Expression l1 = new Constante(true);
            for(int j=0; j<n; j++) {
                Expression e1 = new Constante(true);
                for(int k = 0; k< n; k++) {
                    if(k!=j) {
                        e1 = new Et(e1, new Non(new Atome("x"+i+k)));
                    }
                }
                l1 = new Et(l1, new Implique(new Atome("x"+i+j), e1));
            }
            exp_ligne = new Et(exp_ligne, l1);
        }
        return exp_ligne;
    }
    
    public static Expression aupluscolonne(int n) {
        Expression exp_colonne = new Constante(true);
        for(int i=0; i<n;i++) {
        	Expression c1 = new Constante(true);
            for(int j=0; j<n; j++) {
                Expression e1 = new Constante(true);
                for(int k = 0; k< n; k++) {
                    if(k!=j) {
                        e1 = new Et(e1, new Non(new Atome(("x"+k)+i)));
                    }
                }
               c1 =  new Et(c1, new Implique(new Atome("x"+i+j), e1));
            }
            exp_colonne = new Et(exp_colonne, c1);
        }
        return exp_colonne;
    }
    
    public static Expression auplusdiagonale(int n) {
    	Expression exp_diagonales = new Constante(true);
    	Expression top_diag = new Constante(true);
        for(int i=0; i<n-1;i++) {
        	Expression d1 = new Constante(true);
            for(int j = 0;j<n-i;j++) {
            	Expression inter = new Constante(true);
            	for(int k = 0; k<n-i;k++) {
            		if(k != j) {
            			int indice = k+i;
            			inter = new Et(inter, new Non (new Atome("x"+k+indice)));
            		}
            	}
            	int index = i+j;
            	d1 = new Et(d1, new Implique(new Atome("x"+j+index), new Non(inter)));
            }
            top_diag = new Et(top_diag, d1);
        }
        
        Expression middle = new Constante(true);
        for(int i=1; i<n-1;i++) {
            Expression middle_descending = new Constante(true);
            Expression middle_ascending = new Constante(true);
        	Expression d1 = new Constante(true);
            for(int j = 0;j<n-i;j++) {
            	Expression inter = new Constante(true);
            	for(int k = 0; k<n-i;k++) {
            		if(k != j) {
            			int indice = i+k;
            			inter = new Et(inter, new Non (new Atome("x"+indice+k)));
            		}
            	}
            	int index = i+j;
            	d1 = new Et(d1, new Implique(new Atome("x"+index+j), new Non(inter)));
            }
            middle_descending = new Et(middle_descending, d1);
            
            Expression d2 = new Constante(true);
            for(int j = 0;j<1+i;j++) {
            	Expression inter = new Constante(true);
            	for(int k = 0; k<1+i;k++) {
            		if(k != j) {
            			int indice = i-k;
            			inter = new Et(inter, new Non (new Atome("x"+indice+k)));
            		}
            	}
            	int index = i-j;
            	d2 = new Et(d2, new Implique(new Atome("x"+index+j), new Non(inter)));
            }
            System.out.println(d2.atomes());
            middle_ascending = new Et(middle_ascending, d2);
            middle = new Et(middle,new Et(middle_descending, middle_ascending));
        }
        Expression low_diag = new Constante(true);
        for(int i=0; i<n-1;i++) {
        	Expression d1 = new Constante(true);
            for(int j = 0;j<n-i;j++) {
            	Expression inter = new Constante(true);
            	for(int k = 0; k<n-i;k++) {
            		if(k != j) {
            			int indicel = n-1-k;
            			int indicec = k+i;
            			inter = new Et(inter, new Non (new Atome("x"+indicel+indicec)));
            		}
            	}
    			int indexl = n-1-j;
    			int indexc = j+i;
            	d1 = new Et(d1, new Implique(new Atome("x"+indexl+indexc), new Non(inter)));
            }
            low_diag = new Et(low_diag, d1);
        }
        exp_diagonales = new Et(exp_diagonales, new Et(new Et(top_diag, middle), low_diag));
        return exp_diagonales;
    }


}
