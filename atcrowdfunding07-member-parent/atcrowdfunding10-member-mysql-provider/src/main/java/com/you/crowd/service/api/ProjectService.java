package com.you.crowd.service.api;

import com.you.crowd.entity.vo.DetailProjectVO;
import com.you.crowd.entity.vo.PortalTypeVO;
import com.you.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-08  8:26
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberLoginVOId);

    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
