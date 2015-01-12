/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.kohsuke.args4j.ClassParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import radium.arg4j.Arg4j;
import radium.executable.Executable;

/**
 *
 * @author adrien
 */
public class Arg4jModule extends AbstractModule {

    private Map<Object, Optional<Object>> map;
    
    public Arg4jModule(Class<?> mainClass, String... arguments) throws CmdLineException {
        this(Arg4j.forClass(mainClass).parseArguments(arguments));
    }
    
    public Arg4jModule(Map<Object, Optional<Object>> map) {
        super();
        
        this.map = map;
    }
    
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new TypeListener() {

            @Override
            public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
                
            }
            
        });
    }
    
}
