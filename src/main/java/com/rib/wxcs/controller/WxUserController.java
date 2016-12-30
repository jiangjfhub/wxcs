package com.rib.wxcs.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    @Autowired
    private WxUserSV wxUserSV;
    
    @RequestMapping(method = RequestMethod.GET)
    public String emMerchantManage(PrintWriter printWriter) {
        return "/main/main";
    }
    
    @RequestMapping(value="/registerUser",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> createUser(@RequestBody WxUser user){
    	try{
    		user.setState(1);
    		user.setCreateDate(new Date());
    		String expireDate = "2099-12-31 23:59:59";
    		user.setExpireDate(sdf.parse(expireDate));
    		wxUserSV.save(user);
        	return ControllerUtils.createReturnObject(ControllerUtils.RESPONSE_CODE_SUCCESS, "注册成功");
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		return ControllerUtils.createReturnObject(ControllerUtils.RESPONSE_CODE_FAILED, "注册失败");
    	}
    }
}
