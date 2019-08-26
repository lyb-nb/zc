package cn.lyb.zc.service.impl;

import cn.lyb.zc.entity.Role;
import cn.lyb.zc.entity.RoleExample;
import cn.lyb.zc.mapper.RoleMapper;
import cn.lyb.zc.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyb
 * @since 2019/8/23 11:00
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> selectByKeyword(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.selectByKeyword(keyword);
        return new PageInfo<>(roleList);
    }

    @Override
    public List<Role> getRoleListByIdList(List<Integer> roleIdList) {
        // 创建实体类Role对应的Example对象
        RoleExample roleExample = new RoleExample();
        // 在Example对象中封装查询条件
        roleExample.createCriteria().andIdIn(roleIdList);
        // 执行查询
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public void batchRemove(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roleIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public void saveRole(String roleName) {
        roleMapper.insert(new Role(null, roleName));
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
}
