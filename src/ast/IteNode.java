package ast;

import java.util.ArrayList;

import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class IteNode implements Node {
	private Node exp;
	private Node then_statement;
	private Node else_statement;

	public IteNode(Node exp, Node then_statement, Node else_statement) {
		this.exp=exp;
		this.then_statement = then_statement;
		this.else_statement = else_statement;
	}

	@Override
	public String toPrint(String indent) {
		String stringa = "if(";
		if(exp != null)
			stringa += exp.toPrint(indent);
		stringa += "){\n" + then_statement.toPrint(indent) + "\n}";
		if(else_statement != null)
			stringa += "else{\n" + else_statement.toPrint(indent)+ "\n}";
		return indent + stringa + "\n";
	}

	@Override
	public Node typeCheck() {
		if(exp == null) {
			System.err.println("The condition is null.");
			System.exit(-1);
		}
		if(!SimpLanPlusLib.isSubtype(exp.typeCheck(), new TypeNode("bool")) ) {
			System.err.println("Condition must return boolean value");
			System.exit(-1);
		}
		Node type_then = then_statement.typeCheck();
		if(else_statement != null) {
			Node type_else = else_statement.typeCheck();
			if(type_then != null && type_else != null) {
				if(!SimpLanPlusLib.isSubtype(type_then,type_else) ) {
					System.err.println("Statements must have the same type");
					System.exit(-1);
				}
			}
		}
		if(exp.getClass().getName().contains("DerExpNode")) {
			DerExpNode exp1 = (DerExpNode)(exp);
			IdNode variable = exp1.getIdNode();
			STentry entryVariable = variable.getEntry();
			entryVariable.getEffect().setUsed();
		}
		return null;
	}

	@Override
	public String codeGeneration() {

		String true_branch  = SimpLanPlusLib.freshLabel();
		String end_if = SimpLanPlusLib.freshLabel();

		if (else_statement != null){
			return this.codeGeneration() +          //r1 = cgen(stable, e)
					"lir2 1\n" +
					"beq " + true_branch + "\n" +           //se r1 = r2 = 1 quindi la condizione è vera, salta a true_branch
					else_statement.codeGeneration() +       //qui caso else. r1 = cgen(stable, else_stm)
					"b " + end_if + "\n" +                  //jump ad end_if
					true_branch + ":\n" +                   //caso true branch
					then_statement.codeGeneration() +       //cgen(stable, then)
					end_if + ":\n";                         //end_if: 
		} else{
			//caso in cui non si ha la condizione dell'else branch
			return  this.codeGeneration() +
					"lir2 1\n" +
					"beq  " + true_branch + "\n" +
					"b " +end_if + "\n" +
					true_branch + ":\n" +
					then_statement.codeGeneration() +
					end_if + ":\n";
		}
		
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(this.exp != null) {
			output.addAll(this.exp.checkSemantics(env));
		}
		
		if(this.then_statement != null) {
			output.addAll(this.then_statement.checkSemantics(env));
		}
		
		if(this.else_statement != null) {
			output.addAll(this.else_statement.checkSemantics(env));
		}
		return output;
	}
	
	public Node getExp() {
		return exp;
	}
}

/*package ast;

import java.util.ArrayList;

import parser.SimpLanPlusParser.ExpContext;
import util.Environment;
import util.SemanticError;

public class IteNode implements Node {
	
	private Node exp;
	private ArrayList<Node> then_statement;
	private ArrayList<Node> else_statement;

	public IteNode(Node exp, ArrayList<Node> then_statement, ArrayList<Node> else_statement) {
		
		this.exp=exp;
		this.then_statement = then_statement;
		this.else_statement = else_statement;
	}

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		String stringa = "if(" + exp.toPrint(indent) + "){\n"; 
		for(Node stat: then_statement) {
			stringa += stat.toPrint(indent) + "\n ";
		//	System.out.println("Perche si");

		}
		stringa += "}\n"; 
		if(else_statement.size()>0) {
		//	System.out.println("Perche no");
			
			stringa += "else { \n";
			for(Node stat: else_statement) {
				stringa += stat.toPrint(indent)+ "\n";
			}
			stringa += "}";
		}
		return stringa + "\n";
	}

	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		
		if(this.exp != null) {
			output.addAll(this.exp.checkSemantics(env));
		}
		
		for(Node stat : then_statement) {
			output.addAll(stat.checkSemantics(env));
		}
		
	
		for(Node stat : else_statement) {
			output.addAll(stat.checkSemantics(env));
		}
		
		return output;
	}

}*/
