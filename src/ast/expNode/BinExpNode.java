package ast.expNode;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.STentry;
import ast.TypeNode;
import util.*;

public class BinExpNode implements Node {
	private Node leftNode;
	private Node rightNode;
	private String operator;

	public BinExpNode(Node left, Node right, String op) {
		this.leftNode = left;
		this.rightNode = right;
		this.operator = op;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env){
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(this.leftNode != null) {
			output.addAll(leftNode.checkSemantics(env));
		}
		if(this.rightNode != null) {
			output.addAll(rightNode.checkSemantics(env));
		}
		return output;
	}

	@Override
	public String printer(String emptySpace) {
		return "BinExp " + leftNode.printer(emptySpace) + " " + operator + " " + rightNode.printer(emptySpace)+ "\n";
	}

	@Override
	public Node typeCheck() {

		//could have been implemented better as a Switch. Unfortunately, we couldn't use "string" statements in switch
		//conditions due to mismatch of Java version. So, we sticked with if statements.
		//our first implementation used different exp nodes. We refactored the code to be less dispersive and more
		//efficient to read and understand, changing also the structure of the file hierarchy taking example from other
		//colleagues who had done a better and neater job than us in code structure
		if (!(SimpLanPlusLib.isSubtype(leftNode.typeCheck(),rightNode.typeCheck()))){
			System.err.println("Terms type mismatch");
			System.exit(-1);
		}
		String className1 = leftNode.getClass().getName();
		String className2 = rightNode.getClass().getName();
		if(className1.contains("DerExpNode")) {
			DerExpNode variable = (DerExpNode)(leftNode);
			IdNode detailVariable = variable.getIdNode();
			STentry entryStat = detailVariable.getEntry();
			if(entryStat.getEffect().getValue() < 1) {
				System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
				System.exit(-1);
			}
			entryStat.getEffect().setUsed();
		}
		if(className2.contains("DerExpNode")) {
			DerExpNode variable = (DerExpNode)(rightNode);
			IdNode detailVariable = variable.getIdNode();
			STentry entryStat = detailVariable.getEntry();
			if(entryStat.getEffect().getValue() < 1) {
				System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
				System.exit(-1);
			}
			entryStat.getEffect().setUsed();
		}
		TypeNode firstType = (TypeNode) leftNode.typeCheck();
		if(operator.contains("'+'") || operator.contains("'-'") || operator.contains("'*'") || operator.contains("'/'") || operator.contains("'<'") || operator.contains("'>'")
				|| operator.contains("'<='") || operator.contains("'>='")) {
			if(!(firstType.getText().equals("int"))) {
				System.err.println("The operator requires int type");
				System.exit(-1);
			}
			if(operator.contains("'+'") || operator.contains("'-'") || operator.contains("'*'") || operator.contains("'/'")) {
				return new TypeNode("int");
			}
			if(operator.contains("'<'") || operator.contains("'>'") || operator.contains("'<='") || operator.contains("'>='")) {
				return new TypeNode("bool");
			}
			
		}
		if(operator.contains("'&&'") || operator.contains("'||'")) {
			if(!(firstType.getText().equals("bool"))) {
				System.err.println("The operator requires bool type");
				System.exit(-1);
			}
			return new TypeNode("bool");
			
		}
		if(operator.contains("==") || operator.contains("!=")) {
			return new TypeNode("bool");
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		
		if(operator.contains("'&&'")){
			return rightNode.codeGeneration() +     //r1 <- cgen(stable, right)                 S->[]
					"lr1\n"+                    //r1 -> top of Stack        S->right        S->[r1]
					leftNode.codeGeneration() +     //r2 <- cgen(stable, left)                  S->[r1]
					"sr2\n"+                    //r2 -> top of Stack        r2<-right       S->[]    
					"and\n";              // add r1 <- r1 && r2       left op right   S->[] 
		}
		
		if(operator.contains("'||'")){
			return rightNode.codeGeneration() +     //r1 <- cgen(stable, right)                 S->[]
					"lr1\n"+                    //r1 -> top of Stack        S->right        S->[r1]
					leftNode.codeGeneration() +     //r2 <- cgen(stable, left)                  S->[r1]
					"sr2\n"+                    //r2 -> top of Stack        r2<-right       S->[]    
					"or\n";              // add r1 <- r1 || r2       left op right   S->[]
		}
		
		if(operator.contains("'/'")){
			return rightNode.codeGeneration()+
					"lr1\n"+
					leftNode.codeGeneration()+
					"sr2\n"+
					"div\n";
		}
		
		if(operator.contains("'=='")){
			String true_branch = SimpLanPlusLib.newFunLabel();
			String end_if = SimpLanPlusLib.newFunLabel();
			return rightNode.codeGeneration() +
					"lr1\n" +
					leftNode.codeGeneration() +
					"sr2\n" +
					"beq " + true_branch + "\n" +
					"lir1 0\n" +
					"b " + end_if + "\n" +
					true_branch + ":\n" +
					"lir1 1\n" +
					end_if + ":\n";
		}
		
		if(operator.contains("'>='")){
			//bleq $r1 $r2      salta al label se r1 <= r2
			//caso nostro left >= right         left = r2, right = r1
			String true_branch = SimpLanPlusLib.newLabel();
			String end_if = SimpLanPlusLib.newLabel();


			return leftNode.codeGeneration() +
					"lr1\n" +
					rightNode.codeGeneration() +
					"sr2\n" +                   //r2 = left, r1 = right
					"bleq " + true_branch + "\n" +
					"lir1 0\n" +                  //caso falso, r1<-0
					"b " + end_if + "\n" +
					true_branch + ":\n" +
					"lir1 1\n" +                 //caso true, r1<-1
					end_if + ":\n";
		}

		if(operator.contains("'>'")){
			//bleq $r1 $r2      salta al label se r1 < r2
			//caso nostro left > right         left = r2, right = r1
			String true_branch = SimpLanPlusLib.newLabel();
			String end_if = SimpLanPlusLib.newLabel();


			return leftNode.codeGeneration() +
					"lr1\n" +
					rightNode.codeGeneration() +
					"sr2\n" +                   //r2 = left, r1 = right
					"bless " + true_branch + "\n" +
					"lir1 0\n" +                  //caso falso, r1<-0
					"b " + end_if + "\n" +
					true_branch + ":\n" +
					"lir1 1\n" +                 //caso true, r1<-1
					end_if + ":\n";
		}
		
		if(operator.contains("'<='")){
			//bleq $r1 $r2      salta al label se r1 <= r2
			//caso nostro left >= right         left = r1, right = r2
			String true_branch = SimpLanPlusLib.newLabel();
			String end_if = SimpLanPlusLib.newLabel();


			return rightNode.codeGeneration() +
					"lr1\n" +
					leftNode.codeGeneration() +
					"sr2\n" +                   //r2 = right, r1 = left
					"bleq " + true_branch + "\n" +
					"lir1 0\n" +                  //caso falso, r1<-0
					"b " + end_if + "\n" +
					true_branch + ":\n" +
					"lir1 1\n" +                 //caso true, r1<-1
					end_if + ":\n";
		}

		if(operator.contains("'<'")){
			//bleq $r1 $r2      salta al label se r1 < r2
			//caso nostro left > right         left = r1, right = r2
			String true_branch = SimpLanPlusLib.newLabel();
			String end_if = SimpLanPlusLib.newLabel();


			return rightNode.codeGeneration() +
					"lr1\n" +
					leftNode.codeGeneration() +
					"sr2\n" +                   //r2 = right, r1 = left
					"bless " + true_branch + "\n" +
					"lir1 0\n" +                  //caso falso, r1<-0
					"b " + end_if + "\n" +
					true_branch + ":\n" +
					"lir1 1\n" +                 //caso true, r1<-1
					end_if + ":\n";
		}

		if(operator.contains("'-'")){
			return rightNode.codeGeneration()+
					"lr1\n"+
					leftNode.codeGeneration()+
					"sr2\n"+
					"sub\n";
		}

		if(operator.contains("'*'")){
			return rightNode.codeGeneration()+
					"lr1\n"+
					leftNode.codeGeneration()+
					"sr2\n"+
					"mul\n";
		}
		
		if(operator.contains("'!='")){
			String eq_branch = SimpLanPlusLib.newFunLabel();
			String end_if = SimpLanPlusLib.newFunLabel();
			return rightNode.codeGeneration() +
					"lr1\n" +
					leftNode.codeGeneration() +
					"sr2\n" +
					"beq " + eq_branch + "\n" +
					"lir1 1\n" +                    //caso not equal: si setta r1 a 1 (true)
					"b " + end_if + "\n" +
					eq_branch + ":\n" +
					"lir1 0\n" +                    //caso equal: si setta r1 a 0 (false)
					end_if + ":\n";
		}

		if(operator.contains("'+'")){
			return rightNode.codeGeneration()+
					"lr1\n"+
					leftNode.codeGeneration()+
					"sr2\n"+
					"add\n";
		}
		else return "";
	}

	public Node getFirst() {
		return leftNode;
	}

	public Node getSecond() {
		return rightNode;
	}
}