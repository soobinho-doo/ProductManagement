package product.management.stock.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTBJoinVO {

	private Long branch_office_sq;
	private String branch_office_nm;
	private String branch_office_area;
	
	private Long product_sq;
	private String user_id;
	private String product_nm;
	private String product_st;
	private int product_price;
	private String product_commission;
	private int product_weight;
	private String product_weight_dt;
	private String product_measure;
	
	private Long stock_sq;
	private String stock_st;
	private int stock_no;
	private Date stock_dt;
}
