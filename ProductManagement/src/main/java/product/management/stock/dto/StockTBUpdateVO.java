package product.management.stock.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTBUpdateVO {

	// 전 데이터 
	private String before_stock_st;
	private Long before_product_sq;
	private int before_stock_no;
	private Date before_stock_dt;
	
	// 후 데이터
	private Long stock_sq;
	private String stock_st;
	private Long product_sq;
	private int stock_no;
	private Date stock_dt;
	
}
