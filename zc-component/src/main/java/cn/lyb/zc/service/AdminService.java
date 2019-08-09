package cn.lyb.zc.service;

import cn.lyb.zc.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author lyb
 * @since 2019/8/7 22:41
 */
public interface AdminService {
    List<Admin> list();

    void updateAdmin();

    Admin login(String loginAcct, String userPswd);

    PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword);

    void batchDelete(List<Integer> adminIdList);
}
