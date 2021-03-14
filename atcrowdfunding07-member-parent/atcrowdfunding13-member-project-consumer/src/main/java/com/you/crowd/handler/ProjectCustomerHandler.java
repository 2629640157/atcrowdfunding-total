package com.you.crowd.handler;

import com.you.crowd.api.MySQLRemoteService;
import com.you.crowd.config.OSSProperties;
import com.you.crowd.entity.vo.*;
import com.you.ssm.constant.CrowdConstant;
import com.you.ssm.util.CrowdUtil;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 游斌
 * @create 2020-08-06  21:45
 */
@Controller
public class ProjectCustomerHandler {
    @Autowired
    private OSSProperties ossProperties;
    @Resource
    private MySQLRemoteService mySQLRemoteService;

    /**
     * @param projectVO         接收除了上传图片之外的其他普通数据
     * @param headerPicture     接收上传的头图
     * @param detailPictureList 接收上传的详情图
     * @param session           用来将收集了一部分数据的 ProjectVO 对象存入 Session 域
     * @param modelMap          用来在当前操作失败后返回上一个表单页面时携带提示消息
     * @return
     * @throws IOException
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList, HttpSession session, ModelMap modelMap) throws IOException {
        // 一、完成头图上传
        // 1.获取当前 headerPicture 对象是否为空
        boolean headerPictureIsEmpty = headerPicture.isEmpty();
        if (headerPictureIsEmpty) {
            // 2.如果没有上传头图则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-step1";
        }
        // 3.如果用户确实上传了有内容的文件，则执行上传
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), headerPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), headerPicture.getOriginalFilename());
        String result = uploadHeaderPicResultEntity.getOperationResult();
        // 4.判断头图是否上传成功
        if (ResultEntity.SUCCESS.equals(result)) {
            // 5.如果成功则从返回的数据中获取图片访问路径
            String headerPicturePath = uploadHeaderPicResultEntity.getQueryData();
            // 6.存入 ProjectVO 对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            // 7.如果上传失败则返回到表单页面并显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-step1";
        }
        // 二、上传详情图片
        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();
        // 2.检查 detailPictureList 是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-step1";
        }
        // 3.遍历 detailPictureList 集合
        for (MultipartFile detailPicture : detailPictureList) {
            // 4.当前 detailPicture 是否为空
            if (detailPicture.isEmpty()) {
                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-step1";
            }
            //6.执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), detailPicture.getOriginalFilename());
            // 7.检查上传结果
            String detailUploadResult = detailUploadResultEntity.getOperationResult();
            if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                String detailPicturePath = detailUploadResultEntity.getQueryData();
                // 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(detailPicturePath);
            } else {
                //9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-step1";
            }
        }
        // 10.将存放了详情图片访问路径的集合存入 ProjectVO 中
        projectVO.setDetailPicturePathList(detailPicturePathList);
        // 三、后续操作
        // 1.将 ProjectVO 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        //2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://www.crowd.com/project/to/step2/page";
    }

    /**
     * @param returnPicture 接收用户上传的文件
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        // 1.执行文件上传
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), returnPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), returnPicture.getOriginalFilename());
        // 2.返回上传的结果
        return uploadReturnPicResultEntity;
    }

    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            // 1.从session域中获取之前缓存的projectVO对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            // 2.判断 projectVO 是否为 null
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            // 3.从 projectVO 对象中获取存储回报信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            // 4.判断 returnVOList 集合是否有效
            if (returnVOList == null) {
                // 5.创建集合对象对 returnVOList 进行初始化
                returnVOList = new ArrayList<>();
                // 6.为了让以后能够正常使用这个集合，设置到 projectVO 对象中
                projectVO.setReturnVOList(returnVOList);
            }
            // 7.将收集了表单数据的 returnVO 对象存入集合
            returnVOList.add(returnVO);
            // 8.把数据有变化的 ProjectVO 对象重新存入 Session 域，以确保新的数据最终能够存入 Redis
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            // 9.所有操作成功完成返回成功
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO) {
        //1.从 Session 域读取之前临时存储的 ProjectVO 对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        //2.如果 projectVO 为 null
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        //3.将确认信息数据设置到 projectVO 对象中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        //4.从 Session 域读取当前登录的用户
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberLoginVOId = memberLoginVO.getId();
        //5.调用远程方法保存 projectVO 对象
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberLoginVOId);
        //6.判断远程的保存操作是否成功
        if (ResultEntity.FAILED.equals(saveResultEntity.getOperationResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getOperationMessage());
        }
        // 7.将临时的 ProjectVO 对象从 Session 域移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        // 8.如果远程保存成功则跳转到最终完成页面
        return "redirect:http://www.crowd.com/project/to/step4/page";
    }

    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId, Map map) {
        ResultEntity<DetailProjectVO> detailProjectVORemote = mySQLRemoteService.getDetailProjectVORemote(projectId);
        String operationResult = detailProjectVORemote.getOperationResult();
        if (ResultEntity.SUCCESS.equals(operationResult)) {
            DetailProjectVO detailProjectVO = detailProjectVORemote.getQueryData();
            map.put("detailProjectVO", detailProjectVO);
        }
        return "project-show-detail";
    }
}
