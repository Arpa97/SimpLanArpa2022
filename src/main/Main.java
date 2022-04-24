package main;
import ast.Node;
import ast.SimpLanPlusVisitorImpl;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import util.Environment;
import util.SemanticError;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename = "src/input";
        String out = "src/log.log";
        String out2 = "src/logTwo.log";
        CharStream inputFile = CharStreams.fromFileName(filename);
        //Lexer Creator
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(inputFile);
        //using custom error handler
        ErrorHandlerSimpLan handler = new ErrorHandlerSimpLan(out);
            //changing error listener/handler
        lexer.removeErrorListeners();
        lexer.addErrorListener(handler);

        //Getting Tokens
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        //creating the parser
        SimpLanPlusParser parser = new SimpLanPlusParser(tokenStream);
        //changing error handler
        parser.removeErrorListeners();
        parser.addErrorListener(handler);

        SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();

        System.out.println("Starting syntax analysis...\n");
        //Instantiating the Abstract Syntax Tree
        Node ast = visitor.visit(parser.program());

        //handler is fulled during the tree parse
        if (!handler.err_list.isEmpty()){
            System.out.println("SYNTAX ERROR FOUND! Check the logfile\n");
            return;
        }

        System.out.println("No syntax error found\n");

        //starting semantic analysis
        System.out.println("Starting Semantic Analysis\n\n");
        //getting a new environment
        Environment env = new Environment();
        //calling check semantics. Err will store all the errors found by check semantics
        ArrayList<SemanticError> err = ast.checkSemantics(env);

        //in each node. Called recursively till leaf is found. returns information about the analyzed node
        //needed to fill err with errors (if found)
        ast.Analyze();
        if(err!=null && err.size()>0) {
            System.out.println("SEMANTIC ERROR FOUND! Check the logfile\n");
            BufferedWriter wr = new BufferedWriter(new FileWriter(out2));
            for (SemanticError e : err) {
                System.out.println(e);
                wr.write(e.toString() + "\n");
            }
            wr.close();
            return;
        }
        System.out.println("Semantic is Correct");
        return;
    }

}

