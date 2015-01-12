# executable

The `Executable` class can be use to create `main` classes with Guice and Arg4j. 

```java
public class SayHelloWorld extends Executable {
	
	// Let's add a Guice module which will be discovered automatically.
	@Discoverable(inject = true)
	public static class HelloWorldModule extends AbstractModule {

		@Override
		protected void configure() {
			bindConstant().annotatedWith(Names.named("hello_world")).to("Hello, %s! ");
		}
		
	}
	
	// We use Guice's injection
	@Inject
	@Named("hello_world")
	private String helloWorld;
	
	// We can use Arg4j's @Option, @Argument, etc.
	@Inject
	@Option(name = "--name", required = true)
	private String name;
	
	// That's the main(arguments) equivalent
	@Override
	public Status execute() {
		System.out.println(String.format(helloWorld, name));
		return Status.SUCCESS;
	}
	
	// We bind the main(arguments) with the execute(arguments) method above here. 
	public static void main(String[] arguments) {
		execute(SayHelloWorld.class, arguments);
	}

}


```
