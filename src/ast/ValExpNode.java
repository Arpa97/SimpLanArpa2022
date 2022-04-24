package ast;

import ast.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ValExpNode implements Node {

    private int value;

    public ValExpNode(int value){
        this.value=value;
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