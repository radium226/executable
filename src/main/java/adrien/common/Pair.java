package adrien.common;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public class Pair<F, S> {

    private F first;
    private S second;
    
    private Pair(final F first, S second) {
        super();
        
        this.first = first;
        this.second = second;
    }
    
    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }
    
    public F getFirst() {
        return first;
    }
    
    public S getSecond() {
        return second;
    }
    
    @Override
    public boolean equals(Object object) {
        boolean equals = false;
        if (object instanceof Pair) {
            Pair<?, ?> that = (Pair) object;
            equals = Objects.equals(this.first, that.first) && Objects.equals(this.second, that.second);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Pair.class)
                .add("first", this.first)
                .add("second", this.second)
            .toString();
    }
    
}
