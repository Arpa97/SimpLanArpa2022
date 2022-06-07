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
        if(bool) return s + "Boolean: true\n";
        else return s + "Boolean: false\n";
    }
    
    @Override
    public String Analyze() {
        //return "\n" + "BoolExpNode: " + bool;
        if(bool) return "Boolean: true\n";
        else return "Boolean: false\n";
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
