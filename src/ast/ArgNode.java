package ast;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

//MODIFICHE STE: 
// aggiunti get e setter id e type
public class ArgNode implements Node {

        private Node type;
        private IdNode id;
        private boolean isVar = false;

        public ArgNode(Node type, IdNode id, boolean isVar) {
            this.type = type;
            this.id = id;
            this.isVar = isVar;
        }

        public IdNode getId(){
            return id;
        }
    
        public void setId(IdNode id){
            this.id = id;
        }
    
        public Node getType() {
            return type;
        }
    
        public void setType(Node type) {
            this.type = type;
        }
        @Override
        public String Analyze() {
            return "\n"+"Arg"+this.type.Analyze()+this.id.Analyze();
        }
        @Override
        public Node typeCheck() {
            return null;
        }

        @Override
        public String codeGeneration() {
            return "";
        }

        @Override
        public ArrayList<SemanticError> checkSemantics(Environment env) {
            ArrayList<SemanticError> res = new ArrayList<SemanticError>();
            HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);

            if (st.put(this.id.getId(), new STentry(env.nestingLevel, type, env.offset--)) != null) {
                res.add(new SemanticError("Argument id " + this.id.getId() + " already defined for the function."));
            }

            return res;
        }
    }
