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
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return "lir1 "+value+"\n"; //li r1 val   s->[]
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
}