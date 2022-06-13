package main;
import Interpreter.ExecuteVM;
import ast.SVMVisitorImpl;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;import ast.Node;
import ast.SimpLanPlusVisitorImpl;
import parser.SVMLexer;
import parser.SVMParser;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;


public class Main{
	public static void main(String[] args) throws Exception {
		//LEXICAL ANALYSIS...
		String filename = "input";
		InputStream is = new FileInputStream(filename);
		CharStream input = CharStreams.fromStream(is);
		SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
		ErrorListener listener = new ErrorListener();
		lexer.addErrorListener(listener);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SimpLanPlusParser parser = new SimpLanPlusParser(tokens);
		parser.addErrorListener(listener);
		//SYMBLE TABLE
		SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
		Node ast = visitor.visit(parser.program());
		Environment env = new Environment();		
		ArrayList<SemanticError> err = ast.checkSemantics(env); //catch the semantic errors and print them
		if(err.size() > 0) {
			System.out.println("You had: "+err.size()+" errors:");
			for(SemanticError e: err) {
				System.out.println("\t" + e);
			}
		}else{
			ast.typeCheck();
			System.out.println(ast.printer(""));

			// CODE GEN
			System.out.println("Generating code...");
			String code=ast.codeGeneration();
			BufferedWriter out = new BufferedWriter(new FileWriter(filename+".asm"));
			out.write(code + "halt" + SimpLanPlusLib.getCode());
			out.close();
			System.out.println("Code generated! Assembling and running generated code.");
			System.out.println("File: " + filename + ".asm");
			FileInputStream isASM = new FileInputStream(filename+".asm");
			ANTLRInputStream inputASM = new ANTLRInputStream(isASM);
			SVMLexer lexerASM = new SVMLexer(inputASM);
			CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
			SVMParser parserASM = new SVMParser(tokensASM);

			//parserASM.assembly(); is called in the visitorSVM

			SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
			visitorSVM.visit(parserASM.assembly());

			System.out.println("You had: "+lexerASM.lexicalErrors+" lexical errors and "+parserASM.getNumberOfSyntaxErrors()+" syntax errors.");
			if (lexerASM.lexicalErrors>0 || parserASM.getNumberOfSyntaxErrors()>0) System.exit(1);

			System.out.println("Starting Virtual Machine...");
			ExecuteVM vm = new ExecuteVM(visitorSVM.code);
			System.out.println("Output: ");
			vm.cpu();
			System.out.println("-----------------");
		}
	}
}