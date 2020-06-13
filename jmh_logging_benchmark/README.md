# jmh logging benchmark

See http://www.slf4j.org/faq.html#logging_performance for background. 

JMH tests to compare the performance of four different methods of constructing slf4j log statements

1. String concatenation (string + string + ...)
2. [String format](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#format-java.lang.String-java.lang.Object...-)
3. Slf4j parameterized logging using `Object[]` (old slf4j method)
4. Slf4j parameterized logging

... and an extra test to check the overhead of checking `logger.isDebugEnabled()` before logging. 

#### Running

```bash
$ ./gradlew --no-daemon --console plain --parallel clean jmh
```

#### Results

All tests:

 * MacBook Pro (Retina, 15-inch, Mid 2015)
 * 2.2 GHz Intel Core i7
 * 16 GB 1600 MHz DDR3
 * OSX 10.13.6
 * VM version: JDK 1.8.0_144, Java HotSpot(TM) 64-Bit Server VM, 25.144-b01
 * JMH version: 1.21
 * Warmup: 5 iterations, 10 s each
 * Measurement: 5 iterations, 10 s each
 * Threads: 1 thread
 * Benchmark mode: Throughput, ops/time


##### [Debug logging](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java)
 
| Benchmark                     | Mode  | Cnt | Score           | Error           | Units | 
|-------------------------------|:-----:|:---:|----------------:|----------------:|:-----:|
| [testDebugIfDebugEnabled](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java#L52)       | thrpt |  25 | 396,735,052.262 | ± 1,420,391.770 | ops/s |
| [testDebugObjectArrayArguments](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java#L38) | thrpt |  25 | 367,849,446.630 | ± 1,612,988.284 | ops/s |
| [testDebugVariableArguments](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java#L45)    | thrpt |  25 | 352,710,501.124 | ± 5,149,100.790 | ops/s |
| [testDebugConcatenatingStrings](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java#L24) | thrpt |  25 |  30,131,784.070 | ±   251,416.081 | ops/s |
| [testDebugStringFormat](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/DebugLoggingBenchmark.java#L31)         | thrpt |  25 |     799,986.036 | ±     6,873.380 | ops/s |


##### [String formatting](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/StringFormatBenchmark.java)


| Benchmark               | Mode  | Cnt | Score         | Error         | Units | 
|-------------------------|:-----:|:---:|--------------:|--------------:|:-----:|
| [testStringConcatenation](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/StringFormatBenchmark.java#L43) | thrpt |  25 | 4,500,727.857 | ± 126,882.198 | ops/s |
| [testStringBuilder](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/StringFormatBenchmark.java#L30)       | thrpt |  25 | 4,390,182.935 | ±  20,069.493 | ops/s |
| [testStringFormat](https://github.com/michaelparkin/randoms/blob/master/jmh_logging_benchmark/src/jmh/java/michaelparkin/StringFormatBenchmark.java#L21)        | thrpt |  25 |   507,248.360 | ±    2654.033 | ops/s |
