package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node{

    private Node dec;

    public DeclarationNode(Node dec){
        this.dec = dec;
    }


    //forse bisogna fare distinzione se è una decfun o decvar
    @Override
    public Node typeCheck() {
        
        return dec.typeCheck();
    }

    //anche qui bisognerà distinguere
    @Override
    public String codeGeneration() {
        return dec.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if(dec==null){
            return new ArrayList<SemanticError>();
        }
        return this.dec.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return null;
    }
}