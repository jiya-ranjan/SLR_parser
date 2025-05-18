import java.util.*;

public class SLRParser {
    private List<String> tokens;
    private int index = 0;

    public SLRParser(List<String> tokens) {
        this.tokens = tokens;
    }

    public boolean parse() {
        try {
            parseProgram();
            return index == tokens.size();
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
        while (peek().equals(",")) {
            match(",");
            match("id");
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
        switch (t) {
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
                if (peek().equals(",")) {
                    match(",");
                    if (peek().equals("&")) match("&");
                    match("id");
                }
                match(")");
                match(";");
                break;
            case "id":
                parseAssignment();
                break;
            default:
                throw new Exception("Unexpected token: " + t);
        }
    }

    private void parseAssignment() throws Exception {
        match("id");
        match("=");
        parseExpr();
        match(";");
    }

    private void parseExpr() throws Exception {
        parseTerm();
        while (peek().equals("+") || peek().equals("-")) {
            match(peek());
            parseTerm();
        }
    }

    private void parseTerm() throws Exception {
        String t = peek();
        if (t.equals("id") || t.equals("number")) {
            match(t);
        } else {
            throw new Exception("Expected id or number in expression");
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
