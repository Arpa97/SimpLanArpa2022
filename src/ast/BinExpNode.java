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
//        switch(op){
//            //controllo che nelle uguaglianze ambedue i termini siano dello stesso tipo
//            case "==", "!=": {
                if(!( SimpLanPlusLib.isSubtype(left.typeCheck(), right.typeCheck()) ||
                        SimpLanPlusLib.isSubtype(right.typeCheck(), left.typeCheck()) )) {
                    System.out.println("Incompatible operands type for the operator" + op);
                    System.exit(0);
                }
                return new BoolTypeNode();
//            }
//            //controllo che nelle operazioni aritmetiche gli operandi siano interi
//            case "+", "-", "*", "/": {
//                if(! (left.typeCheck().getClass().equals(IntTypeNode.class) &&
//                        right.typeCheck().getClass().equals(IntTypeNode.class))) {
//                    System.out.println("Type of operand is not integer: Error addressing ----> " + op);
//                    System.exit(0);
//                }
//                return new IntTypeNode();
//            }
//            //controllo che nelle disuguaglianze ambedue i termini siano interi
//            case ">=", "<=", "<", ">": {
//                if(! (left.typeCheck().getClass().equals(IntTypeNode.class) &&
//                        right.typeCheck().getClass().equals(IntTypeNode.class))) {
//                    System.out.println("Type of operand is not integer: Error addressing ---->  " + op);
//                    System.exit(0);
//                }
//                return new BoolTypeNode();
//            }
//            //controllo che nelle operazioni booleane or e and gli operandi siano booleani
//            case "&&", "||": {
//                if(! (left.typeCheck().getClass().equals(BoolTypeNode.class) &&
//                        right.typeCheck().getClass().equals(BoolTypeNode.class))) {
//                    System.out.println("Type of operand is not boolean: Error addressing ---->  " + op);
//                    System.exit(0);
//                }
//                return new BoolTypeNode();
//            }
//        }
        //return null;
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