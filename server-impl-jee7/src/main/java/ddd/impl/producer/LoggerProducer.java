package ddd.impl.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LoggerProducer {

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		Class<?> type = injectionPoint.getMember().getDeclaringClass();
		Logger logger = LoggerFactory.getLogger(type);
		return logger;
	}
}
