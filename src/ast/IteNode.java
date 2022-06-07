package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

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
    public Node typeCheck() {
        //ste
        //la guardia dell'if la metto booleana
        if(!(SimpLanPlusLib.isSubtype(exp.typeCheck(), new BoolTypeNode()))) {
            System.out.println("Iteration Error: bad condition type ");
            System.exit(0);
        }
            //faccio controllo su entrambi gli statement se ce ne sono due (then - else )
        if(else_statement != null) {
            Node then_st = this.then_statement.typeCheck();
            Node else_st = this.else_statement.typeCheck();
            
            if(SimpLanPlusLib.isSubtype(then_st, else_st)){
                return else_st;
            }

            if(SimpLanPlusLib.isSubtype(else_st, then_st)){
                return then_st;
            }
            
            System.out.println("Incompatible types then branch and else branch statements");
            System.exit(0);
        }
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