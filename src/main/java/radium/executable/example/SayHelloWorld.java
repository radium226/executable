package radium.executable.example;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.kohsuke.args4j.Option;

import radium.executable.Executable;

public class SayHelloWorld extends Executable {

    @Inject
    @Named("hello_world")
    private String helloWorld;

    @Inject
    private Foo foo;
    
    @Inject
	@Option(name = "--name", required = true)
	private String bar;
    
    @Override
    public Status execute() {
        System.out.println(String.format(helloWorld, foo.getBar()));
        return Status.SUCCESS;
    }

    public static void main(String[] arguments) {
        execute(SayHelloWorld.class, arguments);
    }

}
