package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class CallNode implements Node {
	private IdNode idNode;
	private ArrayList<Node> expressions;
	private STentry entry;
	private ArrowTypeNode t = null;

	
	public CallNode(IdNode idNode, ArrayList<Node> expressions) {
		this.idNode = idNode;
		if(expressions == null) {
			this.expressions = new ArrayList<Node>();
		}else {
			this.expressions = expressions;
		}
	}

	@Override
	public String toPrint(String indent) {
		String stringa = "Call " + idNode.toPrint(indent) + "(";
		int i = 0;
		if(expressions != null) {
		for(Node n:expressions) {
			stringa += n.toPrint(indent);
			if(i < (expressions.size() - 1))
				stringa += ",";
			i++;
		}
		stringa += ") ";
		return stringa + "\n";
		} else {
			return "";
		}

	}

	@Override
	public Node typeCheck() {
		HashMap<ArgNode,TypeNode> p = t.getParList();
	     if ( !(p.size() == expressions.size() )) {
    		 System.err.println("Wrong number of parameters in the invocation of "+idNode.getId());
    		 System.exit(0);
    	 }
    	 int cont=0;
    	 for (Entry<ArgNode,TypeNode> par: p.entrySet()) {
    		 if(!(SimpLanPlusLib.isSubtype(expressions.get(cont).typeCheck(), par.getValue()))) {
    			 if((cont+1) == 1)
    			 	System.err.println("Wrong type for "+(cont+1)+"-st parameter in the invocation of "+idNode.getId());
    			 if((cont+1) == 2)
    			 	System.err.println("Wrong type for "+(cont+1)+"-nd parameter in the invocation of "+idNode.getId());
    			 if((cont+1) == 3)
    			 	System.err.println("Wrong type for "+(cont+1)+"-rd parameter in the invocation of "+idNode.getId());
    			 if((cont+1) > 3)
    			 	System.err.println("Wrong type for "+(cont+1)+"-th parameter in the invocation of "+idNode.getId());
    			 System.exit(-1);
    		 }
    		 ArgNode argument = par.getKey();
    		 STentry entryArgument = argument.getEntry();
    		 entryArgument.getEffect().setInitialized();
    		 if(argument.getVar()) {
    			 if(expressions.get(cont).getClass().getName().contains("DerExpNode")) {
    				 DerExpNode variable = (DerExpNode)(expressions.get(cont));
    				 argument.setEntry(variable.getIdNode().getEntry());
    			 }
    		 }
    		 cont++;
    	 }
    	 for(Node exp:expressions) {
    		 if(exp.getClass().getName().contains("DerExpNode")) {
    			 DerExpNode singleExp = (DerExpNode)(exp);
    			 IdNode variable = singleExp.getIdNode();
    			 variable.getEntry().getEffect().setUsed();
    		 }
    	 }
		 return t.getRet();
	}
	
	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		STentry flag = null;
		int i = env.getNestingLevel();
		while (i >=0 && flag==null) {
            flag=(env.getSymTable().get(i--)).get(this.idNode.getId());           
		}
        if (flag==null){
            output.add(new SemanticError("Function "+this.idNode.getId()+" not defined."));
            return output;
        } else {
			 this.entry = flag;
			 entry.getEffect().setUsed();
			 if(this.expressions != null) {
		            for (Node exp : this.expressions) {   	
		                output.addAll(exp.checkSemantics(env));                                
		            }
		     }
        }
        if(entry.getType() instanceof ArrowTypeNode) {
			t = (ArrowTypeNode) entry.getType();	
		} else {
			 System.err.println("Invocation of a non-function "+idNode.getId());
		     System.exit(-1);
		}
    	HashMap<ArgNode,TypeNode> p = t.getParList();
		int cont=0;
		if(expressions.size() > 0) {
		    for (Entry<ArgNode,TypeNode> par: p.entrySet()) {
		       	if(par.getKey().getVar()){
		       		if(expressions.get(cont) != null) {
		       			if(!(expressions.get(cont).getClass().getName().contains("DerExpNode"))) {
		       				output.add(new SemanticError("The argument " +par.getKey().getId()+ " of the function requests var"));
		       			}
		       		}
		       	}
		       	cont++;
		    }
		}
        return output;
	}


	public ArrayList<Node> getExpressions(){
		return expressions;
	}
	
	public ArrowTypeNode getArrow() {
		return t;
	}

	public IdNode getIdNode() {
		return idNode;
	}
}
