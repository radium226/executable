package radium.executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import radium.inject.Injectors;
import radium.inject.arg4j.Arg4jModule;

import com.google.inject.Injector;

public abstract class Executable {

	public static enum Status {
		
		SUCCESS(0), FAILURE(1);
		
		private int code;
		
		Status(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	final private static Logger LOGGER = LoggerFactory.getLogger(Executable.class);
	
	public static Injector INJECTOR;
	
	private CmdLineParser parser;
	
	protected Executable() {
		super();
	}
	
	public static void execute(Class<? extends Executable> executableClass, String... arguments) {
		String executableClassSimpleName = executableClass.getSimpleName();
		// Let create the Guice injector. 
		Injector injector = null; 
		try {
			injector = Injectors.discoverInjector(new Arg4jModule(executableClass, arguments));
		} catch (CmdLineException e) {
			e.printStackTrace();
		}
		try {
			try {
				Method createInjectorMethod = executableClass.getMethod("createInjector");
				LOGGER.debug("Using createInjector() of {}", executableClassSimpleName);
				injector = (Injector) createInjectorMethod.invoke(null, injector);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
				e.printStackTrace();
				LOGGER.error("Unable to invoke createInjector", e);
				exit(Status.FAILURE);
			}
		} catch (NoSuchMethodException e) {
			LOGGER.debug("Using only the discovered injector");
		}
		INJECTOR = injector;
		
		// Now let's find the ParserProperties we are going to use with Args4j
		ParserProperties parserProperties = parserProperties();
		try {
			try {
				Method parserPropertiesMethod = executableClass.getMethod("parserProperties");
				LOGGER.debug("Using parserProperties() of {}", executableClassSimpleName);
				parserProperties = (ParserProperties) parserPropertiesMethod.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
				LOGGER.error("Unable to invoke createInjector()", e);
			}
		} catch (NoSuchMethodException e) {
			LOGGER.debug("Using the default parserProperties()");
			parserProperties = parserProperties();
		}
		
		// We can now instanciate the subclass of Executable. 
		Executable executable = injector.getInstance(executableClass);
		
		/*CmdLineParser parser = new CmdLineParser(executable, parserProperties);
		executable.parser = parser;
		try {
			parser.parseArgument(arguments);
		} catch (CmdLineException e) {
			e.printStackTrace();
			usage(executableClass, parser);
			exit(Status.FAILURE);
		}*/
		
		Status status = executable.execute();
		exit(status);
	}
	
	public static Injector getInjector() {
		return INJECTOR;
	}
	
	protected static ParserProperties parserProperties() {
		return ParserProperties.defaults();
	}
	
	public static void usage(Class<? extends Executable> executableClass, CmdLineParser parser) {
		System.err.print(executableClass.getName());
		parser.printSingleLineUsage(System.err);
		System.err.println();
		parser.printUsage(System.err);
	}
	
	public void usage() {
		usage(getClass(), parser);
	}
	
	public static void exit(Status status) {
		System.exit(status.getCode());
	}
	
	public abstract Status execute();
	
}
