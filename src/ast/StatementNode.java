package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class StatementNode implements Node {

    private Node statement;

    public StatementNode(Node statement) {
        this.statement = statement;
    }


    @Override
    public String Analyze() {
        return "\n"  + "Statement" + statement.Analyze();
    }

    @Override
    public Node typeCheck() {
        return statement.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if (statement != null) {
            res.addAll(this.statement.checkSemantics(env));
        }
        return res;
    }
}