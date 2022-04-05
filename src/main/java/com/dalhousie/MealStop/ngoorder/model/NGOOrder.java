package com.dalhousie.MealStop.ngoorder.model;


import javax.persistence.*;

@Entity
@Table(name="ngoorder")
public class NGOOrder implements INGOOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="ngoId")
    private long ngoId;

    @Column(name="orderId")
    private long orderId;

    @Column(name="status")
    private int status;

    @Override
    public long getNGOId(){
        return ngoId;
    }

    @Override
    public void setNGOId(long ngoId){
        this.ngoId=ngoId;
    }

    @Override
    public long getOrderId(){
        return orderId;
    }

    @Override
    public void setOrderId(long orderId){
        this.orderId=orderId;
    }

    public NGOOrder(long orderId, long ngoId, int status){
        this.orderId=orderId;
        this.ngoId=ngoId;
        this.status=status;
    }

    public NGOOrder(){

    }
}
