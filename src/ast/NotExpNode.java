package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NotExpNode extends BaseExpNode{
    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String Analyze() {
        return "\n"+"NotExpNode "+this.exp.Analyze();
    }

    @Override
    public Node typeCheck() {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +           // r1 <- cgen(stable, expNode);                 s -> []
                "not\n";                        // not r1   if r1 == 0 r1 <- 1 else r1 <- 0;    s -> []
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return super.checkSemantics(env);
    }
}