package main;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;import ast.Node;
import ast.SimpLanPlusVisitorImpl;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import util.Environment;
import util.SemanticError;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class Main{
	public static void main(String[] args) throws Exception {
		//Lexical Analyzer Ex. 1 - The first part of the function
		//extracts the lexical errors and print them on a file .txt
		String filename = "input";
		InputStream is = new FileInputStream(filename);
		CharStream input = CharStreams.fromStream(is);
		SimpLanPlusLexer lexer = new SimpLanPlusLexer(input);
		ErrorListener listener = new ErrorListener();
		lexer.addErrorListener(listener);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SimpLanPlusParser parser = new SimpLanPlusParser(tokens);
		parser.addErrorListener(listener);
		//Symbol Table Ex. 2 - The second part of the function
		//creates the Symbol table implemented as list of hash
		//(visible inside the file Environment.java)
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
			System.out.println(ast.toPrint(""));
		}
	}
}