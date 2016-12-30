package com.rib.wxcs.geetest;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.SkeletonNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rib.wxcs.bean.WxUser;
import com.rib.wxcs.services.WxUserSV;



@Controller
@RequestMapping(value = "/verifyLoginManage")
public class VerifyLoginManageController {
    private static Logger logger = LoggerFactory.getLogger(VerifyLoginManageController.class);
    private static final long serialVersionUID = 244554953219893949L;

    @Autowired
    private WxUserSV wxUserSV;
    
    @RequestMapping(method = RequestMethod.GET)
    public void startCaptcha(HttpServletRequest request, HttpServletResponse response,
            PrintWriter printWriter) throws ServletException, IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());

        String resStr = "{}";
        
        //自定义userid
        String userid = "madebyjjf";

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);
        
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);
        
        resStr = gtSdk.getResponseStr();

        printWriter.println(resStr);
    }
    /**
     * 使用post方式，返回验证结果, request表单中必须包含challenge, validate, seccode
     */
    @RequestMapping(value="/verifyLogin", method = RequestMethod.POST)
    public String verifyLogin(HttpServletRequest request, HttpServletResponse response,Model model,
            PrintWriter printWriter) throws ServletException, IOException {
        String page ="/main/main";
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(),
                GeetestConfig.getGeetest_key());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        // 从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession()
                .getAttribute(gtSdk.gtServerStatusSessionKey);

        // 从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            // gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        if (gtResult == 1) {
            // 验证成功
            Map<String, String> map = verifyByNameAndPassWord(request);
            map.put("status", "success");
            map.put("version", gtSdk.getVersionInfo());
            model.addAllAttributes(map);
            if(map.get("code").equals("0000")){
                page = "/mian/index";
            }
            
            //printWriter.println(data.toString());

        } else {
            // 验证失败
            Map<String,String> data = new HashMap<String,String>();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
                model.addAllAttributes(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return page;
    }
    
    private Map<String, String> verifyByNameAndPassWord(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String message = "";
        String code = "0000";// 0000表示成功
        WxUser wxUser = wxUserSV.findOneByName(userName);
        if (wxUser != null) {
            String pw = wxUser.getPassWord();
            if (pw.equals(passWord)) {
                message = "登录成功";
            } else {
                message = "密码错误";
                code = "0002";
            }
        } else {
            message = "用户名不正确";
            code = "0001";
        }
        result.put("userName", userName);
        result.put("passWord", passWord);
        result.put("message", message);
        result.put("code", code);
        return result;
    }
}
