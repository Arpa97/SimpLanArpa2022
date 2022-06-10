package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NegExpNode extends BaseExpNode{
    public NegExpNode(Node exp) {
        super(exp);
    }
    @Override
    public String Analyze() {
        return "negExpNode: -"+this.exp.Analyze() + "\n";
    }

    @Override
    public Node typeCheck() {
        Node type = exp.typeCheck();
        if(exp.getClass().getName().contains("DerExpNode")){
            DerExpNode exp1 = (DerExpNode) (exp);
            exp1.getIdNode().getEntry().getEffect().setUsed();
        }
        return type;
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +   //r1 <- cgen(stable, negExp)        S->[] 
                "lir2 -1\n" +           //li r2 -1                          S->[]
                "mult\n";               //mul r1 r1 r2      (r1* -1)        S->[]
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return super.checkSemantics(env);
    }
}