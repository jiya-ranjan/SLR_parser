import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ParserGUI extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;

    public ParserGUI() {
        setTitle("C Lexer & Parser GUI");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inputArea = new JTextArea(10, 50);
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);

        JButton parseButton = new JButton("Parse");

        parseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String code = inputArea.getText();
                List<String> lexerTokens = Lexer.tokenize(code); // Make sure your Lexer class is implemented

                // Map lexer tokens to parser tokens exactly as given
                List<String> parserTokens = new ArrayList<>();
                for (String token : lexerTokens) {
                    if (token.startsWith("KEYWORD: int")) parserTokens.add("int");
                    else if (token.startsWith("KEYWORD: if")) parserTokens.add("if");
                    else if (token.startsWith("KEYWORD: else")) parserTokens.add("else");
                    else if (token.startsWith("KEYWORD: while")) parserTokens.add("while");
                    else if (token.startsWith("KEYWORD: printf")) parserTokens.add("printf");
                    else if (token.startsWith("IDENTIFIER:")) parserTokens.add("id");
                    else if (token.startsWith("NUMBER:")) parserTokens.add("number");
                    else if (token.startsWith("STRING:")) parserTokens.add("string");
                    else if (token.startsWith("SYMBOL: ==")) parserTokens.add("==");
                    else if (token.startsWith("SYMBOL: =")) parserTokens.add("=");
                    else if (token.startsWith("SYMBOL: ;")) parserTokens.add(";");
                    else if (token.startsWith("SYMBOL: ,")) parserTokens.add(",");
                    else if (token.startsWith("SYMBOL: (")) parserTokens.add("(");
                    else if (token.startsWith("SYMBOL: )")) parserTokens.add(")");
                    else if (token.startsWith("SYMBOL: {")) parserTokens.add("{");
                    else if (token.startsWith("SYMBOL: }")) parserTokens.add("}");
                    else if (token.startsWith("SYMBOL: <")) parserTokens.add("<");
                    else if (token.startsWith("SYMBOL: >")) parserTokens.add(">");
                    else if (token.startsWith("SYMBOL: +")) parserTokens.add("+");
                    else if (token.startsWith("SYMBOL: -")) parserTokens.add("-");
                    else if (token.startsWith("SYMBOL: *")) parserTokens.add("*");
                    else if (token.startsWith("SYMBOL: /")) parserTokens.add("/");
                    // Add other symbols if needed
                }

                SLRParser slrParser = new SLRParser(parserTokens);
                boolean success = slrParser.parse();

                outputArea.setText("Tokens:\n" + String.join("\n", lexerTokens) +
                        "\n\nParse Result: " + (success ? "Valid Code" : "Invalid Code"));
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(inputArea), BorderLayout.NORTH);
        panel.add(parseButton, BorderLayout.CENTER);
        panel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParserGUI().setVisible(true));
    }
}

