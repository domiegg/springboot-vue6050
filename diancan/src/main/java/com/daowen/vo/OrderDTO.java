package com.daowen.vo;

import com.daowen.entity.Shorder;

import java.util.List;

public class OrderDTO extends Shorder {



    public List<OrderItemDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderItemDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    private List<OrderItemDTO> orderDetail;

    private String puraccount;

    private String purname;

    public String getPuraccount() {
        return puraccount;
    }

    public void setPuraccount(String puraccount) {
        this.puraccount = puraccount;
    }

    public String getPurname() {
        return purname;
    }

    public void setPurname(String purname) {
        this.purname = purname;
    }


}


