package edu.cscc;
/**
 * @author Prakash Parasuram
 */

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConfigTest {
    @Test
    public void justTestProperties(){
        Config config = new Config();
        config.dumpProperties();
        assertEquals("./html",config.getProperty("defaultFolder"));
    }
}
