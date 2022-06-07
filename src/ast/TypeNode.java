package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeNode implements Node{

    private String type;

    public TypeNode(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }


    @Override
    public Node typeCheck() {
        return this;
    }

    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
}