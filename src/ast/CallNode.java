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
	private int nlCall;
	private String f_entry;
	private int nlDec;

	
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
		String parameters = "" ;
		for (int i=0; i< expressions.size(); i++)
			parameters += expressions.get(i).codeGeneration() // r1 <- cgen(stable, e(i)) i in 1, exp.size() - 1; s -> s[e(i)]
					+ "lr1\n" ;      // r1 -> top_of_stack; s -> [e(i), .., e(0), fp]


		String ar = "";
		for(int i = 0; i < this.nlCall - entry.getNestinglevel(); i++ ){
			ar += "lw 0\n";     // lw al 0(al) :: al = MEMORY[al + 0]
		}

		if(this.f_entry == null){
			this.f_entry = this.entry.getReference().getfEntry();
		}

		return "lfp\n"+ 				// push $fp to save it in the stack [fp]
				"lfp\n" +                        // fp -> top_of_stack :: s -> [al, fp]
				"sal\n" +                        // al <- top_of_stack :: al <- fp; s -> [fp]
				ar     +                        // lw al 0(al) :: al = MEMORY[al + 0] to check the AR; s -> [fp]
				//  "lw1 "+ entry.getOffset()+"\n"+  // lw r1 entry.offset(al) :: r1 <- MEMORY[entry.offset + al]; s -> [fp]
				// "lr1\n"+ //inserisco activation link in stack
				"lal\n"+ //load the access link into the top of the stack; al -> top_of_stack; s -> [al,fp]
				parameters +            // cgen(stable, exp.get(i)) :: for i in exp.size() - 1 to 0; s-> [e(n), .., e(0),al, fp]
				"mfp " + expressions.size() + "\n" +   // move sp in fp
				"cra\n"  +              // ra <- ip + 4
				"lra\n" +               // push ra in the stack
				"b " + f_entry + "\n";  // doing js on the address ra; ip <- ra; s -> [ra, e(n), .., e(0), al, fp]


	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		STentry flag = null;
		int i = env.getNestingLevel();
		nlCall = i;
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
