package com.dalhousie.MealStop;

import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class MealStopApplication {

	@Autowired
	OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(MealStopApplication.class, args);
	}

	public MealStopApplication(OrderService orderService) {
		this.orderService = orderService;
	}

	//@GetMapping("/")
	public void home(){


		//orderService.addOrder(new Orders(1,3,3,4,100,1));
		//orderService.addOrder(new Orders(2,3,5,5,200,2));
		//orderService.addOrder(new Orders(12,3,5,5,500,0));
		//orderService.addOrder(new Orders(21,3,5,5,600,0));

//		Map<Integer, Float> meals=orderService.getMonthlyReportofRestaurant(3,2022);
//		for (Orders id:meals) {
//			System.out.println("id :"+id.getOrderAmount());
//		}

		//orderService.updateOrderStatus(24,2);
	}

}
