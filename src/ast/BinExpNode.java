package ast;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpNode implements Node {

    private String op;
    private Node left;
    private Node right;

    public BinExpNode(String op, Node left, Node right){
        this.op = op;
        this.left = left;
        this.right = right;
    }
    @Override
    public String Analyze() {
        return "\n"+"BinExp "+this.left.Analyze()+ this.op + this.right.Analyze(); // left + op + right
    }
    @Override
    public Node typeCheck() {
        //se teniamo un solo nodo e poi mettiamo i casi successivamente 
        // bisogna distinguere i tipi di operazioni interi: + , - , *, /, <, <=, >, >= 
        // da quelli booleani: == , != , || , && 
        
        if((!SimpLanPlusLib.isSubtype(left.typeCheck(), new IntTypeNode()) &&
            !SimpLanPlusLib.isSubtype(right.typeCheck(), new IntTypeNode())) || 
                (!SimpLanPlusLib.isSubtype(left.typeCheck(), new BoolTypeNode()) &&
                        !SimpLanPlusLib.isSubtype(right.typeCheck(), new BoolTypeNode()))){
            
            System.out.println("Incompatible operands type for the operator" + op +"\n");
            System.exit(0);
        }
        if (SimpLanPlusLib.isSubtype(left.typeCheck(), new BoolTypeNode()) &&
                SimpLanPlusLib.isSubtype(right.typeCheck(), new BoolTypeNode())){
            
            return new BoolTypeNode() /* TypeNode("bool")*/;
        }
        return new IntTypeNode() /* TypeNode("int")*/;
    }

    @Override
    public String codeGeneration() {
        // s -> []
        return right.codeGeneration() +     //r1 <- cgen(stable, right)                 S->[]
                "lr1\n"+                    //r1 -> top of Stack        S->right        S->[r1]
                left.codeGeneration() +     //r2 <- cgen(stable, left)                  S->[r1]
                "sr2\n"+                    //r2 -> top of Stack        r2<-right       S->[]    
                op;              // add r1 <- r1 op r2       left op right   S->[] 
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(this.left!=null) {
            res.addAll(left.checkSemantics(env));
        }
        if(this.right!=null) {
            res.addAll(right.checkSemantics(env));
        }
        return res;
    }
}