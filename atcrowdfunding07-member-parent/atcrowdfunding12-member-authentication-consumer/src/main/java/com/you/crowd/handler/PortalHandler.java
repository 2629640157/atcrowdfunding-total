package com.you.crowd.handler;


import com.you.crowd.api.MySQLRemoteService;
import com.you.crowd.api.RedisRemoteService;
import com.you.crowd.config.ShortMessageProperties;
import com.you.crowd.entity.po.MemberPO;
import com.you.crowd.entity.vo.MemberLoginVO;
import com.you.crowd.entity.vo.MemberVO;
import com.you.crowd.entity.vo.PortalTypeVO;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class PortalHandler {
    @Autowired
    private ShortMessageProperties messageProperties;
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Resource
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Map map) {
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDateRemote();
        String operationResult = resultEntity.getOperationResult();
        if (ResultEntity.SUCCESS.equals(operationResult)) {
            List<PortalTypeVO> portalTypeVOList = resultEntity.getQueryData();
            map.put("portalTypeVOList", portalTypeVOList);
        }
        return "portal";
    }

    //获取验证码，发送短信，并把验证码存储到redis中
    @ResponseBody
    @RequestMapping(value = "/member/send/short/message.json")
    public ResultEntity<String> sendShortMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> codeResultEntity = CrowdUtil.sendCodeByShortMessage(messageProperties.getHost(), messageProperties.getPath(), messageProperties.getMethod(), messageProperties.getAppCode(), phoneNum, messageProperties.getTemplateId());
        //判断短信是否发送成功
        if (ResultEntity.SUCCESS.equals(codeResultEntity.getOperationResult())) {
            //如果成功，将验证码出入redis中
            //1.获取随机生成的验证码
            String code = codeResultEntity.getQueryData();
            //2.拼接一个用于redis中存储的key
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            //3.调用远程接口存入redis
            ResultEntity<String> saveToRedis = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
            //4.判断结果
            if (ResultEntity.SUCCESS.equals(saveToRedis.getOperationResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return saveToRedis;
            }
        }
        return codeResultEntity;
    }

    //通过ajax提交json串完成注册
    @ResponseBody
    @RequestMapping("/do/member/checked")
    public ResultEntity<String> checkedNewMember(@RequestBody MemberVO memberVO) {
        System.out.println("memberVO = " + memberVO.toString());
        //通过拼接redis中验证码的key
        String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        //通过key获取redis中对应的值
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        //获取其中对应的状态，消息，数据的信息
        String operationResult = redisStringValueByKeyRemote.getOperationResult();
        String operationMessage = redisStringValueByKeyRemote.getOperationMessage();
        String redisCode = redisStringValueByKeyRemote.getQueryData();
        //判断是否通过key获取到对应的value
        if (ResultEntity.FAILED.equals(operationResult)) {
            return ResultEntity.failed(operationMessage);
        }
        if (redisCode == null) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_EXIST);
        }
        //验证码不正确时
        if (!Objects.equals(memberVO.getCode(), redisCode)) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_INVALID);
        }
        //如果一致删除redis中的验证码
        //redisRemoteService.removeRedisKeyRemote(key);
        //密码加密
        String userpswd = memberVO.getUserpswd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodepswd = bCryptPasswordEncoder.encode(userpswd);
        memberVO.setUserpswd(encodepswd);
        //设置用户名与账号相同
        memberVO.setUsername(memberVO.getLoginacct());
        MemberPO memberPO = new MemberPO();
        //把memberVO的属性赋给memberPO
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveNewMember = mySQLRemoteService.saveNewMember(memberPO);
        if (ResultEntity.FAILED.equals(saveNewMember.getOperationResult())) {
            return ResultEntity.failed(saveNewMember.getOperationMessage());
        }
        //使用重定向避免刷新浏览器导致重新执行注册流程
        return ResultEntity.successWithoutData();
    }


    //通过表单提交注册用户
    @RequestMapping("/do/member/register")
    public String registerNewMember(MemberVO memberVO, Map map) {
        System.out.println("memberVO = " + memberVO.toString());
        //通过拼接redis中验证码的key
        String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        //通过key获取redis中对应的值
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        //获取其中对应的状态，消息，数据的信息
        String operationResult = redisStringValueByKeyRemote.getOperationResult();
        String operationMessage = redisStringValueByKeyRemote.getOperationMessage();
        String redisCode = redisStringValueByKeyRemote.getQueryData();
        //判断是否通过key获取到对应的value
        if (ResultEntity.FAILED.equals(operationResult)) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, operationMessage);
            map.put(CrowdConstant.ATTR_NAME_MEMBERVO, memberVO);
            return "member-reg";
        }
        if (redisCode == null) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXIST);
            map.put(CrowdConstant.ATTR_NAME_MEMBERVO, memberVO);
            return "member-reg";
        }
        //验证码不正确时
        if (!Objects.equals(memberVO.getCode(), redisCode)) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            map.put(CrowdConstant.ATTR_NAME_MEMBERVO, memberVO);
            return "member-reg";
        }
        //如果一致删除redis中的验证码
        redisRemoteService.removeRedisKeyRemote(key);
        //密码加密
        String userpswd = memberVO.getUserpswd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodepswd = bCryptPasswordEncoder.encode(userpswd);
        memberVO.setUserpswd(encodepswd);
        //设置用户名与账号相同
        memberVO.setUsername(memberVO.getLoginacct());
        MemberPO memberPO = new MemberPO();
        //把memberVO的属性赋给memberPO
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveNewMember = mySQLRemoteService.saveNewMember(memberPO);
        if (ResultEntity.FAILED.equals(saveNewMember.getOperationResult())) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, saveNewMember.getOperationMessage());
            return "member-reg";
        }
        //使用重定向避免刷新浏览器导致重新执行注册流程
        return "redirect:http://www.crowd.com/member/to/login/page";
    }


    //登陆
    @RequestMapping("/do/member/login")
    public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd") String userpswd, Map map, HttpSession session) {
        //通过loginacct查找用户
        ResultEntity<MemberPO> memberPOByLoginAcctRemote = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        String operationMessage = memberPOByLoginAcctRemote.getOperationMessage();
        MemberPO memberPO = memberPOByLoginAcctRemote.getQueryData();
        String operationResult = memberPOByLoginAcctRemote.getOperationResult();
        //获取数据库数据失败时
        if (ResultEntity.FAILED.equals(operationResult)) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, operationMessage);
            map.put(CrowdConstant.ATTR_NAME_LOGIN_ACCT, loginacct);
            return "member-login";
        }
        //通过loginacct没有查找到指定用户
        if (memberPO == null) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            map.put(CrowdConstant.ATTR_NAME_LOGIN_ACCT, loginacct);
            return "member-login";
        }
        //校验密码是否正确
        String memberPOUserpswd = memberPO.getUserpswd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(userpswd, memberPOUserpswd);
        if (!matches) {
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            map.put("loginacct", loginacct);
            return "member-login";
        }
        //把memberLoginVO添加到session域
        MemberLoginVO memberLoginVO = new MemberLoginVO();
        BeanUtils.copyProperties(memberPO, memberLoginVO);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
        return "redirect:http://www.crowd.com/member/to/main/page";
    }

    //退出
    @RequestMapping("/do/member/loginOut")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}