package product.management.product.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTB {

	private Long product_sq;
	private String user_id;
	private Long branch_office_sq;
	private String branch_office_nm;
	private String product_nm;
	private String product_st;
	private int product_price;
	private String product_commission;
	private String product_weight;
	private String product_weight_dt;
	private String product_measure;
	private int product_stock;
	private int product_sell;
	private int product_recall;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
	
}
