package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifySignTest {

    @Test
    void testVerifySign()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
           String[] args ={"-m","VERIFY","-i","src/test/resources/witch.sign.dux","-n","testVerify","-o","output"};
           Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(),statusCode);
    }
}
