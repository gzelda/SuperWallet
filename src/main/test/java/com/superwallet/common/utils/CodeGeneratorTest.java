package com.superwallet.common.utils;

import com.superwallet.utils.CodeGenerator;
import org.junit.Test;

public class CodeGeneratorTest {

    @Test
    public void testGenerateInvitedCode() {
        String res1 = CodeGenerator.getInvitedCode("8618862173084");
        String res2 = CodeGenerator.getInvitedCode("8617695556242");
        String res3 = CodeGenerator.getInvitedCode("8618571778638");
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
