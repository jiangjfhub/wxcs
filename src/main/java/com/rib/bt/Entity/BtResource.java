package com.rib.bt.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rib.wxcs.base.IdEntity;

@Entity
@Table(name="bt_resource")
public class BtResource extends IdEntity {

	private String btName;
	
	private String btCode;
	
	private String btActor;
	
	private String description;
	
	private String imgUrl;
	
	private Date releasedDate;
	
	private Date createDate;
	
	private int state;

	public String getBtName() {
		return btName;
	}

	public void setBtName(String btName) {
		this.btName = btName;
	}

	public String getBtCode() {
		return btCode;
	}

	public void setBtCode(String btCode) {
		this.btCode = btCode;
	}

	public String getBtActor() {
		return btActor;
	}

	public void setBtActor(String btActor) {
		this.btActor = btActor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
