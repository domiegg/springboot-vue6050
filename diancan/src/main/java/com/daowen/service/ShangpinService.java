package com.daowen.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daowen.entity.Browserec;
import com.daowen.entity.Huiyuan;
import com.daowen.entity.Lanmu;
import com.daowen.util.MyUserCF;
import com.daowen.util.ViewHistory;
import com.daowen.vo.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daowen.mapper.ShangpinMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import javax.servlet.http.HttpServletRequest;


@Service
public class ShangpinService extends SimpleBizservice<ShangpinMapper> implements ISiteSearch, ILanmuAdapter {

	@Autowired
	private HuiyuanService huiyuanSrv=null;
	@Autowired
	private LanmuService lanmuSrv=null;
	@Autowired
	private ShangpinMapper shangpinMapper=null;
	@Autowired
	private BrowserecService browserecSrv=null;

	private String prefix="shangpin_browser_";
	public ShangpinService view(Huiyuan huiyuan, String id){
		if(huiyuan==null)
			return this;
		Browserec browserec = browserecSrv.load(MessageFormat.format("  where  hyid={0,number,#} and targetid={1} and type=''shangpin''  " ,huiyuan.getId(),id));
		if(browserec==null) {
			browserec = new Browserec();
			browserec.setTargetid(Integer.valueOf(id));
			browserec.setHyid(huiyuan.getId());
			browserec.setType("shangpin");
			browserec.setCount(1);
			browserecSrv.save(browserec);
		}
		else{
			browserec.setCount(browserec.getCount()+1);
			browserecSrv.update(browserec);
		}
		return this;
	}

	public List<ShangpinVo> getRecomment(HttpServletRequest request, Huiyuan huiyuan) {

		if(huiyuan==null)
			return null;

		List<ShangpinVo> listShangpin = getEntityPlus(null);
		List<Huiyuan> listHuiyuan=huiyuanSrv.getEntityPlus(null);
		Map<String, Map<String,Double>> mapUsers = new HashMap<>();
		for(Huiyuan h: listHuiyuan) {
			HashMap map = new HashMap();
			map.put("hyid",h.getId());
			map.put("type","shangpin");
			List<Browserec> list = browserecSrv.getEntityPlus(map);
			HashMap<String,Double> mapYingpian=new HashMap<>();
			for (ShangpinVo shangpinVo : listShangpin) {

				Browserec browserec = list.stream()
						.filter(br->{
							return br.getHyid()==h.getId()&&br.getTargetid()==shangpinVo.getId();
						}).findAny().orElse(null);
				if(browserec!=null)
					mapYingpian.put(String.valueOf(shangpinVo.getId()),Double.valueOf(browserec.getCount()));
				else
					mapYingpian.put(String.valueOf(shangpinVo.getId()),0.0);

			}
			mapUsers.put(String.valueOf(h.getId()),mapYingpian);
		}

		String key= MyUserCF.Similar(mapUsers,String.valueOf(huiyuan.getId()));
		System.out.print("相似用户"+key);
		if(key==null)
			return new ArrayList<>();
		HashMap map2=new HashMap();
		map2.put("hyid",key);
		map2.put("type","shangpin");
		List<Browserec> listRc = browserecSrv.getEntityPlus(map2);
		map2.clear();
		List<Integer> ids= listRc.stream().map(Browserec::getId).collect(Collectors.toList());
		map2.put("ids",ids);
		return getEntityPlus(map2);
	}



	public List<ShangpinVo> getRecomment2(Huiyuan huiyuan,Integer id) {

		if(huiyuan==null)
			return null;
		FastByIDMap<PreferenceArray> fastMap =new FastByIDMap<PreferenceArray>();
		List<ShangpinVo> listShangpin = getEntityPlus(null);
		List<Huiyuan> listHuiyuan=huiyuanSrv.getEntityPlus(null);
		HashMap map = new HashMap();
		map.put("type","shangpin");
		List<Browserec> list = browserecSrv.getEntityPlus(map);
		Map<String, Map<String,Double>> mapUsers = new HashMap<>();
		for(Huiyuan h: listHuiyuan) {
			PreferenceArray preferences = new GenericUserPreferenceArray(listShangpin.size());
			preferences.setUserID(0, h.getId());
			int j=0;
			for (ShangpinVo shangpinVo : listShangpin) {
				preferences.setItemID(j,shangpinVo.getId());
				Browserec browserec = list.stream()
						.filter(br->{
							return br.getHyid()==h.getId()&&br.getTargetid()==shangpinVo.getId();
						}).findAny().orElse(null);
				if(browserec!=null)
					preferences.setValue(j, (float) (browserec.getCount()*2));
				else
					preferences.setValue(j,0);
				j++;
			}
			fastMap.put(h.getId(),preferences);
		}
		DataModel dataModel=new GenericDataModel(fastMap);
		try {
			ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

			List<RecommendedItem> listRecommendedItem = recommender.recommendedBecause(huiyuan.getId(), id, 5);
			listRecommendedItem.forEach(System.out::print);
			List<Long> ids= listRecommendedItem.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
			map.clear();
			map.put("ids",ids);
			map.put("state",1);
			return getEntityPlus(map);

		} catch (TasteException e) {
			e.printStackTrace();
		}
		return null;
	}



	public ShangpinVo loadPlus(int id){
		HashMap map=new HashMap();
		map.put("id",id);
		return shangpinMapper.loadPlus(map);
	}
	public List<ShangpinVo> getEntityPlus(HashMap map){
	   return shangpinMapper.getEntityPlus(map);
	}

	public List<ShangpinVo> findByTypeid(int typeId) {
		HashMap map=new HashMap();
		map.put("typeid",typeId);
		map.put("state",1);
		return getEntityPlus(map);
	}

	@Override
	public List<SearchVo>  search(String text){
		HashMap map=new HashMap();
		map.put("spname",text);
		List<ShangpinVo> listShangpin= getEntityPlus(map);
		if(listShangpin==null)
			return null;
		List<SearchVo> listSearch=new ArrayList<>();
		for(ShangpinVo shangpin :listShangpin){
			SearchVo searchVo=new SearchVo();
			searchVo.setId(shangpin.getId());
			searchVo.setName(shangpin.getName());
			searchVo.setHref("/e/shangpininfo?id="+ shangpin.getId());
			if(shangpin.getImages()!=null&&shangpin.getImages().size()>0)
				searchVo.setTupian(shangpin.getImages().get(0));
			else
				searchVo.setTupian(shangpin.getTupian());
			searchVo.setXtype(2);
			listSearch.add(searchVo);
		}
		return  listSearch;
	}


	@Override
	public int getDefineCode() {
		return 2;
	}

	@Override
	public LanmuContentVo getLanmuContent(Lanmu lanmu) {

		if(lanmu==null)
			return null;
		HashMap<String,Object> map=new HashMap<>();
		map.put("typeid",lanmu.getId());
		map.put("state",1);
		map.put("order","order by id desc");
		map.put("topcount",10);
		List<ShangpinVo> listShangpin= getEntityPlus(map);

		List<ImageTextItem> listIti=new ArrayList<>();
		listShangpin.forEach(c->{
			ImageTextItem iti=new ImageTextItem();
			iti.setName(c.getName());
			iti.setHref("/e/shangpininfo?id="+c.getId());
			if(c.getImages()!=null)
				iti.setTupian(c.getImages().get(0));
			else
			   iti.setTupian(c.getTupian());
			iti.setPrice(c.getHyjia());
			iti.setPubtime(c.getPubtime());
			iti.setClickCount(0);
			listIti.add(iti);
		});
		return new LanmuContentVo<ImageTextItem>(lanmu,listIti);

	}

	@Override
	public List<LanmuVo> getMyLanmus() {
		HashMap<String,Object> map=new HashMap<>();
		map.put("type",this.getDefineCode());
		return lanmuSrv.getEntityPlus(map);
	}


}
