package product.management.common;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class UtilConfig {

	// 하루 남은 시간 구하기
	public int remainingTime() {
		LocalTime now = LocalTime.now();

        int endCookieTime = (now.getHour() * 3600) + (now.getMinute() * 60) + now.getSecond();
        int calTime = 86400 - endCookieTime;
        int currentTime = calTime/3600;
        int currentMinute = calTime/ 60 - currentTime * 60;
        //log.info("하루 남은 시간 = "+currentTime+"시간 "+currentMinute+"분 "+calTime%60+"초");
		
		return calTime;
	}
}
