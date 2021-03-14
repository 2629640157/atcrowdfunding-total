package com.you.crowd;

import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author 游斌
 * @create 2020-07-02  17:30
 */
public class CrowdUtilTest {
    @Test
    public void testMd5() {
        String md5 = CrowdUtil.md5("123456");
        System.out.println("md5 = " + md5);
    }

    @Test
    public void uploadFileToOssTest() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("thumb.jpg");
        ResultEntity<String> stringResultEntity = CrowdUtil.uploadFileToOss("http://oss-cn-hangzhou.aliyuncs.com",
                "LTAI4GKGPChYZP87QcWu5hp9",
                "hdZ0Xxx8Yd16i1j5CpsKMaHE2OtrX7",
                inputStream,
                "ydgk-test",
                "http://ydgk-test.oss-cn-hangzhou.aliyuncs.com",
                "thumb.jpg");
        System.out.println("stringResultEntity = " + stringResultEntity.getOperationMessage());
    }
}


