package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node{
    //'print' exp;

    private Node exp;

    public PrintNode(Node exp){
        this.exp = exp;
    }


    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }
}