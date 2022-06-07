package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentNode implements Node{
        // ID '=' exp
        private IdNode id;
        private Node exp;

        public AssignmentNode(IdNode ID, Node exp){
            this.id = ID;
            this.exp = exp;
        }

        @Override
        public Node typeCheck() {
            Node idType = id.typeCheck();
            Node expType = exp.typeCheck();
            
            if(!SimpLanPlusLib.isSubtype(expType, idType)){
                System.out.println("Assignment Error: Assignment type failed");
                System.exit(0);
            }
            
            return new TypeNode("void");
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

            return res;
        }
}
