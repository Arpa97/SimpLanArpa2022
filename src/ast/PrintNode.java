package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node{
    //'print' exp;

    private Node exp;

    public PrintNode(Node exp){
        this.exp = exp;
    }


    @Override
    public Node typeCheck() {
        return exp.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public String Analyze() {
        return "Print node: " + this.exp.Analyze() + "\n";
    }
}