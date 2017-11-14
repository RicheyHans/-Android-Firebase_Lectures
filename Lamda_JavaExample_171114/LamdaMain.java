
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
		// ���� ����� ���ٽ�
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
			
			// callbaack Ŭ������ lamda�� �����ϱ�
			// 1. �Լ����� ���ְ� () �� {} ���̿� ȭ��ǥ�� �ִ´�.
			// (int x) -> {
			//	System.out.println(x);
			//}
			// 2. �Ķ���Ͱ� �ϳ��� Ÿ���� ������ �� �ִ�.
			// (x) -> {
			//	System.out.println(x);
			//}
			// 3. �ϳ��� �Ķ���͸� �Ķ���� ���� ��ȣ�� ����, ������ �� �� �̸� ���� ���� ��ȣ�� ������ �� �ִ�.
			// x -> System.out.println(x)
			// 4. �Ķ���Ͱ� ������ ��ȣ�� �ݵ�� �ۼ�
			// () -> {System.out.println("Hello")
			// 5. ���� ���� ���� ��� 
			// () -> return "Hello"
			// 6. ������
			// x -> System.out.println(x);
			// 6. ������ :������ ����Ǵ� ���� �ڵ尡 �� �� �̰�, �� �ڵ� ���� ���ڸ� �ϳ��� �޴� �Լ��� ���
			// ��, ������ ����Ǵ� ���� ���ڿ� ���ϵǴ� ���ڸ� ������ �� ������
			// �Ķ������ ������ ���� ������ ���, ��ü :: �޼��� ���·� ȣ���� �� �ִ�.
			// System.out::println
		});
	}

}

// ������ ����
// 1. �ϳ��� Ŭ������ �ϳ��� �Լ��� �־�� �Ѵ�.
// 2. �ڹٿ�����, �������̽��� �����ؼ� ���� �� �ֵ��� ����� �����̴�.

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
