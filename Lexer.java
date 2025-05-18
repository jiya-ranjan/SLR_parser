
// Lexer.java
import java.util.*;
import java.util.regex.*;

public class Lexer {
    private static final String KEYWORD = "\\b(int|float|char|return|if|else|for|while|printf)\\b";
    private static final String IDENTIFIER = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMBER = "\\b\\d+\\b";
    private static final String STRING = "\"[^\"]*\"";
    private static final String SYMBOL = "==|<=|>=|!=|[;,(){}\\[\\]+\\-*/=<>]";

    private static final Pattern tokenPatterns = Pattern.compile(
        String.format("(?<KEYWORD>%s)|(?<IDENTIFIER>%s)|(?<NUMBER>%s)|(?<STRING>%s)|(?<SYMBOL>%s)",
                      KEYWORD, IDENTIFIER, NUMBER, STRING, SYMBOL)
    );

    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group("KEYWORD") != null) tokens.add("KEYWORD: " + matcher.group());
            else if (matcher.group("IDENTIFIER") != null) tokens.add("IDENTIFIER: " + matcher.group());
            else if (matcher.group("NUMBER") != null) tokens.add("NUMBER: " + matcher.group());
            else if (matcher.group("STRING") != null) tokens.add("STRING: " + matcher.group());
            else if (matcher.group("SYMBOL") != null) tokens.add("SYMBOL: " + matcher.group());
        }
        return tokens;
    }
}
