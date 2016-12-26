package com.rib.wxcs.controller;

import java.io.PrintWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rib.wxcs.bean.WxUser;
import com.rib.wxcs.services.WxUserSV;
import com.rib.wxcs.utils.ControllerUtils;


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
    
    @RequestMapping(value="/registerUser",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> createUser(@RequestBody WxUser user,PrintWriter printWriter){
    	try{
    		user.setState(1);
    		wxUserSV.save(user);
//    		printWriter.write("success");
//    		printWriter.flush();
//    		printWriter.close();
        	return ControllerUtils.createReturnObject(ControllerUtils.RESPONSE_CODE_SUCCESS,"success");
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return ControllerUtils.createReturnObject(ControllerUtils.RESPONSE_CODE_FAILED,"fail");
    	}
    }
}
