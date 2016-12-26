package com.rib.wxcs.controller;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rib.wxcs.services.WxUserSV;

@Controller
@RequestMapping(value = "/main")
public class WxUserController {
    private static Logger logger = LoggerFactory.getLogger(WxUserController.class);
    @Autowired
    private WxUserSV wxUserSV;
    
    @RequestMapping(method = RequestMethod.GET)
    public String emMerchantManage(PrintWriter printWriter) {
        return "/main/main";
    }
}
