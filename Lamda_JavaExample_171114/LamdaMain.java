
public class LamdaMain {

	public static void main(String[] args) {
		LamdaInterfaceBasic lib = new LamdaInterfaceBasic();
		lib.run();
		// TODO Auto-generated method stub
		/*
		One one = new One() {
			public void run() {
				
			}
		};
		// 위를 축약한 람다식
		One one = () -> {
			System.out.println("Hello");
		};
		one.run();
		*/
		
		/*
		Runnable one = ()->{
			System.out.println("Hello");
		};
		
		new Thread(one).start();
		*/
		// One one = (x)->{System.out.println(x);};
		
		OneProcess process = new OneProcess();
		process.process(x->System.out.println(x));
		
		process.process(new One(){
			public void run(int x) {
				System.out.println(x);
			}
			
			// callbaack 클래스를 lamda로 변경하기
			// 1. 함수명을 없애고 () 와 {} 사이에 화살표를 넣는다.
			// (int x) -> {
			//	System.out.println(x);
			//}
			// 2. 파라미터가 하나면 타입을 생략할 수 있다.
			// (x) -> {
			//	System.out.println(x);
			//}
			// 3. 하나의 파라미터면 파라미터 측의 괄호를 생략, 로직이 한 줄 이면 로직 측의 괄호를 생략할 수 있다.
			// x -> System.out.println(x)
			// 4. 파라미터가 없으면 괄호를 반드시 작성
			// () -> {System.out.println("Hello")
			// 5. 리턴 값이 있을 경우 
			// () -> return "Hello"
			// 6. 참조형
			// x -> System.out.println(x);
			// 6. 참조형 :로직이 실행되는 측의 코드가 한 줄 이고, 그 코드 또한 인자를 하나만 받는 함수일 경우
			// 즉, 로직이 실행되는 측의 인자와 리턴되는 인자를 매핑할 수 있으면
			// 파라미터의 개수가 예측 가능할 경우, 객체 :: 메서드 형태로 호출할 수 있다.
			// System.out::println
		});
	}

}

// 람다의 조건
// 1. 하나의 클래스에 하나의 함수가 있어야 한다.
// 2. 자바에서는, 인터페이스를 구현해서 넣을 수 있도록 만든게 람다이다.

/*
interface One{
	public void run();
}
*/

class OneProcess{
	public void process(One one) {
		
		one.run(1000);
	}
}

interface One{
	public void run(int x);
}
