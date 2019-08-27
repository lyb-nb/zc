package cn.lyb.zc.service.impl;

import cn.lyb.zc.entity.Menu;
import cn.lyb.zc.entity.MenuExample;
import cn.lyb.zc.mapper.MenuMapper;
import cn.lyb.zc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyb
 * @since 2019/8/26 22:03
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }


}
