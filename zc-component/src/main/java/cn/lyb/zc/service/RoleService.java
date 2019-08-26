package cn.lyb.zc.service;

import cn.lyb.zc.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author lyb
 * @since 2019/8/23 11:00
 */
public interface RoleService {
    PageInfo<Role> selectByKeyword(Integer pageNum, Integer pageSize, String keyword);

    List<Role> getRoleListByIdList(List<Integer> roleIdList);

    void batchRemove(List<Integer> roleIdList);

    void saveRole(String roleName);

    void updateRole(Role role);
}
