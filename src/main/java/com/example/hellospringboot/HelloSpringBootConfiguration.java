package com.example.hellospringboot;

import io.jaegertracing.internal.samplers.ConstSampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HelloSpringBootConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HelloSpringBootConfiguration.class);

    private final String applicationName;
    private final String jaegerUdpSenderHost;
    private final int jaegerUdpSenderPort;
    private final boolean jaegerLogSpans;

    public HelloSpringBootConfiguration(@Value("${spring.application.name:hello-spring-boot}") String applicationName,
                                        @Value("${opentracing.jaeger.udp-sender.host:localhost}") String jaegerUdpSenderHost,
                                        @Value("${opentracing.jaeger.udp-sender.port:6831}") int jaegerUdpSenderPort,
                                        @Value("${opentracing.jaeger.log-spans:true}") boolean jaegerLogSpans) {
        log.debug("spring.application.name: {}", applicationName);
        log.debug("opentracing.jaeger.udp-sender.host: {}", jaegerUdpSenderHost);
        log.debug("opentracing.jaeger.udp-sender.port: {}", jaegerUdpSenderPort);
        log.debug("opentracing.jaeger.log-spans: {}", jaegerLogSpans);
        this.applicationName = applicationName;
        this.jaegerUdpSenderHost = jaegerUdpSenderHost;
        this.jaegerUdpSenderPort = jaegerUdpSenderPort;
        this.jaegerLogSpans = jaegerLogSpans;
    }

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv()
                .withSender(io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
                        .withAgentHost(jaegerUdpSenderHost)
                        .withAgentPort(jaegerUdpSenderPort))
                .withLogSpans(jaegerLogSpans);
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(applicationName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        return config.getTracer();
    }
}
