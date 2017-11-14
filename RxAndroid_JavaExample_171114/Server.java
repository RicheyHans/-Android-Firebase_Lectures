package observer;

import java.util.ArrayList;
import java.util.List;

public class Server {
	// ��ϵ� ��������
	List<Observer> observers = new ArrayList<>();
	
	// �����Լ�
	public void run() {
		while(true) {
			/*
			 * ������ ��������� üũ�ϴ� ����
			 */
			if(/*�� ������ ��������� ������*/) {
				for(Observer obs : observers) {
					obs.onNext();
				}
			}
		}
	}
	// ������ �������̽�
	public interface Observer{
		public void onNext();		// �����͸� ��� ���� �� �ִ��� ��� üũ�Ѵ�.
		public void onComplete();
		public void onError();
	}
}