package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {

    private IdNode id;
    private STentry entry;
    //private int nestinglevel;

    public DerExpNode(IdNode id){
        this.id = id;
    }


    @Override
    public Node typeCheck() {
//        if (entry.getType() instanceof ArrowTypeNode) { //
//            System.out.println("Wrong usage of function identifier");
//            System.exit(0);
//        }
//        return entry.getType();
        //return id.typeCheck();
        if(entry == null){
            System.out.println("Variable "+this.id.getId()+" not declared");
            System.exit(0);
        }
        //System.out.println(entry.getType());
        return entry.getType();
    }

    @Override
    public String codeGeneration() {
        return id.codeGeneration(); // r1 <- codgen(stable, derExp)
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
        
        entry = tmp;
        return res;
    }

    @Override
    public String Analyze() {
        return "DerExpNode:";
    }
}