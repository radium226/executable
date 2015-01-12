/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radium.arg4j;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.spi.FieldSetter;
import org.kohsuke.args4j.spi.Setter;

public class MapSetter implements Setter {

    private Map<Object, Optional<Object>> map;
    private Field field;
    private Object optionOrArgument;
    
    public MapSetter(Map<Object, Optional<Object>> map, Field field, Object optionOrArgument) {
        super();
        
        this.map = map;
        this.field = field;
        this.optionOrArgument = optionOrArgument;
    }
    
    @Override
    public void addValue(Object value) throws CmdLineException {
        map.put(optionOrArgument, Optional.of(value));
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public boolean isMultiValued() {
        return false;
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
