package ast;

import java.util.ArrayList;

import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class AssignmentNode implements Node {
	private IdNode idNode;
	private Node exp;
	private Node type;
	private STentry entryVariable;
	
	public AssignmentNode(IdNode idNode, Node exp) {
		this.idNode = idNode;
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"Assignment "+ idNode.toPrint(indent) + " = " + exp.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		if(exp == null) {
			System.err.println("The expression is null.");
			System.exit(-1);
		}
		if(!(SimpLanPlusLib.isSubtype(type, exp.typeCheck()))){
			System.err.println("Incompatible value for variable "+idNode.getId());
			System.exit(-1);
		}
		entryVariable.getEffect().setInitialized();
		if(exp.getClass().getName().contains("DerExpNode")) {
			DerExpNode variableAssigned = (DerExpNode)(exp);
			IdNode variable1 = variableAssigned.getIdNode();
			STentry entryAssigned = variable1.getEntry();
			entryAssigned.getEffect().setUsed();
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		if(idNode instanceof IdNode){
			IdNode idGen = (IdNode) idNode;
			STentry entry = idGen.getEntry();
			//int counterST = ((LhsNode<?>) lhs).getCounterST();
			if(idGen.getId() instanceof String){

				String ar = "";
				for(int i = 0; i < this.entryVariable.getNestinglevel() - entry.getNestinglevel(); i++ ){
					ar += "lw 0\n";     // lw al 0(al) :: al = MEMORY[al + 0]
				}
				return  exp.codeGeneration() +           // r1 <- cgen(stable, exp) s -> []
						"lfp\n" +                        // fp -> top_of_stack :: s -> [fp]
						"sal\n" +                        // al <- top_of_stack :: al <- fp; s -> []
						//"lwafp 0\n" +                        // fp -> top_of_stack :: s -> [fp]
						ar     +                        // lw al 0(al) :: al = MEMORY[al + 0] to check the AR; s -> []
						"sw1 "+ entry.getOffset()+"\n";  // sw r1 entry.offset(al) :: r1 <- MEMORY[al + entry.offset]; s -> []

			}
		}
		// is always an LhsNode if come here
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		STentry flag = null;
		int i = env.getNestingLevel();
		while(i>=0 && flag==null) {
			flag = (env.getSymTable().get(i--)).get(this.idNode.getId());
		}
		if(flag != null) {
			setType(flag.getType());
			if(this.exp != null) {
				output.addAll(this.exp.checkSemantics(env));
			}
			entryVariable = flag;
		} else {
			output.add(new SemanticError("Variable " + this.idNode.getId() + " not defined"));
		}
		return output;
	}
	
	private void setType(Node type) {
		this.type=type;
	}

	
	public Node getExp() {
		return exp;
	}

	public IdNode getIdNode() {
		return idNode;
	}
}