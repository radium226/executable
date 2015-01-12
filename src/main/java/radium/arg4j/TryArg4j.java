/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radium.arg4j;

import java.util.Map;
import java.util.Optional;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.Setters;

/**
 *
 * @author adrien
 */
public class TryArg4j {
    
    public static class Main {
        
        @Option(name = "--option")
        private String option;
        
    }
    
    public static void main(String[] arguments) throws CmdLineException {
        //Map<Object, Optional<Object>> map = Arg4j.forClass(Main.class).parseArguments(new String[] {"--option=value"});
        //System.out.println("map=" + map);
    }
    
}
