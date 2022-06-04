package util;

import ast.Node;

public class SimpLanPlusLib {

    private static int labCount=0;

    private static int funLabCount=0;

    private static String funCode="";
    
    //[se a<:b return true con a,b :: int | bool] && [(int <:bool | bool <: int) return false]
    public static boolean isSubtype(Node a, Node b){
        return a.getClass().equals(b.getClass());
    }
    //funzioni che servono per la code generation
    public static String freshLabel() {
        return "label"+(labCount++);
    }

    public static String freshFunLabel() {
        return "function"+(funLabCount++);
    }

    public static void putCode(String c) {
        funCode+="\n"+c; //aggiunge una linea vuota di separazione prima di funzione
    }

    public static String getCode() {
        return funCode;
    }
}
