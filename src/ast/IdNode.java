package ast;

import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class IdNode implements Node {
	private String id;
	private STentry entry;
	private int nLevel_id;
	
	public IdNode(String text) {
		id = text;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"ID "+ id + "\n";
	}

	@Override
	public Node typeCheck() {
		if(entry==null) {
			System.exit(-1);
		}
		return entry.getType();
	}

	@Override
	public String codeGeneration() {
		// ID case
		String ar = "";
		for(int i = 0; i < nLevel_id - entry.getNestinglevel(); i++ ){
			ar += "lw 0\n";     // lw al 0(al) :: al = MEMORY[al + 0]
		}
		return "lfp\n" +                        // fp -> top_of_stack :: s -> [fp]
				"sal\n" +                        // al <- top_of_stack :: al <- fp; s -> []
				ar     +                        // lw al 0(al) :: al = MEMORY[al + 0] to check the AR; s -> []
				"lw1 "+ entry.getOffset()+"\n";  // lw r1 entry.offset(al) :: r1 <- MEMORY[entry.offset + al]; s -> []
		
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		int j=env.getNestingLevel();
		STentry tmp=null; 
		while (j>=0 && tmp==null) {
			tmp=(env.getSymTable().get(j--)).get(id);
		}
	    if(tmp==null){
	    	output.add(new SemanticError("Id "+id+" not declared"));
	    }else{
	    	entry = tmp;
			this.nLevel_id = env.getNestingLevel();
	    }
		return output;
	}

	public STentry getEntry() {
		return entry;
	}

	public void setEntry(STentry entry) {
		this.entry = entry;
	}

	public String getId() {
		return id;
	}

	public int getnLevel_id() {
		return nLevel_id;
	}

	public void setnLevel_id(int nLevel_id) {
		this.nLevel_id = nLevel_id;
	}
}