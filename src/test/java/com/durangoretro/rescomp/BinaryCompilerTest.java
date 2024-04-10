package com.durangoretro.rescomp;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class BinaryCompilerTest {

    @Test
    void testCompiler()throws Exception {
        int statusCode = SystemLambda.catchSystemExit(() -> {
            String[] args = {"-m", "BINARY", "-n", "testBinary", "-i", "src/test/resources/binary.txt", "-o", "target/binary.h"};
            Main.main(args);
        });
        Assertions.assertEquals(Status.OK.getCode(), statusCode);

        File expectedFile = new File("src/test/resources/binary.h");
        File actualFile = new File("target/binary.h");
        org.assertj.core.api.Assertions.assertThat(actualFile).hasContent(Files.readString(expectedFile.toPath()));
    }
}
