package org.isp.util;

import org.springframework.ui.Model;

public class ControllerUtil {
    public static Model addErrorToModel(String errorMsg, Model model) {
        model.addAttribute("error", errorMsg);
        return model;
    }
}
