package radium.arg4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.kohsuke.args4j.spi.Setter;

public class MapSetters {

    public static Setter create(Map<Object, Optional<Object>> map, Field field, Object optionOrArgument) {
        Setter setter = null;
        Class<?> type = field.getType();
        if (type.isArray()) {
            setter = new ArrayMapSetter(map, field, optionOrArgument);
        } else if (List.class.isAssignableFrom(type)) {
            setter = new MultiValueMapSetter(map, field, optionOrArgument);
        } else {
            System.out.println("????");
            setter = new MapSetter(map, field, optionOrArgument);
        }
        return setter;
    }

}
