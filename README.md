# Application Log File Analyzer & Statistics Generator

A standalone Java application that reads an application log file line-by-line using `BufferedReader`, tallies log levels (`INFO`, `WARNING`, and `ERROR`), and calculates the distribution percentage for each level.

Built using standard Java SE libraries (`java.io`) without third-party dependencies.

---

## 📋 Features

- **Memory-Efficient Streaming**: Utilizes `BufferedReader` to process log files line-by-line, maintaining low and predictable memory usage even on large files.
- **Resource Management**: Implements Java 7+ try-with-resources to guarantee safe and automatic stream closure.
- **Case-Insensitive Parsing**: Normalizes entries with `toUpperCase()` to handle varying case conventions (`INFO`, `info`, `Warning`, etc.).
- **Log Level Aggregation**: Tracks total parsed records alongside individual counts for `INFO`, `WARNING`, and `ERROR`.
- **Percentage Distribution**: Computes the proportion of each log level with two-decimal precision using `printf`.
- **Zero-Dependency**: Runs natively on standard Java SE (JDK 8 or higher).

---

## 📁 Project Structure

```text
.
├── application.log      # Sample input log file with timestamped entries
├── Practise.java        # Core Java source code for log parsing & statistics
├── .gitignore           # Git ignore rule for compiled bytecode (*.class)
└── README.md            # Project documentation and execution instructions
```

---

## 📥 Input Data Format (`application.log`)

The application expects an input log file named `application.log` located in the application's working directory.

### Sample Contents
```text
2026-10-25 09:00:01 INFO Application started
2026-10-25 09:00:02 INFO Database connected
2026-10-25 09:00:03 WARNING Disk space is low
2026-10-25 09:00:04 ERROR Database connection failed
2026-10-25 09:00:05 INFO User logged in
2026-10-25 09:00:06 ERROR File not found
```

### Log Schema Breakdown
Each line contains three primary components:
1. **Timestamp**: `yyyy-MM-dd HH:mm:ss` (e.g., `2026-10-25 09:00:01`)
2. **Log Level**: The severity marker (`INFO`, `WARNING`, or `ERROR`)
3. **Message**: Free-form diagnostic description of the application event

---

## 📊 Statistical Calculation

The percentage for each log level is calculated using the formula:

$$\text{Percentage} = \left( \frac{\text{Log Level Count}}{\text{Total Log Entries}} \right) \times 100$$

Formatted output is rounded to two decimal places using the `%.2f%%` specifier in `System.out.printf`.

---

## 📤 Output

When executed with the sample dataset, the program outputs:

```text
===== LOG STATISTICS =====
Total log entries: 6
INFO count: 3
WARNING count: 1
ERROR count: 2
INFO percentage: 50.00%
WARNING percentage: 16.67%
ERROR percentage: 33.33%
```

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher (tested with JDK 23).

Verify your Java installation:
```bash
javac -version
java -version
```

### Compilation & Execution

1. Open your terminal in the project directory:
   ```bash
   cd "path/to/java intership"
   ```

2. Compile the Java source file:
   ```bash
   javac Practise.java
   ```

3. Run the application:
   ```bash
   java Practise
   ```

---

## ⚙️ How the Code Works

1. **File Opening**: A `FileReader` wrapped in a `BufferedReader` opens `application.log` inside a try-with-resources statement.
2. **Line-by-Line Iteration**: `br.readLine()` reads the file sequentially until it reaches the end of the file (`null`).
3. **Classification**:
   - `line.toUpperCase()` normalizes the input string.
   - Conditional statements check whether the line contains `INFO`, `WARNING`, or `ERROR`.
   - Relevant counters and `totalCount` increment accordingly.
4. **Summary & Percentage Calculation**: Computes and prints the final statistics and percentages to `System.out`.
5. **Exception Handling**: Traps and reports `IOException` if the file is missing or unreadable.

---

## 💡 Notes & Production Considerations

- **Log Field Boundary Matching**:
  The basic implementation uses `String.contains()`. In production systems, a log message might describe an error without being an error-level log (e.g., `INFO Error handler initialized successfully`). In production pipelines, consider tokenizing by delimiter (e.g., `parts[2]`) or applying regex:
  ```java
  Pattern.compile("^\\S+\\s+\\S+\\s+(INFO|WARNING|ERROR)\\b")
  ```
- **Extensible Log Levels**:
  For applications supporting additional severity levels (`DEBUG`, `TRACE`, `FATAL`), a `Map<String, Integer>` or Java `enum` can dynamically aggregate metrics without cascading `if-else` blocks.
- **Java NIO Streams**:
  In modern Java (Java 8+), processing can also be expressed concisely using `Files.lines(Path.of(filePath))` combined with Stream collectors (`Collectors.groupingBy(..., Collectors.counting())`).
