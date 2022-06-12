package ast;

import java.util.ArrayList;
import ast.expNode.BaseExpNode;
import ast.expNode.BinExpNode;
import ast.expNode.BoolExpNode;
import ast.expNode.CallExpNode;
import ast.expNode.DerExpNode;
import ast.expNode.NegExpNode;
import ast.expNode.NotExpNode;
import ast.expNode.ValExpNode;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;
import parser.SimpLanPlusParser.AssignmentContext;
import parser.SimpLanPlusParser.BlockContext;
import parser.SimpLanPlusParser.DecFunContext;
import parser.SimpLanPlusParser.DecVarContext;
import parser.SimpLanPlusParser.DeclarationContext;
import parser.SimpLanPlusParser.ProgramContext;
import parser.SimpLanPlusParser.StatementContext;
import parser.SimpLanPlusParser.TypeContext;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node>{	
	@Override 
	public Node visitProgram(ProgramContext ctx) { 
		ProgramNode res;
		ArrayList<Node> declarations = new ArrayList<>();
		ArrayList<Node> statements = new ArrayList<>();

		//DECLARATIONS VISIT
		for(SimpLanPlusParser.DeclarationContext decCtx : ctx.declaration()) {
			declarations.add(visit(decCtx));
		}
		//STATEMENT VISIT
		for(SimpLanPlusParser.StatementContext stmCtx : ctx.statement()) {
			statements.add(visit(stmCtx));
		}
		
		res = new ProgramNode(declarations, statements);
		return res; 
	}


	@Override 
	public Node visitBlock(BlockContext ctx) { 
		BlockNode res;
		ArrayList<Node> declarations = new ArrayList<>();
		ArrayList<Node> statements = new ArrayList<>();

		//DECLARATIONS VISIT
		for(DecVarContext decCtx : ctx.decVar()) {
			declarations.add(visit(decCtx));
		}

		//STATEMENT VISIT
		for(SimpLanPlusParser.StatementContext stmCtx : ctx.statement()) {
			statements.add(visit(stmCtx));
		}
		res = new BlockNode(declarations, statements);
		return res; 
	}


	@Override 
	public Node visitDeclaration(DeclarationContext ctx) { 
		Node res;
		if(ctx.decFun() != null) {
			res = visit(ctx.decFun());
		} 
		else if(ctx.decVar() != null) {
			res = visit(ctx.decVar());
		}
		else {
			return null;
		}
		return new DeclarationNode(res);
	}


	@Override 
	public Node visitStatement(StatementContext ctx) {
		Node res;
		if(ctx.block() != null) {
			res = visit(ctx.block());
		}
		else if(ctx.call() != null) {
			res = visit(ctx.call());
		} 
		else if(ctx.ite() != null) {
			res = visit(ctx.ite());
		}
		else if(ctx.ret() != null) {
			res = visit(ctx.ret());
		}
		else if(ctx.print() != null) {
			res = visit(ctx.print());
		}
		else if(ctx.assignment() != null) {
			res = visit(ctx.assignment());
		} 
		else {
			return null;
		}
		return new StatementNode(res);
	}


	@Override
	public Node visitDecFun(DecFunContext ctx) {
		DecFunNode res;
		ArrayList<Node> args = new ArrayList<>();
		for(SimpLanPlusParser.ArgContext argCtx : ctx.arg()) {
			args.add(visit(argCtx));
		}
		if(ctx.type() != null) {
			
			res = new DecFunNode(visit(ctx.type()), new IdNode(ctx.ID().getText()), args, visit(ctx.block()));
		}
		else {
			res = new DecFunNode(null, new IdNode(ctx.ID().getText()), args, visit(ctx.block()));
		}
		return res; 
	}


	@Override 
	public Node visitType(TypeContext ctx) { 
		return new TypeNode(ctx.getText()); 
	}


	@Override
	public Node visitArg(SimpLanPlusParser.ArgContext ctx) {
		boolean var = false;
		if(ctx.children.get(0).toString().equals("var"))
			var = true;
		return new ArgNode(var,visit(ctx.type()),new IdNode(ctx.ID().getText()));
	}


	@Override 
	public Node visitAssignment(AssignmentContext ctx) { 
		return new AssignmentNode(new IdNode(ctx.ID().getText()), visit(ctx.exp())); 
	}


	@Override 
	public Node visitPrint(SimpLanPlusParser.PrintContext ctx) { 
		return new PrintNode(visit(ctx.exp())); 
	}

	@Override 
	public Node visitRet(SimpLanPlusParser.RetContext ctx) { 
		if(ctx.exp() != null) {
			return new RetNode(visit(ctx.exp())); 

		}
		return new RetNode(null);	
	}

	@Override
	public Node visitIte(SimpLanPlusParser.IteContext ctx) {	
		IteNode res;
		if(ctx.statement().size()==1) {
			res = new IteNode(visit(ctx.exp()), visitStatement(ctx.statement().get(0)),null);
		} else {
			if(ctx.statement().size()==2) {
				res = new IteNode(visit(ctx.exp()), visitStatement(ctx.statement().get(0)), visitStatement(ctx.statement().get(1)));
			} else {
				return null;
			}
		}
		return res;
	}

	@Override 
	public Node visitCall(SimpLanPlusParser.CallContext ctx) {
		if(ctx.exp().isEmpty()){
			return new CallNode(new IdNode(ctx.ID().getText()), null);
		}
		ArrayList<Node> expressions = new ArrayList<>();
		for(SimpLanPlusParser.ExpContext expCtx : ctx.exp()) {
				expressions.add(visit(expCtx));
		}
		return new CallNode(new IdNode(ctx.ID().getText()), expressions);
	}
	
	
	@Override 
	public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
		if(ctx.exp() != null) {
			return new DecVarNode((TypeNode) visit(ctx.type()), new IdNode(ctx.ID().getText()), visit(ctx.exp()));
		} else {
			return new DecVarNode((TypeNode) visit(ctx.type()), new IdNode(ctx.ID().getText()), null);
		}
	}


	@Override 
	public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) { 
		return new BaseExpNode(visit(ctx.exp())); 
	}

	
	@Override 
	public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
		return new NegExpNode(visit(ctx.exp()));
	}

	
	@Override
	public Node visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
		if(ctx.left != null && ctx.right != null && ctx.op != null) {
			return new BinExpNode(visit(ctx.left),visit(ctx.right),ctx.op.toString());
		}
		return null;
	}

	@Override
	public Node visitDerExp(SimpLanPlusParser.DerExpContext ctx) { 
		return new DerExpNode(new IdNode(ctx.ID().getText())); 
	}

	@Override
	public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx) { 
		return new NotExpNode(visit(ctx.exp())); 
	}

	@Override
	public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) { 
		return new BoolExpNode(Boolean.parseBoolean(ctx.getText())); 
	}

	
	@Override
	public Node visitValExp(SimpLanPlusParser.ValExpContext ctx) { 
		return new ValExpNode(Integer.parseInt(ctx.getText()));
	}

	@Override
	public Node visitCallExp(SimpLanPlusParser.CallExpContext ctx) { 
		return new CallExpNode(visitCall(ctx.call())); 
	}	
}
