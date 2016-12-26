package com.rib.wxcs.bean;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.rib.wxcs.base.IdEntity;
import lombok.Data;

@Data
@Entity
@Table(name="wx_user")
public class WxUser extends IdEntity{
    
     private String name;
     private String passWord;
     private Integer state;

}
