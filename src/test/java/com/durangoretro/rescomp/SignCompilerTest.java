package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SignCompilerTest {

    @Test
    void testRequiredTitle()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args = {"-m", "SIGNER", "-n", "testBinary", "-i", "src/test/resources/binary.txt", "-o", "target/binary.h"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testRequiredDescription()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args = {"-m", "SIGNER", "-n", "testBinary", "-i", "src/test/resources/binary.txt", "-o", "target/binary.h",
            "-t","title"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testSignerCompiler()throws Exception{

        int statusCode = SystemLambda.catchSystemExit(()->{
          String[] args = {"-m","SIGNER","-n", "DCLIB","-t","WITCH","-d","catch all candys","-i","src/test/resources/witch.dux",
          "-o","target/witch.sign.dux"};
          Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(),statusCode);

    }
}
