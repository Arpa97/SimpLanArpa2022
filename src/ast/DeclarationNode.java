package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node{

    private Node dec;

    public DeclarationNode(Node dec){
        this.dec = dec;
    }

    @Override
    public String Analyze() {
        return "\n"+dec.Analyze();
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
        if(dec==null){
            return new ArrayList<SemanticError>();
        }
        return this.dec.checkSemantics(env);
    }
}