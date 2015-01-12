package radium.arg4j;

public class TryHello {

    public static class FirstHello {
        @Hello(value="1")
        private String firstHello;
    }
    
    public static class SecondHello {
        @Hello(value="2")
        private String secondHello;
    }
    
    public TryHello() {
        super();
    }
    
    public static void main(String[] arguments) throws Throwable {
        Hello firstHello = FirstHello.class.getDeclaredField("firstHello").getAnnotation(Hello.class);
        Hello secondHello = SecondHello.class.getDeclaredField("secondHello").getAnnotation(Hello.class);
        System.out.println(firstHello.equals(secondHello));
        
    }
    
}
