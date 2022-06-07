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

    @Override
    public String Analyze() {
        //da rivedere
        String out ="";
        for (Node decVar:declarations)
            out += decVar.Analyze() ;

        for (Node st:statements)
            out += st.Analyze();


        return "BlockNode:" + out + "\n" ;
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
    
    public Boolean checkRetValue(){
        boolean hasRetValue = false;
        boolean hasElse = false;
        
        for (int i = 0; i < statements.size(); i++){
            StatementNode stm = (StatementNode) statements.get(i);
            if(stm.getStatement() instanceof IteNode){
                hasRetValue = ((IteNode) stm.getStatement()).isCheckRetValueIte();
                //caso in cui esista anche l'else statement
                if( ((IteNode) stm.getStatement()).getElse_statement() != null ){
                    hasElse = true;
                }
            }
            //se ha UN tipo di ritorno
            if(stm.getCheckRet()){
                if(hasRetValue && hasElse) {
                    System.out.println("Block Error: Multiple return conflicts with iteration statement");
                    System.exit(0);
                }
                //caso in cui si abbiano più tipi di ritorno nello stesso blocco
                if(i !=statements.size() - 1){
                    System.out.println("Block Error: Multiple return");
                    System.exit(0);
                } else return true;
            } 
        }
        
        return hasRetValue;
        
    }
}