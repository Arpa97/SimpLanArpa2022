package ast;

import util.Effect;
import util.Environment;
import util.SemanticError;
import util.VoidNode;

import java.util.ArrayList;

public class PrintNode implements Node{
    //'print' exp;

    private Node exp;
    //private Effect effect;

    public PrintNode(Node exp){
        this.exp = exp;
    }


    @Override
    public Node typeCheck() {
        exp.typeCheck();
        //effect.setUsed(true);
        return new VoidNode(); //ste
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
}