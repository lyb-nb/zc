package cn.lyb.zc.handler;

import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.entity.Role;
import cn.lyb.zc.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyb
 * @since 2019/8/23 11:01
 */
@Controller
public class RoleController {

    @Resource
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/search/keyword")
    public ResultEntity roleSearch(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        PageInfo<Role> pageInfo = roleService.selectByKeyword(pageNum, pageSize, keyword);
        return ResultEntity.success(pageInfo);
    }

    @ResponseBody
    @RequestMapping("/role/get/list/by/id/list")
    public ResultEntity<List<Role>> getRoleListByIdList(@RequestBody List<Integer> roleIdList) {

        List<Role> roleList = roleService.getRoleListByIdList(roleIdList);

        return ResultEntity.success(roleList);
    }

}
