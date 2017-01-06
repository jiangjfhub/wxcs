package com.rib.wxcs.bean;

import java.util.Date;

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
     private Date createDate;
     private Date expireDate;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

     
}
