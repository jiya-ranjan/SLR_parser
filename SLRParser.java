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
            System.err.println("Parse error: " + e.getMessage());
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

    // ✅ Enhanced Declaration Parsing (with initialization)
    private void parseDecl() throws Exception {
        match("int");
        match("id");
        if (peek().equals("=")) {
            match("=");
            parseExpr();
        }
        while (peek().equals(",")) {
            match(",");
            match("id");
            if (peek().equals("=")) {
                match("=");
                parseExpr();
            }
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
        return t.equals("if") || t.equals("while") || t.equals("printf") || t.equals("id") || t.equals("return");
    }

    private void parseStmt() throws Exception {
        switch (peek()) {
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
            case "return":
                parseReturnStmt();
                break;
            case "id":
                parseAssignment();
                break;
            default:
                throw new Exception("Unexpected token in statement: " + peek());
        }
    }

    private void parseAssignment() throws Exception {
        match("id");
        match("=");
        parseExpr();
        match(";");
    }

    // ✅ Enhanced Expression Parsing
    private void parseExpr() throws Exception {
        parseLogicalOr();
    }

    private void parseLogicalOr() throws Exception {
        parseLogicalAnd();
        while (peek().equals("||")) {
            match("||");
            parseLogicalAnd();
        }
    }

    private void parseLogicalAnd() throws Exception {
        parseEquality();
        while (peek().equals("&&")) {
            match("&&");
            parseEquality();
        }
    }

    private void parseEquality() throws Exception {
        parseRelational();
        while (peek().equals("==") || peek().equals("!=")) {
            match(peek());
            parseRelational();
        }
    }

    private void parseRelational() throws Exception {
        parseAdditive();
        while (Arrays.asList("<", "<=", ">", ">=").contains(peek())) {
            match(peek());
            parseAdditive();
        }
    }

    private void parseAdditive() throws Exception {
        parseTerm();
        while (peek().equals("+") || peek().equals("-")) {
            match(peek());
            parseTerm();
        }
    }

    private void parseTerm() throws Exception {
        parseFactor();
        while (peek().equals("*") || peek().equals("/")) {
            match(peek());
            parseFactor();
        }
    }

    private void parseFactor() throws Exception {
        if (peek().equals("(")) {
            match("(");
            parseExpr();
            match(")");
        } else if (peek().equals("id")) {
            match("id");
            if (peek().equals("(")) {
                parseFunctionCall();
            }
        } else if (peek().equals("number")) {
            match("number");
        } else {
            throw new Exception("Expected id, number, or expression in parentheses.");
        }
    }

    private void parseFunctionCall() throws Exception {
        match("(");
        if (!peek().equals(")")) {
            parseExpr();
            while (peek().equals(",")) {
                match(",");
                parseExpr();
            }
        }
        match(")");
    }

    private void parseReturnStmt() throws Exception {
        match("return");
        parseExpr();
        match(";");
    }

    private void parseBlock() throws Exception {
        match("{");
        parseStmtList();
        match("}");
    }

    private String peek() {
        return index < tokens.size() ? tokens.get(index) : "";
    }

    private void match(String expected) throws Exception {
        if (index < tokens.size() && tokens.get(index).equals(expected)) {
            index++;
        } else {
            throw new Exception("Expected '" + expected + "' but found '" +
                (index < tokens.size() ? tokens.get(index) : "EOF") + "'");
        }
    }
}

