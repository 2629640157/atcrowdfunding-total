package com.you.crowd.service.impl;

import com.you.crowd.entity.Menu;
import com.you.crowd.mapper.MenuMapper;
import com.you.crowd.service.aop.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-06  15:46
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenus() {
        List<Menu> menus = menuMapper.selectByExample(null);
        return menus;
    }

    @Override
    public void insertMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateMenu(Menu menu) {
//        注意修改的时候要使用updateByPrimaryKeySelective
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}
