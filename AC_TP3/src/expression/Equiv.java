package expression;

import java.util.HashSet;
import java.util.Set;

public class Equiv extends Expression {

    private Expression e1, e2;
    
    public Equiv(Expression e1, Expression e2) {
            this.e1 = e1;
            this.e2 = e2;
    }
        
    public Expression simplifier(){
        e1 = e1.simplifier();
        e2 = e2.simplifier();
        if (e1.estVrai())
            return e2;
        if (e2.estVrai())
            return e1;
        if (e1.estFaux())
            return (new Non(e2)).simplifier();
        if (e2.estFaux())
            return (new Non(e1)).simplifier();
        return this;
    }

    public boolean evalue() throws RuntimeException {
        if(!atomes().isEmpty()){
            throw new RuntimeException("L'expression ne peut pas être évaluée car elle contient (au moins) l'atome ");
        }else{
            return e1.evalue() == e2.evalue();
        }
    }

    public Set<String> atomes() {
        Set<String> atomespresents = new HashSet<String>();
        atomespresents.addAll(e1.atomes());
        atomespresents.addAll(e2.atomes());
        return atomespresents;
    }

    public Expression remplace(String s, boolean b) {
        return new Equiv(e1.remplace(s,b), e2.remplace(s,b));
    }

}