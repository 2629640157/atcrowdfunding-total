package com.you.ssm.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 游斌
 * @create 2020-08-03  20:32
 */
public class AccessPassResources {
    public static final Set<String> STATIC_RES_SET = new HashSet<>();
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        //这里为需要放行的路径（注意要和你代码中的路径相同）
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/member/to/reg/page");
        PASS_RES_SET.add("/member/to/login/page");
        PASS_RES_SET.add("/do/member/loginOut");
        PASS_RES_SET.add("/do/member/login");
        PASS_RES_SET.add("/do/member/checked");
        PASS_RES_SET.add("/member/send/short/message.json");
        PASS_RES_SET.add("project/get/project/detail/*");
    }

    static {
        //这里为需要放行的静态资源（注意要和你代码中的静态资源路径相同）
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 用于判断某个 ServletPath 值是否对应一个静态资源
     *
     * @param servletPath
     * @return true：是静态资源
     * false：不是静态资源
     */
    public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath) {
        // 1.排除字符串无效的情况
        if (servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 2.根据“/”拆分 ServletPath 字符串
        String[] split = servletPath.split("/");
        // 3.考虑到第一个斜杠左边经过拆分后得到一个空字符串是数组的第一个元素，所以需要使用下标 1 取第二个元素
        String firstLevelPath = split[1];
        // 4.判断是否在集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }
}
