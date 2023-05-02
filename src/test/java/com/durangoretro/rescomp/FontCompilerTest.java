package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class FontCompilerTest {

    @Test
    void testRequiredHeight()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args ={"-n","testFont","-m","FONT","-i","test","-o","test","-w","8"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testRequiredWidth()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args ={"-n","testSign","-m","FONT","-i","test","-o","test"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testFontCompile()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(()-> {
            String[] args = {"-n", "testSign", "-m", "FONT", "-i", "src/test/resources/font.png", "-o", "target/font.h"
                    , "-w", "8", "-h", "5"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(),statusCode);
        File expectedFile=new File("src/test/resources/witch.sign.dux");
        File actualFile=new File("target/witch.sign.dux");
        org.assertj.core.api.Assertions.assertThat(actualFile).hasBinaryContent(Files.readAllBytes(expectedFile.toPath()));
    }
}
