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
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        
        return "lir1" + (bool?1:0)+"\n";    //li r1 1/0 dipende cosa si passa
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
}
