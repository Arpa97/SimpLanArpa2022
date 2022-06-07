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
        ArrayList<Node> baseTypeNode = new ArrayList<Node>();

        for (Node dec: declarations){
            baseTypeNode.add(dec.typeCheck());
        }

        for (Node st: statements){
            baseTypeNode.add(st.typeCheck());
        }
        //ritorna ultima dichiarazione o stm del blocco
        if(baseTypeNode.size() > 0){
            return baseTypeNode.get(baseTypeNode.size() - 1);
        }
        else return null; /*new VoidNode()*/ //se non ci sono dichiarazioni e statement ritorna null/blocco void

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
