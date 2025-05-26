import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static List<String[]> tokenize(String code) {
        List<String[]> tokens = new ArrayList<>();

        String tokenPatterns =
                "(#include)" +                // preprocessor
                "|<[^>]+>" +                 // header
                "|int|return|void|printf|main|if|else|while|for" + // keywords
                "|[a-zA-Z_][a-zA-Z0-9_]*" + // identifiers
                "|[0-9]+" +                  // numbers
                "|\"[^\"]*\"" +              // string literals
                "|==|!=|<=|>=|&&|\\|\\|" +  // multi-char operators
                "|[{}()\\[\\];,<>+\\-/*=]" ; // single-char symbols

        Pattern pattern = Pattern.compile(tokenPatterns);
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            String token = matcher.group();

            if (token.equals("#include")) {
                tokens.add(new String[]{token, "preprocessor"});
            } else if (token.matches("<[^>]+>")) {
                tokens.add(new String[]{token, "header"});
            } else if (token.matches("int|return|void|printf|main|if|else|while|for")) {
                tokens.add(new String[]{token, "keyword"});
            } else if (token.matches("\"[^\"]*\"")) {
                tokens.add(new String[]{token, "string"});
            } else if (token.matches("[0-9]+")) {
                tokens.add(new String[]{token, "number"});
            } else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                tokens.add(new String[]{token, "identifier"});
            } else {
                tokens.add(new String[]{token, "symbol"});
            }
        }
        return tokens;
    }
}
