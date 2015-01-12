package radium.arg4j;

import adrien.common.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Arg4j {

    private Class<?> mainClass;
    private Map<Object, Optional<Object>> map;

    private Arg4j(Class<?> mainClass) {
        super();
        this.mainClass = mainClass;
    }

    public static Arg4j forClass(Class<?> mainClass) {
        return new Arg4j(mainClass);
    }

    public Arg4j parseArguments(String... texts) throws CmdLineException {
        map = Maps.newHashMap();
        CmdLineParser parser = new CmdLineParser(null);
        for (Field field : mainClass.getDeclaredFields()) {
            Option option = field.getAnnotation(Option.class);
            if (option != null) {
                parser.addOption(MapSetters.create(map, field, option), option);
            }
            
            Argument argument = field.getAnnotation(Argument.class);
            if (argument != null) {
                parser.addArgument(MapSetters.create(map, field, argument), argument);
            }
        }
        parser.parseArgument(texts);
        return this;
    }
    
    public static Pair<List<Option>, List<Argument>> parseOptionsAndArguments(Class<?> mainClass) {
        List<Option> options = Lists.newArrayList();
        List<Argument> arguments = Lists.newArrayList();
        for (Field field : mainClass.getDeclaredFields()) {
            Option option = field.getAnnotation(Option.class);
            if (option != null) {
                options.add(option);
            }
            
            Argument argument = field.getAnnotation(Argument.class);
            if (argument != null) {
                arguments.add(argument);
            }
        }
        return Pair.of(options, arguments);
    }
    
    public <I> void setFields(I instance) {
    	Class<?> instanceClass = instance.getClass();
    	for (Field field : instanceClass.getDeclaredFields()) {
    		Inject inject = field.getAnnotation(Inject.class);
    		if (inject != null) {
	    		Option option = field.getAnnotation(Option.class);
	    		if (option != null) {
	    			Optional<Object> value = map.get(option);
	    			if (value != null && value.isPresent()) {
	    				boolean accessible = field.isAccessible();
	    				field.setAccessible(true);
	    				try {
							field.set(instance, value.get());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
	    				field.setAccessible(accessible);
	    			}
	    		}
    		}
    		
    		Argument argument = field.getAnnotation(Argument.class);
    		if (argument != null) {
    			Optional<Object> value = map.get(argument);
    			if (value != null && value.isPresent()) {
    				boolean accessible = field.isAccessible();
    				field.setAccessible(true);
    				try {
						field.set(instance, value.get());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
    				field.setAccessible(accessible);
    			}
    		}
    	}
    }

}
