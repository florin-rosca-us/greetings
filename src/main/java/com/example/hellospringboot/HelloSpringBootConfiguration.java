package com.example.hellospringboot;

import io.jaegertracing.internal.samplers.ConstSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloSpringBootConfiguration {

    // TODO: The values should be read from application.properties
    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv()
                .withSender(io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
                        .withAgentHost("jaeger")
                        .withAgentPort(6831))
                .withLogSpans(true);
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("hello-spring-boot")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        return config.getTracer();
    }
}
