package cn.lyb.zc.handler;

import cn.lyb.zc.common.Constant;
import cn.lyb.zc.entity.Admin;
import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lyb
 * @since 2019/8/7 23:26
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/get/list")
    public Object list(Model model) {
        List<Admin> list = adminService.list();
        model.addAttribute("list", list);
        return "admin-target";
    }

    @GetMapping("/to/admin/login")
    public String toAdminLogin() {
        return "admin-login";
    }

    @PostMapping(value = "admin/login")
    public String adminLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            Model model,
            HttpSession session) {
        Admin admin = adminService.login(loginAcct, userPswd);

        // 判断admin是否为null
        if (admin == null) {
            model.addAttribute(Constant.ATTR_NAME_MESSAGE, Constant.MESSAGE_LOGIN_FAILED);
            return "admin-login";
        }
        session.setAttribute(Constant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/main";
    }

    @GetMapping("/admin/main")
    public String adminMain() {
        return "admin-main";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index-page.jsp";
    }

    @RequestMapping(value = "/admin/page/query", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryForKeywordSearch(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {
        PageInfo<Admin> pageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
        model.addAttribute(Constant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @PostMapping(value = "/admin/batch/delete")
    @ResponseBody
    public ResultEntity<String> batchDelete(@RequestBody List<Integer> adminIdList) {
        try {
            adminService.batchDelete(adminIdList);
            return ResultEntity.success();
        } catch (Exception ex) {
            return ResultEntity.fail("批量删除失败", ex.getMessage());
        }
    }
}
