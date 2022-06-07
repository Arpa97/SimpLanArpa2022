package ast;

import com.sun.xml.internal.bind.v2.model.core.ID;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;
import java.util.HashMap;

public class DecVarNode implements Node{

    private TypeNode type;
    private IdNode id;
    private Node exp;
    private STentry entry;
    //private Offset offset;

    public DecVarNode(TypeNode type, IdNode id, Node exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    public DecVarNode(TypeNode type, IdNode id){
        this.type = type;
        this.id = id;
    }


    @Override
    public Node typeCheck() {
        if(exp != null){
            if(!(SimpLanPlusLib.isSubtype(type, exp.typeCheck()))){
                System.out.println("Variable Declaration Error: incompatible value for variable " + id);
                System.exit(0);
            }
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
        if(st.put(this.id.getId(), new STentry(env.nestingLevel, type, env.offset--)) != null){
            res.add(new SemanticError("Variable id "+this.id.getId()+" already declared."));
        }
        if(this.exp!=null){
            res.addAll(this.exp.checkSemantics(env));
        }
        return res;
    }
}