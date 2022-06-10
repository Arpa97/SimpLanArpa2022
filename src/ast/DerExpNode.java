package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {

    private IdNode id;
    private STentry entry;
    //private int nestinglevel;

    public DerExpNode(IdNode id){
        this.id = id;
    }

    public IdNode getIdNode(){ return this.id; }

    @Override
    public Node typeCheck() {
        return id.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return id.codeGeneration(); // r1 <- codgen(stable, derExp)
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return id.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return "DerExpNode:" + this.id.Analyze();
    }
    
   
}