package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StamperTest {

    @Test
    void testStamper()throws Exception{
        int statusCode= SystemLambda.catchSystemExit(() -> {
            String[] args={"-m","STAMP","-i","src/test/resources/crt0.s","-o","crt0.s","-n","DCLIB"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(),statusCode);
    }
}
