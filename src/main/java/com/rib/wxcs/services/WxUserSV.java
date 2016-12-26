package com.rib.wxcs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rib.wxcs.bean.WxUser;
import com.rib.wxcs.repository.WxUserDAO;

@Component
@Transactional
public class WxUserSV {

    @Autowired
    private WxUserDAO wxUserDAO;
    
    public WxUser save(WxUser wxUser){
       return wxUserDAO.save(wxUser);
    }
    
    public WxUser findOneByName(String name){
        return wxUserDAO.findOneByName(name);
    }
}
