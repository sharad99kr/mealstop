package com.dalhousie.MealStop;

import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@RestController
public class MealStopApplication {

	OrderService orderService;
	public static void main(String[] args) {
		SpringApplication.run(MealStopApplication.class, args);
	}

	public MealStopApplication(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/")
	public void home(){

		List<Long> meals=orderService.getMostOrderedMealOfRestaurant(3);
		for (Long id:meals) {
			System.out.println("id :"+id);
		}
//		orderService.addOrder(new Orders(2,3,4,5));
//		orderService.addOrder(new Orders(2,3,5,5));
//		orderService.addOrder(new Orders(2,3,5,5));
//		orderService.addOrder(new Orders(2,3,5,5));

		//Orders order=orderService.getOrderByOrderID(4);
		//System.out.println(order.getCustomerId());
	}

}
