package com.cg.casestudy.bookingmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
	private String code;
	private int discount;
	private int price;

}
