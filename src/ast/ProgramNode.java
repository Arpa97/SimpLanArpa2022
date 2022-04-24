package ast;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode implements Node{

    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;

    public ProgramNode(ArrayList<Node> declarations, ArrayList<Node> statements) {
        this.declarations = declarations;
        this.statements = statements;
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
    public String Analyze() {
        String res = "";
        if (this.declarations != null) {
            for (Node dec : declarations) {
                res += dec.Analyze();
            }
        }
        if (this.statements != null) {
            for (Node dec : statements) {
                res += dec.Analyze();
            }
        }
        return "\n" + "Program" + res;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        env.enterScope();

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(this.declarations!=null && this.declarations.size()>0){
            env.offset = -2;
            for(Node n: this.declarations){
                res.addAll(n.checkSemantics(env));
            }
        }
        if(this.statements!=null && this.statements.size()>0){
            env.offset = -2;
            for(Node n: this.statements){
                res.addAll(n.checkSemantics(env));
            }
        }
        env.exitScope();

        return res;
    }

    public ArrayList<SemanticError> checkSemanticsFunction(Environment env) {
        HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(this.declarations!=null && this.declarations.size()>0){
            env.offset = -2;
            for(Node n: this.declarations){
                res.addAll(n.checkSemantics(env));
            }
        }
        if(this.statements!=null && this.statements.size()>0){
            env.offset = -2; // Why?
            for(Node n: this.statements){
                res.addAll(n.checkSemantics(env));
            }
        }
        return res;
    }
}
