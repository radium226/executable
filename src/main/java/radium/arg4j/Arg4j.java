package radium.arg4j;

import adrien.common.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    private Arg4j(Class<?> mainClass) {
        super();
        this.mainClass = mainClass;
    }

    public static Arg4j forClass(Class<?> mainClass) {
        return new Arg4j(mainClass);
    }

    public Map<Object, Optional<Object>> parseArguments(String... texts) throws CmdLineException {
        Map<Object, Optional<Object>> map = Maps.newHashMap();
        CmdLineParser parser = new CmdLineParser(null);
        for (Field field : mainClass.getDeclaredFields()) {
            Option option = field.getAnnotation(Option.class);
            if (option != null) {
                System.out.println("!!!");
                parser.addOption(MapSetters.create(map, field, option), option);
            }
            
            Argument argument = field.getAnnotation(Argument.class);
            if (argument != null) {
                parser.addArgument(MapSetters.create(map, field, argument), argument);
            }
        }
        parser.parseArgument(texts);
        return map;
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

}
