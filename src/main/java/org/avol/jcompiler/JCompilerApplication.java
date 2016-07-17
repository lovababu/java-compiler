package org.avol.jcompiler;

import org.avol.jcompiler.app.config.RestConfig;
import org.avol.jcompiler.app.filters.CorsFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.Filter;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = {"org.avol.jcompiler"})
public class JCompilerApplication {

	@Bean
	public RestConfig restConfig() {
		return new RestConfig();
	}

	@Bean
	public Filter corsFilter() {
		return new CorsFilter();
	}

	@Bean
	public javax.validation.Validator validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		factory.setSessionTimeout((int) TimeUnit.MINUTES.toSeconds(30));
		factory.addServerCustomizers(new JettyServerCustomizer(){

			@Override
			public void customize(Server server) {
				QueuedThreadPool threadPool = (QueuedThreadPool) server.getThreadPool();
				threadPool.setIdleTimeout((int) TimeUnit.MINUTES.toSeconds(30));
				threadPool.setName("JCompiler");
				threadPool.setStopTimeout((int) TimeUnit.MINUTES.toSeconds(30));
				//other custom setting if any.
			}
		});
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(JCompilerApplication.class, args);
	}
}
