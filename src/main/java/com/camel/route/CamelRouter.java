package com.camel.route;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.MetricsComponent;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.codahale.metrics.graphite.GraphiteUDP;

@Component
public class CamelRouter{
	
	@Bean(name = MetricsComponent.METRIC_REGISTRY_NAME)
	public MetricRegistry getMetricRegistry() {
		MetricRegistry registry = new MetricRegistry();
		return registry;
	}

	@Bean(destroyMethod = "stop")
    public GraphiteReporter graphiteReporter() {
        final GraphiteSender graphite = new GraphiteUDP(new InetSocketAddress("localhost", 2018));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(getMetricRegistry()).prefixedWith("camel-spring-boot").convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build(graphite);
        reporter.start(5, TimeUnit.SECONDS);
        return reporter;
    }
	
	@Bean
    public RouteBuilder slowRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	from("timer:greet?period={{timer.period}}").transform().method("camelBean", "greet").end().to("stream:out");
            }
        };
    }
	
	@Bean
    public RouteBuilder fastRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	from("timer:greet?period=2000").routeId("fast-route").setBody().constant("Welcome to Apache Camel Application!").to("stream:out");
            }
        };
    }
	
	 @Bean
	    CamelContextConfiguration contextConfiguration() {
	        return new CamelContextConfiguration() {
	            @Override
	            public void beforeApplicationStart(CamelContext context) {
	                MetricsRoutePolicyFactory fac = new MetricsRoutePolicyFactory();
	                fac.setMetricsRegistry(getMetricRegistry());
	                context.addRoutePolicyFactory(fac);
	            }

	            @Override
	            public void afterApplicationStart(CamelContext camelContext) {
	                // noop
	            }
	        };
	    }

}
