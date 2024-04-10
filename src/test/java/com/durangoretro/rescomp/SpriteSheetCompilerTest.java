package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class SpriteSheetCompilerTest {

    @Test
    void testInvalidOptionWidth()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
           String[] args ={"-n","testsprite","-m","SPRITESHEET","-i","test","-o","test"};
           Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testInvalidOptionHeight()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args ={"-n","testsprite","-m","SPRITESHEET","-i","test","-o","test","-w","20"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.INVALID_PARAMETERS.getCode(),statusCode);
    }

    @Test
    void testSpriteSheet()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(()-> {
            String[] args = {"-n", "testsprite", "-m", "SPRITESHEET", "-i", "src/test/resources/sprites.png", "-o", "target/sprites.h"
                    , "-w", "30", "-h", "27"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(),statusCode);
        File expectedFile=new File("src/test/resources/sprites.h");
        File actualFile=new File("target/sprites.h");
        org.assertj.core.api.Assertions.assertThat(actualFile).hasContent(Files.readString(expectedFile.toPath()));
    }
}
