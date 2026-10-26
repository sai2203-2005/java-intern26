# Java Multithreading Performance Comparison

A demonstration and benchmark comparing single-threaded and multithreaded execution in Java by calculating the sum of integers from 1 to 100,000,000.

---

## 📌 Overview

This project benchmarks CPU-bound work in Java to demonstrate how parallel task partitioning across multiple threads affects execution time and throughput on multicore processors.

- **Range:** $1$ to $100{,}000{,}000$ ($N = 10^8$)
- **Threads:** 4 worker threads
- **Target Sum:** $\frac{N(N+1)}{2} = 5{,}000{,}000{,}050{,}000{,}000$

---

## ⚙️ Work Partitioning

The range of 100 million numbers is divided into four equal chunks:

```text
Main Thread
  │
  ├── Spawns Thread 1 ──> [1         to  25,000,000] ──> Partial Sum 1
  ├── Spawns Thread 2 ──> [25,000,001 to  50,000,000] ──> Partial Sum 2
  ├── Spawns Thread 3 ──> [50,000,001 to  75,000,000] ──> Partial Sum 3
  └── Spawns Thread 4 ──> [75,000,001 to 100,000,000] ──> Partial Sum 4
  │
  ├── thread.join() on all workers
  └── Combine partial sums ──> Final Sum
```

---

## 🔑 Key Java Concepts

| Code / Concept | Purpose |
| :--- | :--- |
| `new Thread(...)` | Instantiates a new worker thread. |
| `thread.start()` | Schedules and begins execution of the worker thread asynchronously. |
| `thread.join()` | Blocks the calling thread (main thread) until the target thread finishes execution. |
| `System.nanoTime()` | High-resolution time-source for measuring elapsed execution time. |
| `results[index]` | Stores each thread's independent partial sum to avoid shared mutable state and lock contention. |

### Concurrency Safety Notes
- **Why use `join()`?** Without `join()`, the main thread might aggregate the results array prematurely before workers complete their calculations.
- **Why is the `results` array thread-safe here?** Each thread writes exclusively to a dedicated array index ($0, 1, 2, 3$). The main thread accesses the array only after all worker threads have completed (`join()`), creating a *happens-before* relationship without requiring locks or synchronized blocks.

---

## 🚀 Running the Project

### Prerequisites
- Java Development Kit (JDK 8 or higher) installed and configured in your `PATH`.

### Compile & Execute

```bash
# Compile
javac Practise.java

# Run
java Practise
```

---

## 📊 Sample Output

```text
Single Thread Result: 5000000050000000
Single Thread Time: 43.85 ms

Multithreaded Result: 5000000050000000
Multithreaded Time: 30.11 ms

Speedup: 1.46x
Results Match: true
```

### Performance & Speedup Formula

$$\text{Speedup} = \frac{\text{Single-Thread Time}}{\text{Multithreaded Time}}$$

---

## 💡 Key Observations & Next Steps

1. **Thread Creation Overhead**: Creating raw `Thread` instances incurs OS-level overhead. For smaller or very short tasks (e.g., $<50\text{ ms}$), spawning overhead and context switching can offset parallel gains.
2. **JVM Warmup & JIT**: Java's Just-In-Time (JIT) compiler optimizes hot code paths during runtime. For precise benchmarking, multiple warmup iterations or tools like [JMH (Java Microbenchmark Harness)](https://github.com/openjdk/jmh) are recommended.
3. **Thread Pools (`ExecutorService`)**: In production systems, reuse threads using `Executors.newFixedThreadPool()` or Java's `ForkJoinPool` / parallel streams (`LongStream.rangeClosed(1, N).parallel().sum()`) to eliminate thread creation overhead.
4. **Mathematical Optimization**: For arithmetic sequences, Gauss's formula $\frac{N(N+1)}{2}$ computes the sum in $\mathcal{O}(1)$ time with zero loops.
