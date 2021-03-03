package com.you.crowd.controller;

import com.you.crowd.entity.Menu;
import com.you.crowd.service.aop.MenuService;
import com.you.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-06  15:47
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/getAllMenus.json")
    //    进行优化过后的
    public ResultEntity getAllMenus() {
        try {
            //            获取所有数据信息
            List<Menu> menus = menuService.getAllMenus();
            //        将数据保存在map集合中
            HashMap<Integer, Menu> menuHashMap = new HashMap<>();
            for (Menu menu : menus) {
                menuHashMap.put(menu.getId(), menu);
            }
            Menu root = null;
            for (Menu menu : menus) {
                //            判断是否是根节点
                if (menu.getpId() == null) {
                    root = menu;
                    continue;
                }
                //            获取该元素的父节点
                Menu menuParent = menuHashMap.get(menu.getpId());
                //            把该元素添加到父节点的children
                menuParent.getChildren().add(menu);
            }
            return ResultEntity.successWithData(root);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/insertMenu.json")
    public ResultEntity insertMenu(Menu menu) {
        try {
            menuService.insertMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/deleteMenu.json")
    public ResultEntity deleteMenu(Integer id) {
        try {
            menuService.deleteMenu(id);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/updateMenu.json")
    public ResultEntity updateMenu(Menu menu) {
        try {
            menuService.updateMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}


//没有进行优化的（时间复杂度和空间复杂度较高）
//    public ResultEntity getAllMenus() {
////            获取所有数据信息
//        List<Menu> menus = menuService.getAllMenus();
////        组件父子关系
//        Menu root = null;
//        for (Menu menuParent : menus) {
////                判断是否是根节点
//            if (menuParent.getpId() == null) {
//                root = menuParent;
//            }
////                继续判断是否是当前节点的子接点
//            for (Menu menu : menus) {
////                    如果是，就把他 加到前节点的children里面
//                if (menuParent.getId() == menu.getpId()) {
//                    menuParent.getChildren().add(menu);
//                }
//            }
//        }
//        return ResultEntity.successWithData(root);
//
//    }


