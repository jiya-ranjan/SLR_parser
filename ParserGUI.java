
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
                List<String> tokens = Lexer.tokenize(code);
                Parser parser = new Parser(tokens);
                boolean success = parser.parseDeclaration();
                outputArea.setText("Tokens:\n" + String.join("\n", tokens) +
                        "\n\nParse Result: " + (success ? "Valid Declaration" : "Invalid Declaration"));
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
