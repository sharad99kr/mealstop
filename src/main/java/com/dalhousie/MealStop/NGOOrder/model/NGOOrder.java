package com.dalhousie.MealStop.NGOOrder.model;


import com.dalhousie.MealStop.ngo.modal.NGO;

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
        return ngoId;
    }

    @Override
    public void setOrderId(long ngoId){
        this.ngoId=ngoId;
    }


//    @ManyToOne
//    @JoinColumn(name = "ngoId", referencedColumnName = "id", nullable = true,insertable = true,updatable = true)
//    private NGO ngo;


    public NGOOrder(long orderId, long ngoId, int status){
        this.orderId=orderId;
        this.ngoId=ngoId;
        this.status=status;
    }



    public NGOOrder(){

    }

//    @Override
//    public String toString()
//    {
//        StringBuilder sb = new StringBuilder();
//        sb.append("NGOOrder [id=" + orderId);
//        sb.append(", ngoId=" + ngoId);
//        sb.append(", status=" + status+"]");
//        return sb.toString();
//    }
}
