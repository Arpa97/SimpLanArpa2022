package ast;

import ast.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BoolExpNode implements Node {

    private boolean bool;

    public BoolExpNode(boolean bool){
        this.bool = bool;
    }
    
    public String toPrint(String s){
        if(bool) return s + "Boolean: true";
        else return s + "Boolean: false";
    }
    
    @Override
    public String Analyze() {
        return "\n" + "Bool " + bool;
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
