package com.rib.bt.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.rib.bt.Entity.BtResource;
import com.rib.bt.Entity.ResourceLink;
import com.rib.bt.service.BtResourceSV;
import com.rib.bt.utils.HttpUtils;
import com.rib.wxcs.utils.ControllerUtils;

@Controller
@RequestMapping(value = "/myBtPage")
public class BtManageController {

	private static Logger logger = LoggerFactory.getLogger(BtManageController.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private BtResourceSV btResourceSV;

	@RequestMapping(method = RequestMethod.GET)
	public String emMerchantManage(PrintWriter printWriter) {
		return "/bt/btManage";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchByBtCode(String btCode) {

		btCode = btCode.toUpperCase();
		String url1 = "https://www.seedmm.com/" + btCode;
		String url2 = "https://btso.pw/search/" + btCode;
		List<ResourceLink> links = null;

		try {
			BtResource btResource = null;
			List<BtResource> btList = btResourceSV.findByBtCode(btCode);
			if (btList != null && btList.size() > 0) {
				btResource = btList.get(0);
			} else {
				btResource =btResourceSV.searchJavBusAndSave(url1);
			}

			List<ResourceLink> respirceLinks = btResourceSV.findByResourceId(btResource.getId());
			if (respirceLinks != null && respirceLinks.size() > 0) {
				links = respirceLinks;
			} else {
				links = btResourceSV.searchBtsoAndSaveLink(url2, btResource.getId());
			}
		} catch (IOException | ParseException e) {
			logger.error(e.getMessage());
		}
		return ControllerUtils.createReturnObject(ControllerUtils.RESPONSE_CODE_SUCCESS, "查询成功", links);

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
				resourceLink = btResourceSV.saveResourceLink(resourceLink);
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
				btResource = btResourceSV.saveBtResource(btResource);
				return btResource;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

		}
		return null;
	}
}
