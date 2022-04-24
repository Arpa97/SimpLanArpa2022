package ast;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class ArgNode implements Node {

        private TypeNode type;
        private IdNode id;
        private boolean isVar = false;

        public ArgNode(TypeNode type, IdNode id, boolean isVar) {
            this.type = type;
            this.id = id;
            this.isVar = isVar;
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
            return null;
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
