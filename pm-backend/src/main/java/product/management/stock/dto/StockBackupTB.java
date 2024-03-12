package product.management.stock.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockBackupTB {
	private Long stock_sq;
	private String user_id;
	private String stock_st;
	private Long product_sq;
	private int stock_no;
	private Date stock_dt;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
}
