package ast;

import util.Effect;
import util.Environment;
import util.SemanticError;
import util.VoidNode;

import java.util.ArrayList;

public class PrintNode implements Node{
    //'print' exp;

    private Node exp;

    public PrintNode(Node exp){
        this.exp = exp;
    }


    @Override
    public Node typeCheck() {
        exp.typeCheck();
        if(exp.getClass().getName().contains("DerExpNode")){
            DerExpNode exp1 = (DerExpNode) (exp);
            if(exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                System.out.println("Errore, variabile non inizializzata");
                System.exit(0);
            }
            exp1.getIdNode().getEntry().getEffect().setUsed();
        }
        return null; //ste metti null
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return "Print node: " + this.exp.Analyze() + "\n";
    }
    
    public Node getExp(){ return this.exp;}
}