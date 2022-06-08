package ast;

import ast.binExp.*;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;

import java.util.ArrayList;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {

    @Override public Node visitProgram(SimpLanPlusParser.ProgramContext ctx){
        ProgramNode res;
        ArrayList<Node> declarations = new ArrayList<Node>();
        ArrayList<Node> statements = new ArrayList<Node>();
        // per ogni dichiarazione del programma...
        for(SimpLanPlusParser.DeclarationContext dc: ctx.declaration()){
            declarations.add(visit(dc));
        }
        // per ogni statement del programma...
        for(SimpLanPlusParser.StatementContext sc: ctx.statement()){
            statements.add(visit(sc));
        }
        res = new ProgramNode(declarations, statements);
        return res;
    }

    @Override public Node visitBlock(SimpLanPlusParser.BlockContext ctx) {
        BlockNode res;
        ArrayList<Node> declarations = new ArrayList<Node>();
        ArrayList<Node> statements = new ArrayList<Node>();
        // per ogni dichiarazione del blocco...
        for(SimpLanPlusParser.DecVarContext dc: ctx.decVar()){
            declarations.add(visit(dc));
        }
        // per ogni statement del blocco...
        for(SimpLanPlusParser.StatementContext sc: ctx.statement()){
            statements.add(visit(sc));
        }
        res = new BlockNode(declarations, statements);
        return res;
    }

    @Override public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx){
        DeclarationNode res;
        if(ctx.decFun()!=null){
            res = new DeclarationNode(visit(ctx.decFun()));
        }
        else if(ctx.decVar()!=null){
            res = new DeclarationNode(visit(ctx.decVar()));
        }
        else{
            return null;
        }
        return res;
    }

    @Override public Node visitStatement(SimpLanPlusParser.StatementContext ctx){
        Node res;
        if(ctx.block()!=null){
            res = visit(ctx.block());
        }
        else if(ctx.call()!=null){
            res = visit(ctx.call());
        }
        else if(ctx.ite()!=null){
            res = visit(ctx.ite());
        }
        else if(ctx.ret()!=null){
            res = visit(ctx.ret());
        }
        else if(ctx.print()!=null){
            res = visit(ctx.print());
        }
        else if(ctx.assignment()!=null){
            res = visit(ctx.assignment());
        }
        else{
            return null;
        }
        return new StatementNode(res);
    }

    @Override public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx){
        DecVarNode res;
        if(ctx.exp()!=null){
            res = new DecVarNode((Node) visit(ctx.type()), new IdNode(ctx.ID().getText()), visit(ctx.exp()));
        }
        else{
            res = new DecVarNode((Node) visit(ctx.type()), new IdNode(ctx.ID().getText()));
        }

        return res;
    }

    @Override public Node visitDecFun(SimpLanPlusParser.DecFunContext ctx){
        DecFunNode res;
        ArrayList<Node> args = new ArrayList<Node>();
        for(SimpLanPlusParser.ArgContext atx: ctx.arg()){
            args.add(visit(atx));
        }
        if(ctx.type()!=null){
            res = new DecFunNode(visit(ctx.type()), new IdNode(ctx.ID().getText()), args, visit(ctx.block()));
        }
        else{
            res = new DecFunNode(null, new IdNode(ctx.ID().getText()), args, visit(ctx.block()));
        }

        return res;
    }

    @Override public Node visitType(SimpLanPlusParser.TypeContext ctx){
        
        //return new TypeNode(ctx.getText());
        if(ctx.getText().equals("int"))
            return new IntTypeNode();
        else 
            return new BoolTypeNode();
    }

    @Override public Node visitArg(SimpLanPlusParser.ArgContext ctx){
        boolean isVar = false;
        if(ctx.children.get(0).toString().equals("var")){
            isVar = true;
        }
        return new ArgNode((Node)visit(ctx.type()), new IdNode(ctx.ID().getText()), isVar);
    }

    @Override public Node visitAssignment(SimpLanPlusParser.AssignmentContext ctx){
        return new AssignmentNode(new IdNode(ctx.ID().getText()), visit(ctx.exp()));
    }

    @Override public Node visitPrint(SimpLanPlusParser.PrintContext ctx){
        return new PrintNode(visit(ctx.exp()));
    }

    @Override public Node visitRet(SimpLanPlusParser.RetContext ctx){
        if(ctx.exp()!=null){
            return new ReturnNode(visit(ctx.exp()));
        }
        return new ReturnNode(null);
    }

    @Override public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx){
        return new BaseExpNode(visit(ctx.exp()));
    }

    @Override public Node visitIte(SimpLanPlusParser.IteContext ctx){
        IteNode res;
        if(ctx.statement().size()==1){
            res = new IteNode(visit(ctx.exp()), visit(ctx.statement().get(0)));
        }
        else if(ctx.statement().size()==2){
            res = new IteNode(visit(ctx.exp()), visit(ctx.statement().get(0)), visit(ctx.statement().get(1)));
        }
        else{
            return null;
        }
        return res;
    }

    @Override public Node visitCall(SimpLanPlusParser.CallContext ctx){
        if(ctx.exp().isEmpty()){
            return new CallNode(new IdNode(ctx.ID().getText()));
        }
        ArrayList<Node> params = new ArrayList<Node>();
        for(SimpLanPlusParser.ExpContext ex: ctx.exp()){
            params.add(visit(ex));
        }
        return new CallNode(new IdNode(ctx.ID().getText()), params);
    }

    @Override public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx){
        return new NegExpNode(visit(ctx.exp()));
    }

    @Override public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx){
        return new NotExpNode(visit(ctx.exp()));
    }

    @Override public Node visitDerExp(SimpLanPlusParser.DerExpContext ctx){
        return new DerExpNode(new IdNode(ctx.ID().getText()));
    }

    @Override public Node visitBinExp(SimpLanPlusParser.BinExpContext ctx){
        Node left = (Node) visit(ctx.left);
        Node right = (Node) visit(ctx.right);
        String op  = ctx.op.getText();
        
        switch(op){
            case "*":
                return new BinExpMulNode(left, right, op);
            case "/":
                return new BinExpDivNode(left, right, op);
            case "+":
                return new BinExpPlusNode(left, right, op);
            case "-":
                return new BinExpMinNode(left, right, op);
            case "<":
                return new BinExpLessThanNode(left, right, op);
            case "<=":
                return new BinExpLessEqNode(left, right, op);
            case ">":
                return new BinExpGreatThanNode(left, right, op);
            case ">=":
                return new BinExpGreatEqNode(left, right, op);
            case "==":
                return new BinExpEqNode(left, right, op);
            case "!=":
                return new BinExpNotEqNode(left, right, op);
            case "&&":
                return new BinExpAndNode(left, right, op);
            case "||":
                return new BinExpOrNode(left, right, op);
            default:
                throw new IllegalArgumentException("Operator `" + ctx.op.getText() + "` undefined");
            
        }
        //return new BinExpNode(ctx.op.getText(), visit(ctx.left), visit(ctx.right));
    }

    @Override public Node visitCallExp(SimpLanPlusParser.CallExpContext ctx){
        return new CallExpNode(visit(ctx.call()));
    }

    @Override public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx){
        return new BoolExpNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override public Node visitValExp(SimpLanPlusParser.ValExpContext ctx){
        return new ValExpNode(Integer.parseInt(ctx.getText()));
    }


}
