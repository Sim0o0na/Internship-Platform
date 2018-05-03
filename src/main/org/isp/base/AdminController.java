package org.isp.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public AdminController() {
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private String base() {
        return "admin/admin-base";
    }
}
