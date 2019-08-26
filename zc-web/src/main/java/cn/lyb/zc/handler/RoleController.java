package cn.lyb.zc.handler;

import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.entity.Role;
import cn.lyb.zc.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyb
 * @since 2019/8/23 11:01
 */
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/role/search/keyword")
    public ResultEntity roleSearch(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        PageInfo<Role> pageInfo = roleService.selectByKeyword(pageNum, pageSize, keyword);
        return ResultEntity.success(pageInfo);
    }

    @RequestMapping("/role/get/list/by/id/list")
    public ResultEntity<List<Role>> getRoleListByIdList(@RequestBody List<Integer> roleIdList) {

        List<Role> roleList = roleService.getRoleListByIdList(roleIdList);

        return ResultEntity.success(roleList);
    }

    @RequestMapping("role/batch/remove")
    public ResultEntity batchRemove(@RequestBody List<Integer> roleIdList) {
        roleService.batchRemove(roleIdList);
        System.out.println(10 / 0);
        return ResultEntity.success();
    }

    @RequestMapping("/role/save/role")
    public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName) {
        roleService.saveRole(roleName);
        return ResultEntity.success();
    }

    @RequestMapping("/role/update/role")
    public ResultEntity<String> updateRole(Role role) {
        roleService.updateRole(role);
        return ResultEntity.success();
    }

}
