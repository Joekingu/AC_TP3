package expression;

import java.util.HashSet;
import java.util.Set;

public class Atome extends Expression {

	private String name;

	public Atome(String s) {
		this.name = new String(s);
	}

	public boolean evalue() throws RuntimeException {
		throw new RuntimeException("L'expression ne peut pas être évaluée car elle contient (au moins) l'atome "+name);
	}

	public Set<String> atomes() {
		Set<String> s = new HashSet<String>();
		s.add(name);
		return s;
	}

	public Expression remplace(String s, boolean b) {
		if(s.equals(name)) {
			return new Constante(b);
		}else { 
			return this;
		}
	}

	public Expression simplifier(){
		return this;
	}

}
