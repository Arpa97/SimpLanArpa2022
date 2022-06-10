package ast;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;


//MODIFICHE STE: 
// aggiunto entry e nesting level, getter e setter vari
public class CallNode implements Node{

    private IdNode id;
    private ArrayList<Node> exp;
    
    private STentry entry;
    private int nlevel;

    public CallNode(IdNode id, ArrayList<Node> exp){
        this.id = id;
        this.exp = exp;
    }

    public CallNode(IdNode id){
        this.id = id;
    }

    public CallNode(IdNode id, ArrayList<Node> exp, STentry entry){
        this.id = id;
        this.exp = exp;
        this.entry = entry;
    }

    public IdNode getId() {
        return id;
    }

    public void setId(IdNode id) {
        this.id = id;
    }

    public ArrayList<Node> getExp() {
        return exp;
    }

    public void setExp(ArrayList<Node> exp) {
        this.exp = exp;
    }

    public STentry getEntry() {
        return entry;
    }

    public void setEntry(STentry entry) {
        this.entry = entry;
    }


    @Override
    public String Analyze() {
        //return "\n" + "DerExpNode" + this.id.Analyze();
        String first = "CallNode" + id + "(";
        String last = ")" + "";
        String exp = "";
        
        for(Node expNode : this.exp){
            exp += expNode.Analyze()+" ";
        }
        String nestingLevel = ":: nesting level" + this.nlevel;
        return first + exp + last + nestingLevel;
    }

    @Override
    public Node typeCheck() {
        ArrowTypeNode t = null;
        if (entry.getType() instanceof ArrowTypeNode){
            t=(ArrowTypeNode) entry.getType(); 
        } else{
            System.out.println("Call Error: invocation of a non-function "+id);
            System.exit(0);
        }
        ArrayList<Node> p = t.getParList();
        
        if(exp.size() != p.size()){
            System.out.println("Incorrect number of declared parameters ");
            System.exit(0);
        }
        
        for(int i = 0; i < p.size(); i++){
            if(!(SimpLanPlusLib.isSubtype((exp.get(i)).typeCheck(), ((ArgNode)p.get(i)).getType()))){
                System.out.println("Call Error: wrong type for " + (i + 1) + "-th parameter in the invocation of " + id);
                System.exit(0);
            }
        }
        for(int i = 0; i < exp.size(); i++){
            if(exp.get(i).getClass().getName().contains("DerExpNode")){
                DerExpNode exp1 = (DerExpNode) (exp.get(i));
                exp1.getIdNode().getEntry().getEffect().setUsed();
            }
        }
        return t.getRet();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        int j=env.nestingLevel;
        STentry tmp=null;
        while (j>=0 && tmp==null)
            tmp=(env.symTable.get(j--)).get(this.id.getId());
        if (tmp==null){
            res.add(new SemanticError("Function "+this.id.getId()+" not declared."));
            return res;
        }
        if(this.exp != null) {
            for (Node arg : exp)
                res.addAll(arg.checkSemantics(env));
        }
        return res;
    }
}