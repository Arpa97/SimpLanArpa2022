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
        return null;
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
