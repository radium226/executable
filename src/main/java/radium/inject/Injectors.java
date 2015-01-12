package radium.inject;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
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
		return discoverInjector(Lists.<Module>newArrayList());
	}
	
	// Create an injector using modules discoverd through the Modules.discoverModules(true)
	public static Injector discoverInjector(Module module) {
		return discoverInjector(Lists.<Module>newArrayList(module));
	}
	
	public static Injector discoverInjector(List<Module> modules) {
		List<Module> discoveredModules = Modules.discoverModules(true);
		modules.addAll(discoveredModules);
		LOGGER.debug("{} module{} discovered", discoveredModules.size(), discoveredModules.size() > 1 ? "s" : "");
		Injector injector = Guice.createInjector(modules);
		return injector;
	}
	
	
	
}
