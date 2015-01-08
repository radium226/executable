package radium.inject;

import static com.google.common.collect.FluentIterable.from;

import java.lang.reflect.Modifier;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.google.inject.Module;

final public class Modules {
	
	final private static Logger LOGGER = LoggerFactory.getLogger(Modules.class);
	
	private Modules() {
		super();
	}
	
	public static List<Module> discoverModules() {
		return discoverModules(false);
	}
	
	public static List<Module> discoverModules(boolean injectOnly) {
		Predicate<Class <? extends Module>> injectOnlyAsPredicate = injectOnly ? Predicates.<Class <? extends Module>>alwaysTrue() : Predicates.<Class <? extends Module>>alwaysFalse();
		Reflections reflections = new Reflections(
				"", 
				new SubTypesScanner(), 
				new TypeAnnotationsScanner(), 
				ClasspathHelper.forJavaClassPath()
			);
		
		List<Module> modules = from(Sets.intersection(reflections.getSubTypesOf(Module.class), reflections.getTypesAnnotatedWith(Discoverable.class)))
				.filter(new Predicate<Class<? extends Module>>() {
		
					@Override
					public boolean apply(Class<? extends Module> moduleClass) {
						return isInstanciable(moduleClass);
					}
		        	
		        })
		        .filter(Predicates.or(Predicates.not(injectOnlyAsPredicate), Predicates.and(injectOnlyAsPredicate, new Predicate<Class<? extends Module>>() {

					@Override
					public boolean apply(Class<? extends Module> moduleClass) {
						return moduleClass.getAnnotation(Discoverable.class).inject();
					}
		        	
		        })))
		        .transform(new Function<Class<? extends Module>, Module>() {
		
					@Override
					public Module apply(Class<? extends Module> moduleClass) {
						Module module = null;
						String moduleClassName = moduleClass.getSimpleName();
						LOGGER.debug("Tyring to instanciate {}", moduleClassName);
						try {
							module = moduleClass.newInstance();
						} catch (InstantiationException e) {
							LOGGER.warn("Unable to instanciate module {}", moduleClassName, e);
						} catch (IllegalAccessException e) {
							LOGGER.warn("Unable to instanciate module {}", moduleClassName, e);
						}
						return module; 
					}
					
				})
			.toList();
		return modules;
	}
	
	private static boolean isInstanciable(Class<?> klass) {
		int modifiers = klass.getModifiers();
		return !Modifier.isInterface(modifiers) && !Modifier.isAbstract(modifiers) && (klass.getEnclosingClass() == null || Modifier.isStatic(modifiers));
	}
}
