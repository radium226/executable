package radium.example;

import org.kohsuke.args4j.Option;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import radium.executable.Executable;
import radium.inject.Discoverable;

public class SayHelloWorld extends Executable {

	@Discoverable(inject=true)
	public static class HelloWorldModule extends AbstractModule {

		public HelloWorldModule() {
			super();
		}
		
		@Override
		protected void configure() {
			bindConstant().annotatedWith(Names.named("hello_world")).to("Hello, %s! ");
		}
		
	}
	
	@Inject
	@Named("hello_world")
	private String helloWorld;
	
	@Option(name = "--name", required = true)
	private String name;
	
	@Override
	public Status execute() {
		System.out.println(String.format(helloWorld, name));
		return Status.SUCCESS;
	}
	
	public static void main(String[] arguments) {
		execute(SayHelloWorld.class, arguments);
	}

}
