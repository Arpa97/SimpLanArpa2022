package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node{

    private Node exp;
    private Node then_statement;
    private Node else_statement;

    public IteNode(Node exp, Node then_statement, Node else_statement){
        this.exp = exp;
        this.then_statement = then_statement;
        this.else_statement = else_statement;
    }

    public IteNode(Node exp, Node then_statement){
        this.exp = exp;
        this.then_statement = then_statement;
    }

    @Override
    public String Analyze() {
        String res = "\n" + "ITE ";
        res += "\n"+ " Condition " + exp.Analyze();
        res += "\n" + " Then " + then_statement.Analyze();

        if(else_statement != null){
            res += "\n" + " Else " + else_statement.Analyze();
        }

        return res;
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
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(this.exp!=null){
            res.addAll(this.exp.checkSemantics(env));
        }
        if(this.then_statement!=null){
            res.addAll(this.then_statement.checkSemantics(env));
        }
        if(this.else_statement!=null){
            res.addAll(this.else_statement.checkSemantics(env));
        }
        return res;
    }
}