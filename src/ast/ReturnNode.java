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
    public Node typeCheck() {
        return exp.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() + "srv\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public String Analyze() {
        return "ReturnNode; " + this.exp.Analyze();
    }
}
