package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node{

    private Node exp;

    public ReturnNode(Node exp){
        this.exp = exp;
    }


    @Override
    public String Analyze() {
        String res = "\n" + "Return";
        if(this.exp != null)
            return res + exp.Analyze();
        else
            return res;
    }

    @Override
    public Node typeCheck() {
        return exp.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
}
