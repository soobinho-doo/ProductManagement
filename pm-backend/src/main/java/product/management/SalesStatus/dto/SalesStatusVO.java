package product.management.SalesStatus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesStatusVO {

	private String years;
	private String months;
	private String days;
	private int sum_price;
	private int sum_commission_price;
	
}
