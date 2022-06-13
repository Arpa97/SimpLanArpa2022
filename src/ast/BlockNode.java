package ast;

import java.util.ArrayList;
import java.util.HashMap;
import ast.expNode.*;
import util.*;

public class BlockNode implements Node {
	private ArrayList<Node> declarations;
	private ArrayList<Node> statements;

	public BlockNode(ArrayList<Node> declarations,ArrayList<Node> statements) {
		this.declarations = declarations;
		this.statements = statements;
	}

	@Override
	public String printer(String indent){
		String declstr = "";
		String statstr = "";
		for(Node dec: declarations)
			declstr += dec.printer(indent);
		for(Node stat: statements)
			statstr += stat.printer(indent);
		return indent + "Block\n" + "Declarations: " + declstr + "\nStatements:" + statstr + "\n";
	}

	@Override
	public Node typeCheck(){
		for(Node dec: declarations) {
			dec.typeCheck();
			DecVarNode dec1 = (DecVarNode)dec;
			if(dec1.getArgument() == true) {
				dec1.getEntry().getEffect().setInitialized();
			}
		}
		ArrayList<RetNode> retnodes = new ArrayList<RetNode>();
		for(Node stat: statements) { 
			StatementNode stat1 = (StatementNode) stat;
			//check if there are Return in the statements
			if(stat1.getStatement().getClass().toString().contains("RetNode")) {
				RetNode statReturn = (RetNode)(stat1.getStatement());
				retnodes.add(statReturn);
				Node exp = statReturn.getExp();
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
			} else {
				stat.typeCheck();
				Node node = stat1.getStatement();
				String className = node.getClass().getName();
				if(className.contains("AssignmentNode")) {
					AssignmentNode node1 = (AssignmentNode)(node);
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
		}
		ArrayList<Node> retTypes = new ArrayList<Node>();
		Node type;
		//save the type of returns in a list
		Node typeRet;
		for(RetNode ret : retnodes) {
			typeRet = ret.typeCheck();
			if(typeRet != null) {
				retTypes.add(typeRet);
			}
		}
		int cont=0;
		//control if the returns' type are equals
		for(Node types : retTypes) {
			for(Node types1 : retTypes) {
				if(!(SimpLanPlusLib.isSubtype(types, types1))) {
					cont++;
				}
			}
		}
		for(Node dec:declarations) {
			DecVarNode variable = (DecVarNode)(dec);
			STentry entryVariable = variable.getEntry();
			if(entryVariable.getEffect().getValue() < 2) {
				System.err.println("The variable " + variable.getIdNode().getId() + " isn't used in this block.");
				System.exit(-1);
			}
		}
		if(cont>0) {
			System.err.println("Different type of return in the function");
			System.exit(-1);
		} else {
			if(retTypes.size()>0) {
				type = retTypes.get(0);
				return type;
			}
		}
		return null;
	}

	@Override
	public String codeGeneration(){

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
		env.increaseNesting();
		HashMap<String,STentry> hm = new HashMap<String,STentry>();
		env.getSymTable().add(hm);
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(declarations.size() > 0) {
			for(Node dec:declarations) {
				if(dec != null) {
					output.addAll(dec.checkSemantics(env));
				}else {
					output.add(new SemanticError("Declaration is null."));
				}
			}
		}
		if(statements.size() > 0) {
			for(Node stat:statements) {
				if(stat != null) {
					output.addAll(stat.checkSemantics(env));
				}else {
					output.add(new SemanticError("Statement is null."));
				}
			}
		}
		env.getSymTable().remove(env.getNestingLevel());
		env.decreaseNesting();
		return output;
	}

	public ArrayList<Node> getStatements() {
		return this.statements;
	}

	public ArrayList<Node> getDeclarations(){
		return declarations;
	}

	public void setDeclarations(ArrayList<Node> declarations){
		this.declarations = declarations;
	}
}
