package ast;

import com.sun.xml.internal.bind.v2.model.core.ID;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DecVarNode implements Node {

    private Node type;
    private IdNode id;
    private Node exp;
    private STentry entry;
    private Effect effect;
    //private Offset offset;

    public DecVarNode(Node type, IdNode id, Node exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
        this.effect = new Effect();
    }

    public DecVarNode(Node type, IdNode id){
        this.type = type;
        this.id = id;
    }


    @Override
    public Node typeCheck() {
        if(exp != null){
            if(!(SimpLanPlusLib.isSubtype(type, exp.typeCheck()))){
                System.out.println("Type id: " + type + ", Exp Type: " + exp.typeCheck());
                System.out.println("Variable Declaration Error: incompatible value for variable " + id);
                System.exit(0);
            }
            effect.setInitialized();
        }
        
        return new VoidNode();
    }

    @Override
    public String codeGeneration() {
        if (this.exp != null){
            //se c'è assegnamento lo si memorizza all'indirizzo dell'offset corrispondente
            return exp.codeGeneration() +  // r1 = cgen(stable, exp)
                    "swfp " + entry.getOffset() + "\n";  //sw r1 -> entry.offset($fp)
        }
        
        //se exp è null
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);
        if(st.put(this.id.getId(), new STentry(env.nestingLevel, type, env.offset--)) != null){
            res.add(new SemanticError("Variable id "+this.id.getId()+" already declared."));
        }
        if(this.exp!=null){
            res.addAll(this.exp.checkSemantics(env));
            
        }
        
        return res;
    }

    @Override
    public String Analyze() {
        return null;
    }
}