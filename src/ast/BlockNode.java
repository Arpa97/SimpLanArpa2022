package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockNode implements Node {

    /*
    Nella sintassi di SLP, le dichiarazioni delle variabili / funzioni devono venire fatte prima
    degli statement.
     */
    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;

    public BlockNode(ArrayList<Node> declarations, ArrayList<Node> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }

    @Override
    public Node typeCheck() {
        
        //come nel program node nel nostro caso, ma con decvar
        
        return null;
    }
    
    //funzione per verificare il tipo di ritorno del blocco?

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        env.nestingLevel++;
        HashMap<String, STentry> st = new HashMap<String, STentry>();
        env.symTable.add(st);

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(this.declarations!=null && this.declarations.size()>0){
            env.offset = -2; // Why?
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
        env.symTable.remove(env.nestingLevel--);

        return res;
    }

    public ArrayList<SemanticError> checkSemanticsFunction(Environment env) {
        HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(this.declarations!=null && this.declarations.size()>0){
            env.offset = -2; // Why?
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