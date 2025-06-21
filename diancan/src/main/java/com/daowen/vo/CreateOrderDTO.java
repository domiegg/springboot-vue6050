package com.daowen.vo;

import java.util.List;

public class CreateOrderDTO {

    private String remark;
    private int purchaser;
    private String psstyle;
    private int addid;

    public int getAddid() {
        return addid;
    }

    public void setAddid(int addid) {
        this.addid = addid;
    }

    public String getPsstyle() {
        return psstyle;
    }

    public void setPsstyle(String psstyle) {
        this.psstyle = psstyle;
    }

    public List<ShoppingGoodInfo> Goods;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(int purchaser) {
        this.purchaser = purchaser;
    }

    public List<ShoppingGoodInfo> getGoods() {
        return Goods;
    }

    public void setGoods(List<ShoppingGoodInfo> goods) {
        Goods = goods;
    }

   public class ShoppingGoodInfo{
        private int spid;
        private int count;

       public Double getPrice() {
           return price;
       }

       public void setPrice(Double price) {
           this.price = price;
       }

       private Double price;

        public int getSpid() {
            return spid;
        }

        public void setSpid(int spid) {
            this.spid = spid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
