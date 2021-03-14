package com.you.crowd.service.impl;

import com.you.crowd.entity.po.*;
import com.you.crowd.entity.vo.*;
import com.you.crowd.mapper.*;
import com.you.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-08-08  8:29
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Resource
    private ReturnPOMapper returnPOMapper;
    @Resource
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Resource
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    /*@Resource
    private TagPOMapper tagPOMapper;
    @Resource
    private  TypePOMapper typePOMapper;*/


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberLoginVOId) {
        //1.把projectVO的属性赋给projectPO，把projectPO加入数据库中
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO, projectPO);
        //把登陆用户的id赋给projectPO（这个项目的创建人）
        projectPO.setMemberid(memberLoginVOId);
        //设置项目创建时间
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createDate);
        //DeployDate发布时间要等后台管理员同意后才确定，这里是模拟的
        projectPO.setDeploydate(createDate);
        //设置项目状态
        projectPO.setStatus(0);
        projectPOMapper.insertSelective(projectPO);
        //从projectPO获取项目自动递增的主键id
        Integer projectId = projectPO.getId();

        //2.通过projectVO获取分类信息typeIdList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectId);
        //3.通过projectVO获取标签信息tagIdList
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectId);
        //4.通过projectVO获取详情图片信息DetailPicturePathList
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectId, detailPicturePathList);

        //5.通过projectVO获取发起人信息memberLauchInfoVO
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        //创建memberLaunchInfoPO对象
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        //memberLauchInfoVO的属性赋给memberLaunchInfoPO
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        //把登陆用户的id赋给memberLaunchInfoPO
        memberLaunchInfoPO.setMemberid(memberLoginVOId);
        //加入数据库
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);

        //6.通过projectVO获取回报信息returnVOList
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        //通过遍历returnVOList，把回报列表中的数据写到returnPOList中去
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            //这里有2种写法
            // 1.给每个returnPO对象赋予projectId，然后调用returnPOMapper.insertSelective(returnPO);
            //2. 通过自己在returnPOMapper写个方法insertReturnPOList(projectId,returnPOList)，通过写sql遍历这个returnPOList集合
            returnPO.setProjectid(projectId);
            returnPOMapper.insertSelective(returnPO);
        }
        //6.通过projectVO获取发起人确认信息memberConfirmInfoVO
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberLoginVOId);
        memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO() {
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
        return portalTypeVOList;
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        // 1.查询得到 DetailProjectVO 对象
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        // 2.根据 status 确定 statusText
        Integer status = detailProjectVO.getStatus();
        switch (status) {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }
        // 3.根据 deployeDate 计算 lastDay
        String deployDate = detailProjectVO.getDeployDate();
        // 获取当前日期
        Date currentDay = new Date();
        // 把众筹日期解析成 Date 类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = format.parse(deployDate);
            // 获取当前当前日期的时间戳
            long currentDayTime = currentDay.getTime();
            // 获取众筹日期的时间戳
            long deployDayTime = deployDay.getTime();
            // 两个时间戳相减计算当前已经过去的时间
            long pastDays = (currentDayTime - deployDayTime) / 1000 / 60 / 60 / 24;
            // 获取总的众筹天数
            Integer totalDays = detailProjectVO.getDay();
            //使用总的众筹天数减去已经过去的天数得到剩余天数
            Integer lastDay = (int) (totalDays - pastDays);
            detailProjectVO.setLastDay(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return detailProjectVO;
    }
}
