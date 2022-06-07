package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFunNode implements Node {

    private TypeNode type;
    private IdNode id;
    private ArrayList<Node> args;
    private BlockNode block;

    public DecFunNode(Node type, Node id, ArrayList<Node> args, Node block) {
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
        this.args = args;
        this.block = (BlockNode) block;
    }
    @Override
    public String Analyze() {
        String t="void";
        if(this.type!=null){
            t = this.type.Analyze();
        }
        String res = "\n" + "DecFun "+this.id.Analyze();
        if (this.args.size() != 0) {
            for (Node arg : this.args) {
                res += arg.Analyze();
            }
        }
        res += block.Analyze();
        return res;
    }

    @Override
    public Node typeCheck() {
        
        //qui bisogna verificare che il tipo di ritorno: 
        // - se è void non torna niente
        // - se ha un tipo != void, bisogna verificare se è presente il return statement
        // - se ha un tipo != void , ed il return statement è sbagliato
        
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
        // Check if function is not already declared
        int argNumber = 0;
        if(this.args!=null){
            argNumber = this.args.size();
        }
        if(st.put(this.id.getId(), new STentry(env.nestingLevel, type, env.offset--)) != null){
            res.add(new SemanticError("Function id "+this.id.getId()+" already declared."));
            return res;
        }
        // Begin analyzing args
        env.nestingLevel++;
        st = new HashMap<String, STentry>();
        env.symTable.add(st);
        if(this.args.size() >0){
            for(Node arg:this.args){
                res.addAll(arg.checkSemantics(env));
            }
        }
        if(this.block!=null){
            res.addAll(this.block.checkSemanticsFunction(env));
        }
        env.symTable.remove(env.nestingLevel--);
        return res;
    }
}