package radium.inject.arg4j;

import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.ClassParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import radium.arg4j.Arg4j;
import radium.executable.Executable;


public class Arg4jModule extends AbstractModule {

    private Arg4j arg4j;
    
    public Arg4jModule(Class<?> mainClass, String... arguments) throws CmdLineException {
        this(Arg4j.forClass(mainClass).parseArguments(arguments));
    }
    
    public Arg4jModule(Arg4j arg4j) {
        super();
        
        this.arg4j = arg4j;
    }
    
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new TypeListener() {

            @Override
            public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
                encounter.register(new MembersInjector<I>() {

					@Override
					public void injectMembers(I instance) {
						arg4j.setFields(instance);
					}
					
				});
            }
            
        });
    }
    
}
