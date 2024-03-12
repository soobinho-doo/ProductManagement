package product.management.user.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTB {

	private String user_id;
	private String user_pass;
	private String user_nm;
	private String user_phone;
	private String user_email;
	private String user_auth;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
}
