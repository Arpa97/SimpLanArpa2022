package ast;

import util.Effect;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{

    private String id;
    private STentry entry;

    public IdNode(String id){
        this.id = id;
    }


    @Override
    public Node typeCheck() {
        
//        if (entry.getType() instanceof ArrowTypeNode){
//            System.out.println("Wrong usage of function identifier");
//            System.exit(0);
//        }
        if(entry == null){
            System.out.println("Variable "+this.id+" not declared");
            System.exit(0);
        }

        return entry.getType();
    }

    public String getId() {
        return id;
    }

    @Override
    public String codeGeneration() {
//        String getAr="";
//        for (int i=0; i<nestinglevel - entry.getNestinglevel(); i++) getAr+="lw\n"; //al = MEMORY[fp + offset]
//        return "push " + entry.getOffset() + "\n" + //si aggiunge l'offset richiesto nello stack 
//               "lfp\n"+getAr+ //si risale catena statica
//                "add\n"+ 
//                "lw"; //si carica nello stack il valore dell'indirizzo ottenuto
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        
        //return new ArrayList<SemanticError>();
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        int j=env.nestingLevel;
        STentry tmp=null;
        while (j>=0 && tmp==null)
            tmp=(env.symTable.get(j--)).get(this.id);
        if (tmp==null)
            res.add(new SemanticError("Variable "+this.id+" not declared"));
        else entry = tmp;
        
        return res;
    }

    @Override
    public String Analyze() {
        return "IdNode: " + this.id + entry.Analyze();
    }

    public STentry getEntry(){ return this.entry; }
}