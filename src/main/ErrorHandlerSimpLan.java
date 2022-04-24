package main;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

    public class ErrorHandlerSimpLan extends BaseErrorListener {
        private  String filename;
        private BufferedWriter writer;

        ArrayList<String> err_list;

        public ErrorHandlerSimpLan(String filename)throws IOException {
            this.filename = filename;
            try{
                writer=new BufferedWriter(new FileWriter(this.filename,false));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.err_list = new ArrayList<String>();
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            err_list.add("ERROR FOUND AT LINE "+line+" AT POSITION "+charPositionInLine+ "\n"+ msg);
            try{
                writer=new BufferedWriter(new FileWriter(filename,true));
                writer.write("ERROR FOUND AT LINE "+line+" AT POSITION "+charPositionInLine +"\n"+ msg +"\n");
                close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        public void close(){
            try {
             writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

