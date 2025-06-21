package com.daowen.service;
import com.daowen.entity.Huiyuan;
import com.daowen.entity.Orderitem;
import com.daowen.entity.Shangpin;
import com.daowen.entity.Shorder;
import com.daowen.util.JsonResult;
import com.daowen.util.SequenceUtil;
import com.daowen.vo.CreateOrderDTO;
import com.daowen.vo.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daowen.mapper.ShorderMapper;
import com.daowen.ssm.simplecrud.SimpleBizservice;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShorderService extends SimpleBizservice<ShorderMapper>{


    @Autowired
    private OrderitemService oiSrv=null;
    @Autowired
    private ShorderMapper orderMapper=null;
    @Autowired
    private ShangpinService shangpinSrv=null;
    @Autowired
    private HuiyuanService huiyuanSrv=null;
    
    public JsonResult createOrder(CreateOrderDTO orderDTO){

        for(CreateOrderDTO.ShoppingGoodInfo sgi :orderDTO.Goods){
            Shangpin shangpin = shangpinSrv.load(" where id=" + sgi.getSpid());
            if (shangpin.getKucun()<sgi.getCount()) {
               return JsonResult.error(-1,"商品库存不足");
            }
        }
        for(CreateOrderDTO.ShoppingGoodInfo sgi :orderDTO.Goods){
            Shangpin shangpin = shangpinSrv.load(" where id=" + sgi.getSpid());
            if (shangpin.getKucun() >= sgi.getCount()) {
               shangpin.setKucun(shangpin.getKucun()-sgi.getCount());
               shangpinSrv.update(shangpin);
            }
        }
        Shorder shorder=new Shorder();
        shorder.setDdno(SequenceUtil.buildSequence("D"));
        shorder.setCreatetime(new Date());
        shorder.setPsstyle(orderDTO.getPsstyle());
        shorder.setPurchaser(orderDTO.getPurchaser());
        shorder.setRemark(orderDTO.getRemark());
        shorder.setAddid(orderDTO.getAddid());
        shorder.setState(1);
        shorder=save(shorder);
        double totalfee=0;
        for(CreateOrderDTO.ShoppingGoodInfo sgi :orderDTO.Goods){
            Orderitem  oi =new Orderitem();
            oi.setOrderid(shorder.getId());
            oi.setCount(sgi.getCount());
            oi.setSpid(sgi.getSpid());
            oi.setPrice(sgi.getPrice());
            oi.setTotalprice(oi.getPrice()*oi.getCount());
            oi.setState(1);
            oiSrv.save(oi);
            totalfee+=oi.getTotalprice();

        }
        shorder.setOriginfee(totalfee);
        Huiyuan huiyuan=huiyuanSrv.loadPlus(shorder.getPurchaser());
        if(huiyuan!=null&&huiyuan.getHytype()!=null){
            totalfee=totalfee*huiyuan.getHytype().getDiscount();
        }
        shorder.setTotalfee(totalfee);
        update(shorder);
        return JsonResult.success(1,"下单成功",shorder);


    }


    public JsonResult validateStock(CreateOrderDTO orderDTO){
        int code=1;
        String des="";
        for(CreateOrderDTO.ShoppingGoodInfo sgi :orderDTO.Goods){
            Shangpin shangpin = shangpinSrv.load(" where id=" + sgi.getSpid());
            if (shangpin.getKucun() < sgi.getCount()) {
                code=-1;
                des+=MessageFormat.format("{0},库存{1,number,#}低于{2,number,#}。  ",shangpin.getName(),shangpin.getKucun(),sgi.getCount());
            }
        }
        return JsonResult.success(code,des);
    }


    public List<OrderDTO> getEntityPlus(HashMap map) {

        List<OrderDTO> purchaseOrder = getMapper().getEntityPlus(map);

        return purchaseOrder;
    }

    public OrderDTO loadPlus(HashMap map) {
       return this.getMapper().loadPlus(map);
    }

    public OrderDTO loadPlus(int id) {
        HashMap map = new HashMap();
        map.put("id",id);
        return this.loadPlus(map);
    }
    
    public double getTotalPrice(int orderId){
        if(orderId<=0)
            return 0;
        List<Orderitem> listOrderitem = oiSrv.getEntity("where orderid=" + orderId);
        double sum=0;
        for (Orderitem orderitem : listOrderitem) {
            sum+=orderitem.getTotalprice();
        }
        return sum;

    }

    public Boolean changeToPayed(int id) {

       int count= oiSrv.executeUpdate(MessageFormat.format("update orderitem set state=2 where orderid={0,number,#} and state=1",id));
       int oc=this.executeUpdate(MessageFormat.format("update shorder set state=2 where id={0,number,#} ",id));
       return count>0?true:false;
    }

    public List<HashMap<String,Object>> saleStat(Map map){
        return orderMapper.saleStat(map);
    }


}
