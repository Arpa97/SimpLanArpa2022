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
    //private Offset offset;

    public DecVarNode(Node type, IdNode id, Node exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
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
            entry.getEffect().setInitialized();
        }
        System.out.println(entry);
        
        return type;
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
        if(this.exp!=null){
            res.addAll(this.exp.checkSemantics(env));
        }
        
        HashMap<String, STentry> st = env.symTable.get(env.nestingLevel);
        STentry entry1 = new STentry(env.nestingLevel, type, env.offset--);
        if(st.put(this.id.getId(), entry1) != null){
            res.add(new SemanticError("Variable id "+this.id.getId()+" already declared."));
        }
        if(exp != null){
            entry1.getEffect().setInitialized();
        }
       entry = entry1;
        
        return res;
    }

    @Override
    public String Analyze() {
        return null;
    }
    
    public STentry getEntry(){ return this.entry;}
}