package ast;

import util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentNode implements Node{
        // ID '=' exp
        private IdNode id;
        private Node exp;
        private STentry entry;
        private Effect effect;

        public AssignmentNode(IdNode ID, Node exp){
            this.id = ID;
            this.exp = exp;
            this.effect = new Effect();
        }

        @Override
        public Node typeCheck() {
            Node idType = id.typeCheck();
            Node expType = exp.typeCheck();
            
            if(entry == null){
                System.out.println("Assignment Error: Variable not declareted yet");
                System.exit(0);
            }
            
            if(!SimpLanPlusLib.isSubtype(expType, idType)){
                System.out.println("Assignment Error: Assignment type failed");
                System.exit(0);
            }
            //la variabile viene inizializzata
            effect.setInitialized();
            
            //faccio tornare un null perchè al prof non piace il tipo di ritorno void
            return new VoidNode();
        }

        @Override
        public String codeGeneration() {
            return null;
        }

        @Override
        public ArrayList<SemanticError> checkSemantics(Environment env) {
            ArrayList<SemanticError> res = new ArrayList<SemanticError>();
            HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);

            // check for variable with such id in current level and below
            int j=env.nestingLevel;
            STentry tmp=null;
            while (j>=0 && tmp==null)
                tmp=(env.symTable.get(j--)).get(this.id.getId());
            if (tmp==null)
                res.add(new SemanticError("Variable "+this.id.getId()+" not declared"));
            else{   // if variable exists, check the exp
                if(this.exp != null) {
                    res.addAll(this.exp.checkSemantics(env));
                }
            }
            entry = tmp;

            return res;
        }

    @Override
    public String Analyze() {
        return "AsgnNode: " + this.id.Analyze() + " = " + this.exp.Analyze();
    }
}
