package com.you.ssm.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.message.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 游斌
 * @create 2020-07-02  8:52
 * 此工具类提供一些crowd项目中的工具方法
 */
public class CrowdUtil {

    /**
     * 专门负责上传文件到 OSS 服务器的工具方法
     *
     * @param endpoint        OSS 参数
     * @param accessKeyId     OSS 参数
     * @param accessKeySecret OSS 参数
     * @param inputStream     要上传的文件的输入流
     * @param bucketName      OSS 参数
     * @param bucketDomain    OSS 参数
     * @param originalName    要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(String endpoint,
                                                       String accessKeyId, String accessKeySecret,
                                                       InputStream inputStream, String bucketName,
                                                       String bucketDomain, String originalName) {
        //  创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在 OSS 服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用 UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName,
                    inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return ResultEntity.failed(" 当 前 响 应 状 态 码 =" + statusCode + " 错 误 消 息 =" + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }
    }


    /**
     * 给远程第三方接口发送信息请求，把验证码发送到用户手机上
     *
     * @param host       短信接口调用的url
     * @param path       具体发送短信的地址
     * @param method     请求方式
     * @param appCode    用户调用第三方短信api的appCode
     * @param receive    用户（电话号码）
     * @param templateId 模板id
     * @return
     */
    public static ResultEntity<String> sendCodeByShortMessage(String host, String path, String method,
                                                              String appCode, String receive,
                                                              String templateId) {
        try {
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appCode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("receive", receive);
            //随机生成验证码
            StringBuffer code = new StringBuffer();
            for (int i = 0; i < 6; i++) {
                int random = (int) (Math.random() * 10);
                code.append(random);
            }
            //将StringBuffer转换为string
            String codeStr = code.toString();
            querys.put("tag", codeStr);
            querys.put("templateId", templateId);
            Map<String, String> bodys = new HashMap<String, String>();
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取状态行
            StatusLine statusLine = response.getStatusLine();
            //通过状态行获取状态码
            int statusCode = statusLine.getStatusCode();
            //状态消息
            String reasonPhrase = statusLine.getReasonPhrase();
            if (statusCode == 200) {
                return ResultEntity.successWithData(codeStr);
            }
            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * 此方法用于判断请求类型是否为ajax请求
     *
     * @return true:是一个ajax请求  false：不是一个ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
//        1.获取请求头中的信息  Accept和 X-Requested-With信息
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
//        2.判断 Accept中是否含application/json    X-Requested-With中是否含XMLHttpRequest

        return (accept != null && !"".equals(accept) && accept.contains("application/json"))
                ||
                (header != null && !"".equals(header) && "XMLHttpRequest".equalsIgnoreCase(header));
    }

    /**
     * 此方法用于对传入的字符串进行md5加密
     *
     * @return
     */
    public static String md5(String source) {
        // 1. 判断传入的字符串是否有效
        if (source == null && "".equals(source)) {
            throw new RuntimeException(CrowdConstant.MESSAGE_INVALID_STRING);
        }

        try {
            // 2.   进行加密
            String encryptType = "md5";//加密方法
            // 3.  获取加密对象  MessageDigest 对象
            MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
            // 4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();
            // 5.执行加密
            byte[] output = messageDigest.digest(input);
            // 6.创建 BigInteger 对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            // 7.按照 16 进制将 bigInteger 的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
