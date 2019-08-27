package cn.lyb.zc.handler;

import cn.lyb.zc.entity.Menu;
import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyb
 * @since 2019/8/26 22:04
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/menu/get/whole/tree")
    public ResultEntity<Menu> getWholeTree() {
        // 1.查询所有的树形节点用于组装
        List<Menu> menuList = menuService.findAll();
        // 2.将List<Menu>转换为Map<Menu的id,Menu>
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        // 3.声明变量用于存储根节点对象
        Menu rootNode = null;
        // 4.遍历List<Menu>
        for (Menu menu : menuList) {
            // 5.获取当前Menu对象的pid属性
            Integer pid = menu.getPid();
            // 6.判断pid是否为null
            if (pid == null) {
                // 7.如果pid为null，说明当前节点是根节点，所以赋值
                rootNode = menu;
                // 8.根节点没有父节点，所以不必找父节点组装，本次for循环停止执行，继续执行下一次循环
                continue;
            }
            // 9.既然pid不为null，那么我们根据这个pid查找当前节点的父节点。
            Menu father = menuMap.get(pid);
            // 10.组装：将menu添加到maybeFather的子节点集合中
            father.getChildren().add(menu);
        }
        return ResultEntity.success(rootNode);
    }

    @RequestMapping("/menu/save")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.success();
    }

    @RequestMapping("menu/get/{menuId}")
    public ResultEntity<Menu> updateMenu(@PathVariable Integer menuId) {
        Menu menu = menuService.getMenuById(menuId);
        return ResultEntity.success(menu);
    }

    @RequestMapping("menu/update")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.success();
    }

}
