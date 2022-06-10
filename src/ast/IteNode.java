package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class IteNode implements Node{

    private Node exp;
    private Node then_statement;
    private Node else_statement;
    boolean checkRetValueIte;

    public IteNode(Node exp, Node then_statement, Node else_statement){
        this.exp = exp;
        this.then_statement = then_statement;
        this.else_statement = else_statement;
        this.checkRetValueIte = checkRet();
    }

    public IteNode(Node exp, Node then_statement){
        this.exp = exp;
        this.then_statement = then_statement;
        this.checkRetValueIte = checkRet();
    }
    
    public boolean checkRet(){
        checkRetValueIte = false;
        ArrayList<Node> stm_list = new ArrayList<Node>();
        stm_list.add(then_statement);
        if(else_statement != null) stm_list.add(else_statement);
        
        for(Node s: stm_list){
            StatementNode stm = (StatementNode) s;
            if(stm.getCheckRet()){
                checkRetValueIte = true;
            } else checkRetValueIte = false;
        }
        
        return checkRetValueIte;
        
    }

    public Node getExp() {
        return exp;
    }

    public void setExp(Node exp) {
        this.exp = exp;
    }

    public boolean isCheckRetValueIte() {
        return checkRetValueIte;
    }

    public void setCheckRetValueIte(boolean checkRetValueIte) {
        this.checkRetValueIte = checkRetValueIte;
    }

    public Node getElse_statement() {
        return else_statement;
    }

    public void setElse_statement(Node else_statement) {
        this.else_statement = else_statement;
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

            else if(SimpLanPlusLib.isSubtype(else_st, then_st)){
                return then_st;
            }
            else {
                System.out.println("Incompatible types then branch and else branch statements");
                System.exit(0);
            }
        }
        if(exp.getClass().getName().contains("DerExpNode")){
            DerExpNode exp1 = (DerExpNode) (exp);
            exp1.getIdNode().getEntry().getEffect().setUsed();
        }
        return then_statement.typeCheck();
    }

    @Override
    public String codeGeneration() {
        String true_branch  = SimpLanPlusLib.freshLabel();
        String end_if = SimpLanPlusLib.freshLabel();
        
        if (else_statement != null){
            return this.codeGeneration() +          //r1 = cgen(stable, e)
                "lir2 1\n" + 
                "beq " + true_branch + "\n" +           //se r1 = r2 = 1 quindi la condizione è vera, salta a true_branch
                else_statement.codeGeneration() +       //qui caso else. r1 = cgen(stable, else_stm)
                "b " + end_if + "\n" +                  //jump ad end_if
                true_branch + ":\n" +                   //caso true branch
                then_statement.codeGeneration() +       //cgen(stable, then)
                end_if + ":\n";                         //end_if: 
        } else{
            //caso in cui non si ha la condizione dell'else branch
            return  this.codeGeneration() + 
                    "lir2 1\n" +
                    "beq  " + true_branch + "\n" +
                    "b " +end_if + "\n" +
                    true_branch + ":\n" +
                    then_statement.codeGeneration() +
                    end_if + ":\n";
        }
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

    @Override
    public String Analyze() {
        if(else_statement != null) {
            return "IteNode: (" + this.exp.Analyze() + ") then " + this.then_statement.Analyze() + "; else " + this.else_statement.Analyze();
        }
        else{
            return "IteNode: (" + this.exp.Analyze() + ") then " + this.then_statement.Analyze();
        }
    }
}