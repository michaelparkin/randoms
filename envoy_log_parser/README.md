# Envoy access log processor

Experimental [Apache Beam](https://beam.apache.org/) project to process Envoy access logs.

#### Test

```bash
$ ./gradlew clean runBatchEnvoyLogParserTest
```

#### Links:

* Envoy [default access log format](https://www.envoyproxy.io/docs/envoy/latest/configuration/observability/access_log/usage#default-format-string)
* Description of [x-envoy-upstream-service-time](https://www.envoyproxy.io/docs/envoy/latest/configuration/http/http_filters/router_filter#x-envoy-upstream-service-time)
