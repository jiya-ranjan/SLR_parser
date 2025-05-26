import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;

public class ParserGUI extends JFrame {
    private JTextArea codeArea;
    private JTable lexerTable;
    private JTextArea parserOutput;
    private JTextArea astOutput;

    public ParserGUI() {
        setTitle("C Parser GUI");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panels
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        codeArea = new JTextArea(15, 60);
        JScrollPane codeScroll = new JScrollPane(codeArea);
        topPanel.add(new JLabel("C Code:"), BorderLayout.NORTH);
        topPanel.add(codeScroll, BorderLayout.CENTER);

        JButton uploadButton = new JButton("Upload C File");
        JButton runButton = new JButton("Run Parser");
        buttonPanel.add(uploadButton);
        buttonPanel.add(runButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Lexer Table
        lexerTable = new JTable(new DefaultTableModel(new Object[]{"Token", "Type"}, 0));
        JScrollPane lexerScroll = new JScrollPane(lexerTable);
        lexerScroll.setBorder(BorderFactory.createTitledBorder("Lexer Output"));

        // Parser Output
        parserOutput = new JTextArea(8, 30);
        parserOutput.setEditable(false);
        JScrollPane parserScroll = new JScrollPane(parserOutput);
        parserScroll.setBorder(BorderFactory.createTitledBorder("Parser Output"));

        // AST Output
        astOutput = new JTextArea(8, 30);
        astOutput.setEditable(false);
        JScrollPane astScroll = new JScrollPane(astOutput);
        astScroll.setBorder(BorderFactory.createTitledBorder("Abstract Syntax Tree"));

        JSplitPane bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lexerScroll, parserScroll);
        bottomPane.setResizeWeight(0.5);
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, bottomPane, astScroll);
        mainSplit.setResizeWeight(0.5);
        add(mainSplit, BorderLayout.CENTER);

        // Actions
        uploadButton.addActionListener(e -> chooseFile());
        runButton.addActionListener(e -> runParser());

        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                codeArea.setText(content);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to read file");
            }
        }
    }

    private void runParser() {
        String code = codeArea.getText();
        List<String[]> tokens = Lexer.tokenize(code);
        updateLexerTable(tokens);

        boolean isValid = SLRParser.parse(tokens);
        parserOutput.setText(isValid ? "Parse Result: ✅ Valid C Code" : "Parse Result: ❌ Invalid C Code");

        astOutput.setText(generateAST(code));
    }

    private void updateLexerTable(List<String[]> tokens) {
        DefaultTableModel model = (DefaultTableModel) lexerTable.getModel();
        model.setRowCount(0);
        for (String[] token : tokens) {
            model.addRow(token);
        }
    }

    private String generateAST(String code) {
        StringBuilder ast = new StringBuilder();
        ast.append("Program\n");
        if (code.contains("int main")) {
            ast.append("└── main()\n    └── Body\n");
            if (code.contains("int ")) ast.append("        └── Variable Declaration\n");
            if (code.contains("printf")) ast.append("        └── Function Call: printf\n");
        } else {
            ast.append("└── [Missing main() function]\n");
        }
        return ast.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParserGUI::new);
    }
}
