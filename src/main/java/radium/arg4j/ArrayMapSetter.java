package radium.arg4j;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.spi.FieldSetter;
import org.kohsuke.args4j.spi.Setter;

public class ArrayMapSetter implements Setter {

    private Map<Object, Optional<Object>> map;
    private Field field;
    private Object optionOrArgument;

    public ArrayMapSetter(Map<Object, Optional<Object>> map, Field field, Object optionOrArgument) {
        super();

        this.map = map;
        this.field = field;
        this.optionOrArgument = optionOrArgument;
    }

    @Override
    public void addValue(Object value) throws CmdLineException {
        Object array = null; 
        if (!map.containsKey(optionOrArgument)) {
            array = Array.newInstance(getType(), 1);
            Array.set(array, 0, value);
            map.put(optionOrArgument, Optional.of(array));
        } else {
            array = map.get(optionOrArgument);
            int length = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), length + 1);
            System.arraycopy(array, 0, newArray, 0, length);
            Array.set(newArray, length, value);
            array = newArray;
        }
        map.put(optionOrArgument, Optional.of(array));
    }

    @Override
    public Class getType() {
        return field.getType().getComponentType();
    }

    @Override
    public boolean isMultiValued() {
        return true;
    }

    @Override
    public FieldSetter asFieldSetter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnnotatedElement asAnnotatedElement() {
        throw new UnsupportedOperationException();
    }

}
