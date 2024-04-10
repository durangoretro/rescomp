package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackGroundCompilerTests {

    @Test
    void testInvalidOptions()throws Exception{

        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args={"-n","testBackground","-i","src/test/resources/background.png","-o","target/background.h"};
            Main.main(args);
        }
        );
        assertEquals(1,statusCode);

    }

    @Test
    void testInvalidMode()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
                    String[] args = {"-m", "AAA", "-n", "testBackground", "-i", "src/test/resources/background.png", "-o", "target/background.h"};
                    Main.main(args);
                }
        );
        assertEquals(2,statusCode);

    }

    @Test
    void testCompileBackGround()throws Exception{
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args = {"-m", "BACKGROUND", "-n", "testBackground", "-i", "src/test/resources/background.png", "-o", "target/background.h"};
            Main.main(args);

        });
        assertEquals(0,statusCode);
        File expectedFile=new File("src/test/resources/background.h");
        File actualFile=new File("target/background.h");
        Assertions.assertThat(actualFile).hasContent(Files.readString(expectedFile.toPath()));
    }


}
