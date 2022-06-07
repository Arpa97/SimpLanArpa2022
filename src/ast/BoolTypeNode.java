package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BoolTypeNode implements Node{
    
    public BoolTypeNode() {
        
    }
    
    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public String Analyze() {
        return "BoolType\n";
    }
}
