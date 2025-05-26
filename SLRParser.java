import java.util.List;

public class SLRParser {

    public static boolean parse(List<String[]> tokens) {
        boolean foundInclude = false;
        boolean foundHeader = false;
        boolean foundMain = false;
        int braceCount = 0;

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i)[0];
            String type = tokens.get(i)[1];

            if (token.equals("#include")) {
                foundInclude = true;
            } else if (type.equals("header")) {
                foundHeader = true;
            } else if (token.equals("main")) {
                foundMain = true;
                // Check for main()
                if (i + 2 >= tokens.size() || 
                    !tokens.get(i + 1)[0].equals("(") || 
                    !tokens.get(i + 2)[0].equals(")")) {
                    System.out.println("Error: main() syntax incorrect");
                    return false;
                }
            } else if (token.equals("{")) {
                braceCount++;
            } else if (token.equals("}")) {
                braceCount--;
            }

            // SEMICOLON CHECK
            // If 'int' or 'printf' is found, expect a semicolon at the end
            if (token.equals("int")) {
                boolean foundSemicolon = false;
                for (int j = i + 1; j < tokens.size(); j++) {
                    if (tokens.get(j)[0].equals(";")) {
                        foundSemicolon = true;
                        break;
                    }
                    // Stop if block ends
                    if (tokens.get(j)[0].equals("}")) break;
                }
                if (!foundSemicolon) {
                    System.out.println("Error: Missing semicolon after variable declaration");
                    return false;
                }
            }

            if (token.equals("printf")) {
                boolean foundSemicolon = false;
                for (int j = i + 1; j < tokens.size(); j++) {
                    if (tokens.get(j)[0].equals(";")) {
                        foundSemicolon = true;
                        break;
                    }
                    if (tokens.get(j)[0].equals("}")) break;
                }
                if (!foundSemicolon) {
                    System.out.println("Error: Missing semicolon after printf");
                    return false;
                }
            }
        }

        // Final structural checks
        System.out.println("foundInclude: " + foundInclude);
        System.out.println("foundHeader: " + foundHeader);
        System.out.println("foundMain: " + foundMain);
        System.out.println("braceCount: " + braceCount);

        if (!foundInclude || !foundHeader) {
            System.out.println("Error: Missing #include or header");
            return false;
        }
        if (!foundMain) {
            System.out.println("Error: Missing main function");
            return false;
        }
        if (braceCount != 0) {
            System.out.println("Error: Braces mismatch");
            return false;
        }

        return true;
    }
}
