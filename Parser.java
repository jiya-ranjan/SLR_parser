
import java.util.List;

public class Parser {
    private List<String> tokens;
    private int index = 0;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
    }

    public boolean parseDeclaration() {
        if (match("KEYWORD")) {
            if (match("IDENTIFIER")) {
                while (match("SYMBOL: ,")) {
                    if (!match("IDENTIFIER")) return false;
                }
                return match("SYMBOL: ;");
            }
        }
        return false;
    }

    private boolean match(String expected) {
        if (index < tokens.size() && tokens.get(index).startsWith(expected)) {
            index++;
            return true;
        }
        return false;
    }
}
