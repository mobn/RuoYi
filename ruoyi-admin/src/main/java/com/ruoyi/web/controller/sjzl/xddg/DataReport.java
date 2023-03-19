package com.ruoyi.web.controller.sjzl.xddg;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author mobong
 * 信贷对公客户数据报备模块
 *
 */
@Controller
@RequestMapping("/sjzl/xddg")
public class DataReport  extends BaseController {
    private String prefix = "sjzl/xddg";

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("sjzl:xddg:view")
    @GetMapping()
    public String xddg()
    {
        return prefix + "/xddg";
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRole role)
    {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /**
     * 新增报备数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 文件上传
     * @param file
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Save file to local file system
            Path path = Paths.get("/path/to/uploads/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            redirectAttributes.addFlashAttribute("message", "File " + fileName + " uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/upload";
    }

}
