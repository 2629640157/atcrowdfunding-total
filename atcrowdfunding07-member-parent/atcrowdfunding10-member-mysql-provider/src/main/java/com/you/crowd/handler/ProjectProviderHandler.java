package com.you.crowd.handler;

import com.you.crowd.entity.vo.DetailProjectVO;
import com.you.crowd.entity.vo.PortalTypeVO;
import com.you.crowd.entity.vo.ProjectVO;
import com.you.crowd.service.api.ProjectService;
import com.you.ssm.util.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-08  9:43
 */
@RestController
public class ProjectProviderHandler {
    @Resource
    private ProjectService projectService;

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberLoginVOId") Integer memberLoginVOId) {
        try {
            // 调用“本地”Service 执行保存
            projectService.saveProject(projectVO, memberLoginVOId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/date/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDateRemote() {
        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

}
