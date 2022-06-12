package ast;

import java.util.ArrayList;
import java.util.HashMap;
import util.Environment;
import util.SemanticError;
import ast.expNode.*;

public class ProgramNode implements Node{
	private ArrayList<Node> declarations;
	private ArrayList<Node> statements;
	
	public ProgramNode(ArrayList<Node> declarations, ArrayList<Node> statements) {
		this.declarations = declarations;
		this.statements = statements;
	}

	@Override
	public String toPrint(String indent) {
		String declstr = "";
		String statstr = "";
		for(Node dec: declarations)
			declstr += dec.toPrint(indent);
		for(Node stat: statements)
			statstr += stat.toPrint(indent);
		return indent + "Main program\n" +  declstr + statstr+ "\n";
	}

	@Override
	public Node typeCheck() {
		for(Node dec: declarations) {
			dec.typeCheck();

		}
		for(Node stat: statements) {
			stat.typeCheck();
			StatementNode stat1 = (StatementNode)stat;
			Node node = stat1.getStatement();
			String className = node.getClass().getName();
			if(className.contains("AssignmentNode")) {
				AssignmentNode node1 = (AssignmentNode)(node);
				Node exp = node1.getExp();
				String className1 = exp.getClass().getName();
				if(className1.contains("DerExpNode")) {
					DerExpNode variable = (DerExpNode)(exp);
					IdNode detailVariable = variable.getIdNode();
					STentry entryStat = detailVariable.getEntry();
					if(entryStat.getEffect().getValue() < 1) {
						System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
						System.exit(-1);
					}
				}
				if(className1.contains("CallExpNode")) {
					CallExpNode calling = (CallExpNode)(exp);
					CallNode call = (CallNode)(calling.getCall());
					ArrayList<Node> expCall = call.getExpressions();
					for(Node singleExp:expCall) {
						if(singleExp.getClass().getName().contains("DerExpNode")) {
							DerExpNode variable = (DerExpNode)singleExp;
							IdNode detailVariable = variable.getIdNode();
							STentry entryStat = detailVariable.getEntry();
							if(entryStat.getEffect().getValue() < 1) {
								System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
								System.exit(-1);
							}
						}
					}
				}
			}
			if(className.contains("PrintNode")) {
				PrintNode node1 = (PrintNode)(node);
				Node exp = node1.getExp();
				String className1 = exp.getClass().getName();
				if(className1.contains("DerExpNode")) {
					DerExpNode variable = (DerExpNode)(exp);
					IdNode detailVariable = variable.getIdNode();
					STentry entryStat = detailVariable.getEntry();
					if(entryStat.getEffect().getValue() < 1) {
						System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
						System.exit(-1);
					}
				}
				if(className1.contains("CallExpNode")) {
					CallExpNode calling = (CallExpNode)(exp);
					CallNode call = (CallNode)(calling.getCall());
					ArrayList<Node> expCall = call.getExpressions();
					for(Node singleExp:expCall) {
						if(singleExp.getClass().getName().contains("DerExpNode")) {
							DerExpNode variable = (DerExpNode)singleExp;
							IdNode detailVariable = variable.getIdNode();
							STentry entryStat = detailVariable.getEntry();
							if(entryStat.getEffect().getValue() < 1) {
								System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
								System.exit(-1);
							}
						}
					}
				}
			}
			if(className.contains("RetNode")) {
				RetNode node1 = (RetNode)(node);
				Node exp = node1.getExp();
				if(exp != null) {
					String className1 = exp.getClass().getName();
					if(className1.contains("DerExpNode")) {
						DerExpNode variable = (DerExpNode)(exp);
						IdNode detailVariable = variable.getIdNode();
						STentry entryStat = detailVariable.getEntry();
						if(entryStat.getEffect().getValue() < 1) {
							System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
							System.exit(-1);
						}
					}
					if(className1.contains("CallExpNode")) {
						CallExpNode calling = (CallExpNode)(exp);
						CallNode call = (CallNode)(calling.getCall());
						ArrayList<Node> expCall = call.getExpressions();
						for(Node singleExp:expCall) {
							if(singleExp.getClass().getName().contains("DerExpNode")) {
								DerExpNode variable = (DerExpNode)singleExp;
								IdNode detailVariable = variable.getIdNode();
								STentry entryStat = detailVariable.getEntry();
								if(entryStat.getEffect().getValue() < 1) {
									System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
									System.exit(-1);
								}
							}
						}
					}
				}
			}
			if(className.contains("IteNode")) {
				IteNode node1 = (IteNode)(node);
				Node exp = node1.getExp();
				String className1 = exp.getClass().getName();
				if(className1.contains("DerExpNode")) {
					DerExpNode variable = (DerExpNode)(exp);
					IdNode detailVariable = variable.getIdNode();
					STentry entryStat = detailVariable.getEntry();
					if(entryStat.getEffect().getValue() < 1) {
						System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
						System.exit(-1);
					}
				}
				if(className1.contains("CallExpNode")) {
					CallExpNode calling = (CallExpNode)(exp);
					CallNode call = (CallNode)(calling.getCall());
					ArrayList<Node> expCall = call.getExpressions();
					for(Node singleExp:expCall) {
						if(singleExp.getClass().getName().contains("DerExpNode")) {
							DerExpNode variable = (DerExpNode)singleExp;
							IdNode detailVariable = variable.getIdNode();
							STentry entryStat = detailVariable.getEntry();
							if(entryStat.getEffect().getValue() < 1) {
								System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
								System.exit(-1);
							}
						}
					}
				}
			}
			if(className.contains("CallNode")) {
				CallNode node1 = (CallNode)(node);
				ArrayList<Node> expr = node1.getExpressions();
				for(int i = 0;i < expr.size();i++){
					String className1 = expr.get(i).getClass().getName();
					if(className1.contains("DerExpNode")) {
						DerExpNode variable = (DerExpNode)(expr.get(i));
						IdNode detailVariable = variable.getIdNode();
						STentry entryStat = detailVariable.getEntry();
						if(entryStat.getEffect().getValue() < 1) {
							System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
							System.exit(-1);
						}
					}
					if(className1.contains("CallExpNode")) {
						CallExpNode calling = (CallExpNode)expr.get(i);
						CallNode nestedCall = (CallNode)(calling.getCall());
						ArrayList<Node> expNestedCall = nestedCall.getExpressions();
						for(Node singleExpNested:expNestedCall) {
							if(singleExpNested.getClass().getName().contains("DerExpNode")) {
								DerExpNode variable = (DerExpNode)(singleExpNested);
								IdNode detailVariable = variable.getIdNode();
								STentry entryStat = detailVariable.getEntry();
								if(entryStat.getEffect().getValue() < 1) {
									System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
									System.exit(-1);
								}
							}
						}
					}
				}
			}
		}
		for(Node dec:declarations) {
			DeclarationNode dec1 = (DeclarationNode)(dec);
			Node dec2 = dec1.getDeclaration();
			if(dec2.getClass().getName().contains("DecVarNode")) {
				DecVarNode variable = (DecVarNode)(dec2);
				STentry entryVariable = variable.getEntry();
				if(entryVariable.getEffect().getValue() < 2) {
					System.err.println("The variable " + variable.getIdNode().getId() + " isn't used in the program.");
					System.exit(-1);
				}
			}/*else{
				if(dec2.getClass().getName().contains("DecFunNode")){
					DecFunNode function = (DecFunNode)(dec2);
					STentry entryFunction = function.getEntry();
					if(entryFunction.getEffect().getValue() < 2) {
						System.err.println("The function " + function.getIdNode().getId() + " is never called in the program.");
						System.exit(-1);
					}
				}
			}*/
		}
		return null;
	}

	@Override
	public String codeGeneration() {

		String code="";
		code += "lfp\n";      //fp -> top       s->[fp]
		code += "lal\n";      //al -> top       s->[al, fp]
		code += "cfp\n";      //setta fp<-sp

		for (Node dec : declarations){
			code += "push 0\n";              //s->[d(0) ... d(n)] 
			code += dec.codeGeneration();   //cgen(stable, dec)     s->[d(0) .. d(n), al, fp]
		}

		for(Node st : statements){
			code += st.codeGeneration();    //cgen(stable, stm)
		}
		for(Node dec : declarations){
			code += "pop\n";                //toglie da stack ogni dichiarazione
		}

		code += "sal\n";        // al <- top    s->[fp]
		code +="sfp\n";         // fp <- top    s->[]

		return code;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env){
		env.incrementNestingLevel();
		HashMap<String,STentry> hm = new HashMap<String,STentry>();
		env.getSymTable().add(hm);
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(declarations.size() > 0 && declarations != null) { 
			//env.offset = -2;
			for(Node dec:declarations) {
				if(dec != null) {
					output.addAll(dec.checkSemantics(env));
				}else {
					output.add(new SemanticError("The declaration is null."));
				}
			}
		}
		if(statements.size() > 0 && statements != null) {
			for(Node stat:statements) {
				if(stat != null) {
					output.addAll(stat.checkSemantics(env));
				}else {
					output.add(new SemanticError("The statement is null."));
				}
			}
		}
		env.getSymTable().remove(env.getNestingLevel());
		env.decrementNestingLevel();
		return output;
	}
}