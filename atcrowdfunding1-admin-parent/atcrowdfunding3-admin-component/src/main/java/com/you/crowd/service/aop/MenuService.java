package com.you.crowd.service.aop;

import com.you.crowd.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 游斌
 * @create 2020-07-06  15:45
 */
public interface MenuService {

    List<Menu> getAllMenus();

    void insertMenu(Menu menu);

    void deleteMenu(Integer id);

    void updateMenu(Menu menu);
}
