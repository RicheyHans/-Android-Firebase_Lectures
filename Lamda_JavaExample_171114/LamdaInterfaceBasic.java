import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LamdaInterfaceBasic {
	public void run() {
		/*
		 * ���� �������̽�
		 */
		// 1. Supplier : �Է°��� ����, ��ȯ���� ���� �� ���
		Supplier<Integer> supplier = () -> 180+20;
		System.out.println(supplier.get());
		
		// 2. Consumer : �Է°��� �ְ�, ��ȯ���� ����.
		//				 �ڵ� �� ������ ���ó���� �Ǿ�� �Ѵ�.
		Consumer<Integer> consumer = System.out::println;
		consumer.accept(100);;
		
		// 3. Function : �Է°��� �ְ�, ��ȯ���� �ִ�.
		//				 �Է°��� ��ȯ���� Ÿ���� ���׸����� ������ ��� �Ѵ�.
		Function<Integer, Double> function = x -> x * 0.5;
		System.out.println(function.apply(50));
		
		// 4. Predicate : �Է°��� ���� ��, ������ �Ǵ�. return Ÿ���� boolean�̶�� �ǹ�
		Predicate<Integer> predicate = x -> x < 100;
		System.out.println("50�� 100���� �۴�? : "+predicate.test(50));
		
		// 5. �Է°� ��ȯ Ÿ���� ������ �� ���
		UnaryOperator<Integer> unary = x -> x + 20;
		System.out.println("unary : "+unary.apply(100));
		
		/*
		 * ���� �������̽�
		 */
		
		// 1. BiConsumer : Consumer�� �Է� ���� �� �� 
		BiConsumer<Integer, Integer> biConsumer = (x,y) -> {System.out.println(x+y);};
		biConsumer.accept(30, 28);
		// 2. BiFunction : Function�� �Է� ���� �� ��
		// 3. BiPredicate : Predicate�� �Է� ���� �� ��
		// 4. BinarryOperator : "
		
	}
}
