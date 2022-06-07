package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallExpNode implements Node {

    private Node call;

    public CallExpNode(Node call){
        this.call = call;
    }

    @Override
    public Node typeCheck() {
        return call.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return call.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return call.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return "CallExpNode: " + this.call.Analyze() + "\n";
    }
}