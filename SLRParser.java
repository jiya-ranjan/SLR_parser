import java.util.*;

public class SLRParser {
    private List<String> tokens;
    private int index = 0;

    // Example grammar terminals and non-terminals:
    // For simplicity, let’s say grammar supports:
    // program -> decl_list stmt_list
    // decl -> int id = number ;
    // stmt -> while ( expr ) block
    // stmt -> if ( expr ) block else block
    // stmt -> printf ( string ) ;
    // expr -> id < number | id == number
    // block -> { stmt_list }
    // stmt_list -> stmt stmt_list | epsilon

    // For real SLR, you need states and action/goto tables.
    // Here we simulate parsing with recursive descent for demonstration.

    public SLRParser(List<String> tokens) {
        this.tokens = tokens;
    }

    public boolean parse() {
        try {
            parseProgram();
            if (index == tokens.size()) return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void parseProgram() throws Exception {
        parseDeclList();
        parseStmtList();
    }

    private void parseDeclList() throws Exception {
        while (peek().equals("int")) {
            parseDecl();
        }
    }

    private void parseDecl() throws Exception {
        match("int");
        match("id");
        if (peek().equals("=")) {
            match("=");
            match("number");
        }
        match(";");
    }

    private void parseStmtList() throws Exception {
        while (peekIsStmtStart()) {
            parseStmt();
        }
    }

    private boolean peekIsStmtStart() {
        String t = peek();
        return t.equals("if") || t.equals("while") || t.equals("printf") || t.equals("id");
    }

    private void parseStmt() throws Exception {
        String t = peek();
        switch(t) {
            case "while":
                match("while");
                match("(");
                parseExpr();
                match(")");
                parseBlock();
                break;
            case "if":
                match("if");
                match("(");
                parseExpr();
                match(")");
                parseBlock();
                if (peek().equals("else")) {
                    match("else");
                    parseBlock();
                }
                break;
            case "printf":
                match("printf");
                match("(");
                match("string");
                match(")");
                match(";");
                break;
            case "id":
                // Could add assignment or function call stmt
                throw new Exception("id stmt not implemented");
            default:
                throw new Exception("Unexpected token: " + t);
        }
    }

    private void parseExpr() throws Exception {
        match("id");
        String op = peek();
        if (op.equals("<") || op.equals("==") || op.equals(">")) {
            match(op);
        } else {
            throw new Exception("Expected relational operator");
        }
        if (peek().equals("number") || peek().equals("id")) {
            match(peek());
        } else {
            throw new Exception("Expected number or id in expr");
        }
    }

    private void parseBlock() throws Exception {
        match("{");
        parseStmtList();
        match("}");
    }

    private String peek() {
        if (index < tokens.size()) return tokens.get(index);
        else return "";
    }

    private void match(String expected) throws Exception {
        if (index < tokens.size() && tokens.get(index).equals(expected)) {
            index++;
        } else {
            throw new Exception("Expected " + expected + " but found " + (index < tokens.size() ? tokens.get(index) : "EOF"));
        }
    }
}
