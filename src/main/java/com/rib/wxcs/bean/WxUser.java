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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

     
}
