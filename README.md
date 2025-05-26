# 🧠 C SLR Parser GUI

This project is a simple **C language parser** built with **Java Swing** that performs:

- **Lexical Analysis** (Lexer)
- **Parsing** (SLR-style)
- **AST Visualization**
- Validates basic structure of C code like `#include`, `main()` function, and semicolon usage.

---

## 📁 Project Structure

```
SLR_parser/
├── ParserGUI.java     # Main GUI class (Swing-based)
├── Lexer.java         # Lexical analyzer: tokenizes C code
├── SLRParser.java     # Parser: validates token list using rules
```

---

## 🛠 Features

- 📥 **File Upload**: Select and load a `.c` file
- 🔍 **Lexical Analysis**: Extracts tokens and types
- 🧾 **Parsing**: Validates syntax structure of C code
- 🌳 **AST Output**: Displays a basic abstract syntax tree (hierarchical layout)
- ❌ Detects errors like:
  - Missing `#include` or headers
  - Improper `main()` syntax
  - Mismatched `{}` braces
  - ❗ Missing semicolons after declarations and statements

---

## ✅ Example Input (Valid)

```c
#include<stdio.h>
int main() {
    int a;
    printf("hello");
}
```

---

## ❌ Example Input (Invalid)

```c
#include<stdio.h>
int main() {
    int a           // ❌ Missing semicolon
    printf("hello");
}
```

---

## 🚀 How to Run

1. **Compile All Java Files:**
   ```bash
   javac ParserGUI.java Lexer.java SLRParser.java
   ```

2. **Run the GUI:**
   ```bash
   java ParserGUI
   ```

---

## 📸 Screenshots

*(Add screenshots here of GUI with lexer table, parser output, and AST output)*

---

## 🤖 Technologies Used

- Java Swing (GUI)
- Java I/O (File reading)
- Custom Lexer and SLR-style Parser logic

