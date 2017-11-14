package observer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	// 등록된 옵저버들
	List<Observer> observers = new ArrayList<>();
	
	// 실행함수
	public void run() {
		while(true) {
			/*
			 * 서버의 변경사항을 체크하는 로직
			 */
			if(/*내 서버에 변경사항이 있으면*/) {
				for(Observer obs : observers) {
					obs.onNext();
				}
			}
		}
	}
	// 옵저버 인터페이스
	public interface Observer{
		public void onNext();		// 데이터를 계속 꺼낼 수 있는지 계속 체크한다.
		public void onComplete();
		public void onError();
	}
}
