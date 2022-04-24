package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {

    private IdNode id;

    public DerExpNode(IdNode id){
        this.id = id;
    }

    @Override
    public String Analyze() {
        return "\n" + "DerExpNode" + this.id.Analyze();
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

        int j=env.nestingLevel;
        STentry tmp=null;
        while (j>=0 && tmp==null)
            tmp=(env.symTable.get(j--)).get(this.id.getId());
        if (tmp==null)
            res.add(new SemanticError("Variable "+this.id.getId()+" not declared"));
        return res;
    }
}