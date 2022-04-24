package util;
import java.util.ArrayList;
import java.util.HashMap;

import ast.Node;
import ast.STentry;
public class Environment {

    public ArrayList<HashMap<String,STentry>>  symTable = new ArrayList<>();
    public int nestingLevel = -1;
    public int offset = 0;

    public void enterScope() {
        nestingLevel++;
        HashMap<String, STentry> hm = new HashMap<>();
        symTable.add(hm);
    }

    public void exitScope() {
        symTable.remove(nestingLevel--);
    }

    public HashMap<String, STentry> getScope(int nl) {
        return symTable.get(nl);
    }

    public HashMap<String, STentry> getCurrentScope() {
        return getScope(getNestingLevel());
    }

    public STentry addEntry(Node type, String id) {
        STentry entry;
        if (type != null)
            entry = new STentry(nestingLevel, type, offset--);
        else
            entry = new STentry(nestingLevel, offset--);
        HashMap<String, STentry> hm = getCurrentScope();

        return hm.put(id, entry);
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public STentry checkDeclaration(String id, int nl) {
        HashMap<String, STentry> hm = getScope(nl);
        return hm.get(id);
    }
}
