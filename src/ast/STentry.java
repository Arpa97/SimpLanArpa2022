package ast;

import util.Effect;

public class STentry {
 
  private int nl;
  private Node type;
  private int offset;
  private Effect effect;
  
  public STentry (int nl, int offset,boolean initialized){
	  this.nl = nl;
	  this.offset = offset;
	  effect = new Effect(initialized);
  } 
   
  public STentry (int nl, Node type, int offset,boolean initialized){
	  this.nl = nl;
	  this.type = type;
	  this.offset = offset;
	  effect = new Effect(initialized);
  }

  public void addType (Node type){
	  this.type = type;
  }
  
  public Node getType (){
	  return type;
  }

  public int getOffset (){
	  return offset;
  }
  
  public int getNestinglevel (){
	  return nl;
  }

  public Effect getEffect() {
	  return effect;
  }
 
  public String toPrint(String s) { //
	   return s+"STentry: nestlev " + Integer.toString(nl) +"\n"+
			  s+"STentry: type" +  type.toPrint(s+"  ") + 
		      s+"\nSTentry: offset " + Integer.toString(offset) + "\n"+
		      s+"STentry: initialized is " + (effect.getValue() < 1) + "\n"+
	   		  s+"STentry: used is " + (effect.getValue() < 2) + "\n";
  }
}  