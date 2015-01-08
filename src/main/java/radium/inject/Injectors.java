package radium.inject;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

final public class Injectors {

	final private static Logger LOGGER = LoggerFactory.getLogger(Injectors.class);
	
	private Injectors() {
		super();
	}
	
	// Create an injector using modules discoverd through the Modules.discoverModules(true)
	public static Injector discoverInjector() {
		List<Module> discoveredModules = Modules.discoverModules(true);
		LOGGER.debug("{} module{} discovered", discoveredModules.size(), discoveredModules.size() > 1 ? "s" : "");
		Injector injector = Guice.createInjector(discoveredModules);
		return injector;
	}
	
	
	
}
