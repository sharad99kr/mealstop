package com.dalhousie.MealStop.review.modal;

import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.modal.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;

@Entity
@Table(name = "customer_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReview
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "review_id")
    private long id;

    @Column(name = "review_score")
    private Integer reviewScore;

    @Column(name = "review_message")
    private String reviewMessage;

    @Column(name = "review_date")
    private Date reviewDate = new Date();

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurantid", nullable = false)
    private Restaurant restaurant;
}
