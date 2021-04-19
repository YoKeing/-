package com.crowd.test;

import com.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {

    @Test
    public void testMd5(){
        String source = "123456";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }

}
