package radium.executable.example;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import radium.inject.Discoverable;

@Discoverable(inject = true)
public class HelloWorldModule extends AbstractModule {

    public HelloWorldModule() {
        super();
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("hello_world")).to("Hello, %s! ");
        bind(Foo.class).asEagerSingleton();
    }
    
}
