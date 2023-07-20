package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class MusicCompilerTest {

    @Test
    void testCompilerTest() throws Exception {
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args ={"-n","testmusic","-m","MUSIC","-i","src/test/resources/test.musicxml","-o","target/testmusic.h"};
            Main.main(args);
        });

        Assertions.assertEquals(Status.OK.getCode(),statusCode);
        File expectedFile = new File("src/test/resources/testmusic.h");
        File actualFile = new File("target/testmusic.h");
        org.assertj.core.api.Assertions.assertThat(actualFile).hasBinaryContent(Files.readAllBytes(expectedFile.toPath()));
    }
}
