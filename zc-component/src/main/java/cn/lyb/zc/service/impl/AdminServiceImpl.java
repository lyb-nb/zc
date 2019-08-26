package cn.lyb.zc.service.impl;

import cn.lyb.zc.entity.Admin;
import cn.lyb.zc.entity.AdminExample;
import cn.lyb.zc.entity.AdminExample.Criteria;
import cn.lyb.zc.mapper.AdminMapper;
import cn.lyb.zc.service.AdminService;
import cn.lyb.zc.utils.Md5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author lyb
 * @since 2019/8/7 22:41
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public List<Admin> list() {
        return adminMapper.selectByExample(new AdminExample());
    }


    @Override
    public Admin login(String loginAcct, String userPswd) {
        AdminExample example = new AdminExample();
        example.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(adminList))
            return null;
        Admin admin = adminList.get(0);
        if (null == admin)
            return null;
        String adminPswd = admin.getUserPswd();
        String md5Pswd = Md5Util.md5(userPswd);
        if (Objects.equals(adminPswd, md5Pswd))
            return admin;
        return null;
    }

    @Override
    public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
        return new PageInfo<>(adminList);
    }

    @Override
    public void batchDelete(List<Integer> adminIdList) {
        AdminExample example = new AdminExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdIn(adminIdList);
        adminMapper.deleteByExample(example);
    }

    @Override
    public void saveAdmin(Admin admin) {
        // 对密码进行加密
        String userPswd = admin.getUserPswd();
        userPswd = Md5Util.md5(userPswd);
        admin.setUserPswd(userPswd);

        adminMapper.insert(admin);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        // 对密码进行加密
        String userPswd = admin.getUserPswd();
        userPswd = Md5Util.md5(userPswd);
        admin.setUserPswd(userPswd);

        adminMapper.updateByPrimaryKey(admin);
    }


}
