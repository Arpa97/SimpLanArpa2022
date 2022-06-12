package ast;

import java.util.ArrayList;
import java.util.HashMap;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class DecFunNode implements Node {
	private Node type;
	private IdNode idNode;
	private ArrayList<Node> args;
	private Node block;
	private STentry entry;

	public DecFunNode(Node type, IdNode idNode, ArrayList<Node> args, Node block) {
		this.type = type;
		this.idNode = idNode;
		this.args = args;
		this.block = block;
	}

	@Override
	public String toPrint(String indent) {
		String stringa = "DecFun ";
		if(type != null) {
			stringa += type.toPrint(indent) + " ";
		}
		stringa += idNode.toPrint(indent) + " Args(";
		for(int i = 0;i < args.size();i++) {
			stringa += args.get(i).toPrint(indent) + " ";
			if(i < (args.size() - 1))
				stringa += ",";
		}
		stringa += ") " + block.toPrint(indent);
		return indent + stringa + "\n";
	}

	@Override
	public Node typeCheck() {
		if(type == null) {
			block.typeCheck();
			ArrayList<Node> statements = ((BlockNode)block).getStatements();
			for(Node stat: statements) {
				StatementNode stats = (StatementNode) stat;
				if(stats.getStatement().getClass().toString().contains("RetNode")){
					RetNode returns = (RetNode) stats.getStatement();
					//In questo modo con void si accetta che venga scritto solo Return (senza exp) o che non ci sia proprio
					if(returns.getExp() != null) {
						System.err.println("The function must return void");
						System.exit(-1);
					}
				}
			}
		}else{
			Node retType = block.typeCheck();
			if(retType == null) {
				System.err.println("The function must return " + type.toPrint(""));
				System.exit(-1);
			}
			if (!(SimpLanPlusLib.isSubtype(retType,type))){
		      System.err.println("Wrong return type for function "+idNode.getId());
		      System.exit(-1);
			}  
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		HashMap<String,STentry> hm = env.getSymTable().get(env.getNestingLevel());
		entry = new STentry(env.getNestingLevel(),type,env.getOffset(),true);
		env.decrementOffset();
		if(hm.put(idNode.getId(),entry) != null) {
			output.add(new SemanticError(idNode.getId()+" is already declared."));
		}else{
			env.incrementNestingLevel();
			HashMap<String,STentry> hmn = new HashMap<String,STentry>();
			env.getSymTable().add(hmn);
			HashMap<ArgNode,TypeNode> parTypes = new HashMap<ArgNode,TypeNode>();
			ArrayList<STentry> entryArgs = new ArrayList<STentry>();
		    //int paroffset=1;
		    BlockNode block1 = (BlockNode)(block);
			ArrayList<Node> declarations = block1.getDeclarations();
			for(Node a : args) {
				ArgNode arg = (ArgNode) a;
				output.addAll(arg.checkSemantics(env));
		    	parTypes.put(arg, (TypeNode)(arg.typeCheck()));
		    	entryArgs.add(arg.getEntry());
		    	//if(arg.getVar() == false) {
				    DecVarNode variableAssociated = new DecVarNode(arg.typeCheck(),arg.getIdNode(),null);
				    variableAssociated.setEntry(arg.getEntry());
				    variableAssociated.setArgument();
				    declarations.add(variableAssociated);
				/*}else{
					int i = env.getNestingLevel() - 1;
		    		STentry flag = null; 
		    		while (i >= 0 && flag == null) {
		    			flag = (env.getSymTable().get(i--)).get(arg.getId());
		    		}
		    	    if(flag == null){
		    	    	output.add(new SemanticError("Id " + arg.getId() + " not declared"));
		    	    }else{
		    	    	arg.setEntry(flag);
		    	    }
		    	}*/
		    }
			block1.setDeclarations(declarations);
			entry.addType(new ArrowTypeNode(parTypes,type,entryArgs));
		    env.getSymTable().remove(env.getNestingLevel());
			env.decrementNestingLevel();
			if(block != null)
				output.addAll(block.checkSemantics(env));
		}
		return output;
	}

	public IdNode getIdNode() {
		return idNode;
	}

	public STentry getEntry() {
		return entry;
	}
}
