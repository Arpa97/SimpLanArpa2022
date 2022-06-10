package ast;

import util.Effect;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{

    private String id;
    private Effect effect;
    private STentry entry;
    private int nestinglevel;

    public IdNode(String id){
        
        this.id = id;
        this.effect = new Effect();
    }


    @Override
    public Node typeCheck() {
        
//        if (entry.getType() instanceof ArrowTypeNode){
//            System.out.println("Wrong usage of function identifier");
//            System.exit(0);
//        }
        
        return null;
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
        return new ArrayList<SemanticError>();
    }

    @Override
    public String Analyze() {
        return "IdNode: " + this.id + "at nesting level " + nestinglevel + "\n" + entry.Analyze();
    }
}