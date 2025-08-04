
---

# Java TCP Server: Single-threaded vs Multithreaded (ThreadPool)

This project demonstrates and benchmarks two Java TCP server implementations:

- **Single-threaded server**: Handles one client connection at a time.
- **Multithreaded server**: Uses a `FixedThreadPool` to handle multiple clients concurrently.

The performance of both servers is tested and compared using **Apache JMeter**, focusing on metrics like throughput, latency, and error rate.

---

## 📌 Features

- Blocking I/O socket programming using `java.net.ServerSocket` and `Socket`.
- Clean separation of concerns for server and client handler logic.
- Graceful exception handling and shutdown.
- JMeter-compatible TCP server communication model.
- Real-world comparison between sequential and concurrent request handling.

---

---

## 🧪 JMeter Test Setup

**Sampler Configuration:**
- **TCP Sampler**
- Server: `localhost`
- Port: `8080` (single-threaded) or `8010` (multithreaded)
- Text to send: `Hello from JMeter\n`
- End of line (EOL) byte: `10`
- Close connection: ✅ checked
- Re-use connection: ❌ unchecked

**Load Profile:**
- Number of threads: 1000
- Ramp-up: 10 seconds
- Loop count: 1 (or more depending on scenario)

---

## 📊 Performance Comparison

| Metric               | Single-threaded Server | Multithreaded Server |
|----------------------|------------------------|-----------------------|
| Avg Latency          | High                   | Low                  |
| Max Latency          | Very High              | Moderate             |
| Throughput           | Low                    | High                 |
| Error Rate           | ~100% under load       | 0%                   |
| Concurrency Handling | One client at a time   | Multiple via threads |

---

## 🚀 How to Run

### 1. Compile the code
```bash
javac SingleThreadedServer/Server.java
javac MultiThreadedServer/*.java
````

### 2. Run the server

**Single-threaded:**

```bash
java SingleThreadedServer.Server
```

**Multithreaded:**

```bash
java MultiThreadedServer.Server
```

Make sure the port (`8080` or `8010`) matches the one configured in your JMeter test plan.

---

## 🔧 Technologies Used

* Java 8+
* Apache JMeter
* Java Sockets (TCP)
* `ExecutorService` and `FixedThreadPool`

---

## 🧠 Key Learnings

* Multithreaded server architecture dramatically improves responsiveness under load.
* Thread pools help control resource usage while scaling.
* Blocking I/O models are simple but can become bottlenecks without concurrency.

---

## 📚 References

* [Java Docs – java.util.concurrent](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html)
* [Apache JMeter TCP Sampler](https://jmeter.apache.org/usermanual/component_reference.html#TCP_Sampler)
* [Java Concurrency in Practice](https://jcip.net/)

---
