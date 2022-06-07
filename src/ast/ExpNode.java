package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ExpNode implements Node {

    protected Node exp;

    public ExpNode(Node exp){
        this.exp = exp;
    }


    @Override
    public Node typeCheck() {
        return exp.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration(); //ste
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return "ExpNode: " + "(" + exp.Analyze() + ")";
    }
}