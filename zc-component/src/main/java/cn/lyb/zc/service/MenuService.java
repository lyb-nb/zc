package cn.lyb.zc.service;

import cn.lyb.zc.entity.Menu;

import java.util.List;

/**
 * @author lyb
 * @since 2019/8/26 22:03
 */
public interface MenuService {
    List<Menu> findAll();

    void saveMenu(Menu menu);

    Menu getMenuById(Integer menuId);

    void updateMenu(Menu menu);
}
