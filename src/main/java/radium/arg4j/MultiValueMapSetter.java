/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radium.arg4j;

import com.google.common.collect.Lists;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.spi.FieldSetter;
import org.kohsuke.args4j.spi.Setter;

/**
 *
 * @author adrien
 */
public class MultiValueMapSetter implements Setter {
    
    private Map<Object, Optional<Object>> map;
    private Field field;
    private Object optionOrArgument;
    
    public MultiValueMapSetter(Map<Object, Optional<Object>> map, Field field, Object optionOrArgument) {
        super();
        
        this.map = map;
        this.field = field;
        this.optionOrArgument = optionOrArgument;
    }

    @Override
    public void addValue(Object value) throws CmdLineException {
        if (!map.containsKey(optionOrArgument)) {
            map.put(optionOrArgument, Optional.<Object>of(Lists.newArrayList()));
        }
        
        ((List<Object>) map.get(optionOrArgument).get()).add(value);
    }

    @Override
    public Class getType() {
        Type type = field.getGenericType();
        if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            type = pt.getActualTypeArguments()[0];
            if (type instanceof Class) {
                return (Class) type;
            }
        }
        return Object.class;
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
