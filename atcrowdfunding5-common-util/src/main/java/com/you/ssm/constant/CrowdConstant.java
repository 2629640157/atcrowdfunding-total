package com.you.ssm.constant;

/**
 * @author 游斌
 * @create 2020-07-02  11:41
 */
//定义常量
public class CrowdConstant {
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_ADMIN = "admin";
    public static final String ATTR_NAME_MEMBERVO = "memberVO";
    public static final String ATTR_NAME_LOGIN_ACCT = "loginacct";
    public static final String ATTR_NAME_LOGIN_MEMBER = "memberLoginVO";
    public static final String MESSAGE_LOGIN_FAILED = "登陆失败，请确认账号和密码！";
    public static final String MESSAGE_INVALID_STRING = "请传入一个有效的字符串！";
    public static final String MESSAGE_ACCESS_FORBIDDEN = "访问权限受限，请先登录！";
    public static final String MESSAGE_LOGIN_ACCT_EXIST_ADD = "添加失败！该账号已经被使用，请重新输入账号！";
    public static final String MESSAGE_LOGIN_ACCT_EXIST_UPDATE = "修改失败！该账号已经被使用，请重新输入账号！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "该账号已经被使用，请重新输入账号！";
    //保存手机号在redis里面的前缀
    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String MESSAGE_CODE_NOT_EXIST = "验证码过期，请检查手机号是否正确或者重新发送!";
    public static final String MESSAGE_CODE_INVALID = "验证码错误！";
    public static final String MESSAGE_STRING_INVALIDATE = "无效资源，请重新访问！";

    public static final String MESSAGE_HEADER_PIC_EMPTY = "头图不能为空！";
    public static final String MESSAGE_DETAIL_PIC_EMPTY = "详情图片不能为空！";
    public static final String MESSAGE_HEADER_PIC_UPLOAD_FAILED = "头图上传失败，请重新上传！";
    public static final String MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "详情图上传失败，请重新上传！";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "tempProject";
    public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "众筹项目不存在，请先众筹创建项目";
    public static final String ATTR_NAME_TYPE_LIST = "portalTypeVOList";
}
