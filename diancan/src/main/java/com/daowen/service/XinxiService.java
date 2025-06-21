package com.daowen.service;
import com.daowen.entity.*;
import com.daowen.jdbc.simplecrud.DataRow;
import com.daowen.jdbc.simplecrud.DataTable;
import com.daowen.jdbc.simplecrud.SimpleJdbc;
import com.daowen.util.BeansUtil;
import com.daowen.vo.*;
import com.github.pagehelper.PageHelper;
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
import com.daowen.mapper.XinxiMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class XinxiService extends SimpleBizservice<XinxiMapper> implements  ISiteSearch, ILanmuAdapter {

    @Autowired
    private XinxiMapper xinxiMapper;

    @Autowired
    private HuiyuanService huiyuanSrv=null;
    private String prefix="xinxi_browser_";
    @Autowired
    private BrowserecService browserecSrv=null;

    @Autowired
    private LanmuService lanmuSrv=null;

    public List<XinxiVo> getEntityPlus(HashMap map){
        return xinxiMapper.getEntityPlus(map);
    }
    public  List<XinxiVo> findXinxi(int lmid){
        HashMap<String,Object> map=new HashMap();
        map.put("lmid",lmid);
        map.put("state",2);
        return xinxiMapper.getEntityPlus(map);
    }
    public XinxiVo loadPlus(int id){
        HashMap<String,Object> map=new HashMap();
        map.put("id",id);
        return xinxiMapper.loadPlus(map);
    }


    public List<XinxiVo> getRecomment(Integer hyid,Integer id) {

        if(hyid==null)
            return null;
        FastByIDMap<PreferenceArray> fastMap =new FastByIDMap<PreferenceArray>();
        List<XinxiVo> listShangpin = getEntityPlus(null);
        List<Huiyuan> listHuiyuan=huiyuanSrv.getEntityPlus(null);
        HashMap map = new HashMap();
        map.put("type","xinxi");
        List<Browserec> list = browserecSrv.getEntityPlus(map);
        Map<String, Map<String,Double>> mapUsers = new HashMap<>();
        for(Huiyuan h: listHuiyuan) {
            PreferenceArray preferences = new GenericUserPreferenceArray(listShangpin.size());
            preferences.setUserID(0, h.getId());
            int j=0;
            for (XinxiVo shangpinVo : listShangpin) {
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

            List<RecommendedItem> listRecommendedItem = recommender.recommendedBecause(hyid, id, 5);
            listRecommendedItem.forEach(System.out::print);
            List<Long> ids= listRecommendedItem.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
            map.clear();
            map.put("ids",ids);
            return getEntityPlus(map);

        } catch (TasteException e) {
            e.printStackTrace();
        }
        return null;
    }




    public  int getCount(int lmid ){


        return getRecordCount(MessageFormat.format(" where  lmid={0} ",lmid));



    }


    public List<Xinxi> getRelated(int id){
        int maxSize=this.getRecordCount("");
        int count=3;
        if(maxSize<count)
            count=maxSize;

        List<Integer> listPos= random(count,maxSize);
        List<Xinxi> list=getEntity("");
        List<Xinxi> listRan=new ArrayList<>();
        int index=0;
        for(Xinxi xinxi :list){
            if(has(listPos,index++))
                listRan.add(xinxi);
        }
        return listRan;
    }
    private  boolean has(List<Integer> list,Integer val){
        for(Integer i :list){
            if(val==i)
                return true;
        }
        return false;
    }

    private List<Integer> random(int count,int maxSize){
        Random r = new Random();
        ArrayList<Integer> h = new ArrayList<>();
        if(count>maxSize)
            count=maxSize;
//        循环生成随机数
        while (h.size() < count) {
            LinkedHashSet<Integer> set = new LinkedHashSet<>();//利用set集合去重
            h.add(r.nextInt((maxSize) + 1));//获取1至20的随机数，nextInt（20）的取值是0至19，所有后面+1
            set.addAll(h);//利用set集合 addAll h集合，达到去重目的
            h.clear();//清空h集合
            h.addAll(set);//再将set集合元素还给h集合
        }

        Collections.sort(h, Collections.reverseOrder());//获取随机数的降序排序
        System.out.println("降序为后：" + h);
        return h;
    }



    public List<SearchVo>  search(String title){

        HashMap map=new HashMap();
        map.put("keyword",title);
        map.put("state",2);
        List<XinxiVo> listXinxi=getEntityPlus(map);
        if(listXinxi==null)
            return null;
        List<SearchVo> listSearch=new ArrayList<>();
        for(XinxiVo xinxi :listXinxi){
            SearchVo searchVo=new SearchVo();
            searchVo.setId(xinxi.getId());
            searchVo.setName(xinxi.getTitle());
            searchVo.setHref("/e/xinxiinfo.jsp?id="+ xinxi.getId());
            if(xinxi.getImages()!=null&&xinxi.getImages().size()>0)
              searchVo.setTupian(xinxi.getImages().get(0));
            else
                searchVo.setTupian(xinxi.getTupian());
            searchVo.setXtype(1);
            listSearch.add(searchVo);
        }
        return  listSearch;
    }

    @Override
    public int getDefineCode() {
        return LanmuType.NEWS;
    }

    @Override
    public LanmuContentVo<ImageTextItem> getLanmuContent(Lanmu lanmu) {
        if(lanmu==null)
            return null;
        HashMap map=new HashMap<String,Object>();
        map.put("lmid",lanmu.getId());
//        map.put("tuijian",1);
        map.put("state",2);
        map.put("order"," order by id desc ");
        map.put("topcount",10);
        List<XinxiVo> listXinxi= getEntityPlus(map);
        List<ImageTextItem> listIti=new ArrayList<>();
        listXinxi.forEach(c->{
            ImageTextItem iti=new ImageTextItem();
            iti.setName(c.getTitle());
            iti.setHref("/e/xinxiinfo?id="+c.getId());
            if(c.getImages()!=null&&c.getImages().size()>0)
                iti.setTupian(c.getImages().get(0));
            else
                iti.setTupian(c.getTupian());
            iti.setPrice(0);
            iti.setPubtime(c.getPubtime());
            iti.setClickCount(c.getClickcount());
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

    public XinxiService view(int hyid, String id){

        Browserec browserec = browserecSrv.load(MessageFormat.format("  where  hyid={0,number,#} and targetid={1} and type=''xinxi''  " ,hyid,id));
        if(browserec==null) {
            browserec = new Browserec();
            browserec.setTargetid(Integer.valueOf(id));
            browserec.setHyid(hyid);
            browserec.setType("xinxi");
            browserec.setCount(1);
            browserecSrv.save(browserec);
        }
        else{
            browserec.setCount(browserec.getCount()+1);
            browserecSrv.update(browserec);
        }
        return this;
    }
    public List<HashMap<String, Object>> countstat(HashMap<String, Object> map) {

        return xinxiMapper.countstat(map);
    }
}
