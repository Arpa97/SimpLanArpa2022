package ast;
import util.Environment;
import util.SemanticError;
import util.VoidNode;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramNode implements Node{

    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;

    public ProgramNode(ArrayList<Node> declarations, ArrayList<Node> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }

    public Boolean checkRetValue(){
        boolean hasRetValue = false;
        boolean hasElse = false;
        if (statements.size() > 0) {
            for (int i = 0; i < statements.size(); i++) {
                StatementNode stm = (StatementNode) statements.get(i);
                if (stm.getStatement() instanceof IteNode) {
                    hasRetValue = ((IteNode) stm.getStatement()).isCheckRetValueIte();
                    //caso in cui esista anche l'else statement
                    if (((IteNode) stm.getStatement()).getElse_statement() != null) {
                        hasElse = true;
                    }
                }
                //se ha UN tipo di ritorno
                if (stm.getCheckRet()) {
                    if (hasRetValue && hasElse) {
                        System.out.println("Program Error: Multiple return conflicts with iteration statement");
                        System.exit(0);
                    }
                    //caso in cui si abbiano più tipi di ritorno nello stesso blocco
                    if (i != statements.size() - 1) {
                        System.out.println("Program Error: Multiple return");
                        System.exit(0);
                    } else return true;
                }
            }
        }

        return hasRetValue;

    }

    @Override
    public Node typeCheck() {
        ArrayList<Node> baseTypeNode = new ArrayList<Node>();

        for (Node dec: declarations){
            //baseTypeNode.add(dec.typeCheck());
            dec.typeCheck();
        }

        for (Node st: statements){
            //baseTypeNode.add(st.typeCheck());
            st.typeCheck();
            StatementNode stat = (StatementNode) (st);
            Node stat1 = stat.getStatement();
            if(stat1.getClass().getName().contains("AssignmentNode")){
                AssignmentNode ass = (AssignmentNode) (stat1);
                Node ass1 = ass.getExp(); //qui abbiamo espressione dell'assegnamento
                if(ass1.getClass().getName().contains("DerExpNode")){
                    DerExpNode var = (DerExpNode) (ass1);
                    if(var.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                        System.err.println("Variable not initialized");
                        System.exit(0);
                    }
                }
                if(ass1.getClass().getName().contains("CallExpNode")){
                    CallExpNode call = (CallExpNode) (ass1);
                    CallNode call1 = (CallNode) (call.getCall());
                    ArrayList<Node> expCall = call1.getExp();
                    for(Node exp : expCall){
                        if(exp.getClass().getName().contains("DerExpNode")){
                            DerExpNode exp1 = (DerExpNode) (exp);
                            if(exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                                System.err.println("Variable not initialized");
                                System.exit(0);
                            }
                        }
                        
                    }
                }
            }
            if(stat1.getClass().getName().contains("PrintNode")){
                PrintNode ass = (PrintNode) (stat1);
                Node ass1 = ass.getExp(); //qui abbiamo espressione dell'assegnamento
                if(ass1.getClass().getName().contains("DerExpNode")){
                    DerExpNode var = (DerExpNode) (ass1);
                    if(var.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                        System.err.println("Variable not initialized");
                        System.exit(0);
                    }
                }
                if(ass1.getClass().getName().contains("CallExpNode")){
                    CallExpNode call = (CallExpNode) (ass1);
                    CallNode call1 = (CallNode) (call.getCall());
                    ArrayList<Node> expCall = call1.getExp();
                    for(Node exp : expCall){
                        if(exp.getClass().getName().contains("DerExpNode")){
                            DerExpNode exp1 = (DerExpNode) (exp);
                            if(exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                                System.err.println("Variable not initialized");
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            if(stat1.getClass().getName().contains("ReturnNode")){
                ReturnNode ass = (ReturnNode) (stat1);
                Node ass1 = ass.getExp(); //qui abbiamo espressione dell'assegnamento
                if(ass1.getClass().getName().contains("DerExpNode")){
                    DerExpNode var = (DerExpNode) (ass1);
                    if(var.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                        System.err.println("Variable not initialized");
                        System.exit(0);
                    }
                }
                if(ass1.getClass().getName().contains("CallExpNode")){
                    CallExpNode call = (CallExpNode) (ass1);
                    CallNode call1 = (CallNode) (call.getCall());
                    ArrayList<Node> expCall = call1.getExp();
                    for(Node exp : expCall){
                        if(exp.getClass().getName().contains("DerExpNode")){
                            DerExpNode exp1 = (DerExpNode) (exp);
                            if(exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                                System.err.println("Variable not initialized");
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            if(stat1.getClass().getName().contains("IteNode")){
                IteNode ass = (IteNode) (stat1);
                Node ass1 = ass.getExp(); //qui abbiamo espressione dell'assegnamento
                if(ass1.getClass().getName().contains("DerExpNode")){
                    DerExpNode var = (DerExpNode) (ass1);
                    if(var.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                        System.err.println("Variable not initialized");
                        System.exit(0);
                    }
                }
                if(ass1.getClass().getName().contains("CallExpNode")){
                    CallExpNode call = (CallExpNode) (ass1);
                    CallNode call1 = (CallNode) (call.getCall());
                    ArrayList<Node> expCall = call1.getExp();
                    for(Node exp : expCall){
                        if(exp.getClass().getName().contains("DerExpNode")){
                            DerExpNode exp1 = (DerExpNode) (exp);
                            if(exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1){
                                System.err.println("Variable not initialized");
                                System.exit(0);
                            }
                        }

                    }
                }
            }
            if(stat1.getClass().getName().contains("CallNode")){
                CallNode ass = (CallNode) (stat1);
                ArrayList<Node> ass1 = ass.getExp(); //qui abbiamo espressione dell'assegnamento
                for(Node assNode : ass1) {
                    if (assNode.getClass().getName().contains("DerExpNode")) {
                        DerExpNode var = (DerExpNode) (assNode);
                        if (var.getIdNode().getEntry().getEffect().getVarEffect() < 1) {
                            System.err.println("Variable not initialized");
                            System.exit(0);
                        }
                    }
                    if (assNode.getClass().getName().contains("CallExpNode")) {
                        CallExpNode call = (CallExpNode) (assNode);
                        CallNode call1 = (CallNode) (call.getCall());
                        ArrayList<Node> expCall = call1.getExp();
                        for (Node exp : expCall) {
                            if (exp.getClass().getName().contains("DerExpNode")) {
                                DerExpNode exp1 = (DerExpNode) (exp);
                                if (exp1.getIdNode().getEntry().getEffect().getVarEffect() < 1) {
                                    System.err.println("Variable not initialized");
                                    System.exit(0);
                                }
                            }

                        }
                    }
                }
            }
        }
        for(Node dec : declarations){
            DeclarationNode dec1 = (DeclarationNode) (dec);
            Node dec2 = dec1.getDec();
            if(dec2.getClass().getName().contains("DecVarNode")){
                DecVarNode var = (DecVarNode) (dec2);
                if(var.getEntry().getEffect().getVarEffect() < 2){
                    System.err.println("Variable not used");
                    System.exit(0);
                }
            }
            
        }
        //ritorna ultima dichiarazione o stm del blocco
        if(baseTypeNode.size() > 0){
            return baseTypeNode.get(baseTypeNode.size() - 1);
        }
        else return new VoidNode(); //se non ci sono dichiarazioni e statement ritorna null/blocco void
        //return new VoidNode();

    }

    @Override
    public String codeGeneration() {

        String code="";
        code += "lfp\n";      //fp -> top       s->[fp]
        code += "lal\n";      //al -> top       s->[al, fp]
        code += "cfp\n";      //setta fp<-sp

        for (Node dec : declarations){
            code += "push0\n";              //s->[d(0) ... d(n)] 
            code += dec.codeGeneration();   //cgen(stable, dec)     s->[d(0) .. d(n), al, fp]
        }

        for(Node st : statements){
            code += st.codeGeneration();    //cgen(stable, stm)
        }
        for(Node dec : declarations){
            code += "pop\n";                //toglie da stack ogni dichiarazione
        }

        code += "sal\n";        // al <- top    s->[fp]
        code +="sfp\n";         // fp <- top    s->[]

        return code;
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

    @Override
    public String Analyze() {
        String out ="";
        for (Node dec:declarations)
            out += dec.Analyze() ;

        for (Node st:statements)
            out += st.Analyze();


        return "ProgramNode:" + out + "\n" ;
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
