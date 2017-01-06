package com.rib.bt.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.rib.bt.Entity.BtResource;
import com.rib.bt.Entity.ResourceLink;
import com.rib.bt.repositiory.BtResourceDAO;
import com.rib.bt.repositiory.ResourceLinkDAO;
import com.rib.bt.utils.HttpUtils;

@Transactional
@Component
public class BtResourceSV {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private BtResourceDAO btResourceDAO;
	@Autowired
	private ResourceLinkDAO resourceLinkDAO;
	
	public BtResource saveBtResource(BtResource btResource){
		return btResourceDAO.save(btResource);
	}
	public ResourceLink saveResourceLink(ResourceLink link){
		return resourceLinkDAO.save(link);
	}
	public List<ResourceLink> findByResourceId(Long resourceId){
		return resourceLinkDAO.findByResourceId(resourceId);
	}
	public List<BtResource> findByBtCode(String btCode){
		return btResourceDAO.findByBtCode(btCode);
	}
	public List<ResourceLink> searchBtsoAndSaveLink(String url, Long resourceId) throws IOException, ParseException {
		Document btdoc = HttpUtils.requstGetByJsoup(url);
		List<ResourceLink> bean = Lists.newArrayList();
		Elements els = btdoc.getElementsByClass("row");
		List<String> urls = Lists.newArrayList();
		for (Element element : els) {
			Pattern p = Pattern.compile("https://btso.pw/magnet/detail/hash/.[a-zA-Z0-9]*");
			Matcher m = p.matcher(element.toString());
			while (m.find()) {
				urls.add(m.group());
			}
		}
		for (String url1 : urls) {
			ResourceLink resourceLink = new ResourceLink();
			Document docu = HttpUtils.requstGetByJsoup(url1);
			if (docu != null) {
				String magnetLink = "暂无链接";
				String linkName = "";
				String size = "未知";
				String convertDate = "1994-06-06";
				if (docu.getElementById("magnetLink") != null)
					magnetLink = docu.getElementById("magnetLink").text();
				if (docu.select("h3") != null)
					linkName = docu.select("h3").get(0).text();
				String hrefLink = url1;
				if (docu.getElementsByClass("col-md-10 col-sm-9 value") != null
						&& docu.getElementsByClass("col-md-10 col-sm-9 value").size() > 2) {
					size = docu.getElementsByClass("col-md-10 col-sm-9 value").get(1).text();
					convertDate = docu.getElementsByClass("col-md-10 col-sm-9 value").get(2).text();
				}
				resourceLink.setConvertDate(sdf.parse(convertDate));
				resourceLink.setCreateDate(new Date());
				resourceLink.setHrefLink(hrefLink);
				resourceLink.setLinkName(linkName);
				resourceLink.setMagnetLink(magnetLink);
				resourceLink.setSize(size);
				resourceLink.setState(1);
				resourceLink.setResourceId(resourceId);
				resourceLink = saveResourceLink(resourceLink);
				bean.add(resourceLink);
			}
		}
		return bean;
	}
	
	public BtResource searchJavBusAndSave(String url) {
		Document doc = HttpUtils.requstGetByJsoup(url);
		if (null != doc) {
			// 提取信息
			Elements el = doc.getElementsByClass("col-md-3 info");

			Elements els = el.select("p");
			els.get(1).select("span").remove();
			String releaseDate = els.get(1).text();
			String btCode = els.get(0).child(1).text();
			Elements ei = doc.getElementsByClass("bigImage");
			String imgUrl = ei.select("img").attr("src");
			String btName = ei.select("img").attr("title");
			String actorName = doc.getElementsByClass("star-name").text();

			BtResource btResource = new BtResource();
			try {
				btResource.setState(1);
				btResource.setBtActor(actorName);
				btResource.setBtCode(btCode);
				btResource.setBtName(btName);
				btResource.setCreateDate(new Date());
				btResource.setImgUrl(imgUrl);
				btResource.setReleasedDate(sdf.parse(releaseDate));
				btResource = saveBtResource(btResource);
				return btResource;
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

		}
		return null;
	}
}
