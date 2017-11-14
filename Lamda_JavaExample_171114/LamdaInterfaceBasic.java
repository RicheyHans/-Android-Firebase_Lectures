import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LamdaInterfaceBasic {
	public void run() {
		/*
		 * 단항 인터페이스
		 */
		// 1. Supplier : 입력값이 없고, 반환값이 있을 때 사용
		Supplier<Integer> supplier = () -> 180+20;
		System.out.println(supplier.get());
		
		// 2. Consumer : 입력값이 있고, 반환값이 없다.
		//				 코드 블럭 내에서 사용처리가 되어야 한다.
		Consumer<Integer> consumer = System.out::println;
		consumer.accept(100);;
		
		// 3. Function : 입력값도 있고, 반환값도 있다.
		//				 입력값과 반환값의 타입을 제네릭으로 정의해 줘야 한다.
		Function<Integer, Double> function = x -> x * 0.5;
		System.out.println(function.apply(50));
		
		// 4. Predicate : 입력값에 대한 참, 거짓을 판단. return 타입이 boolean이라는 의미
		Predicate<Integer> predicate = x -> x < 100;
		System.out.println("50은 100보다 작다? : "+predicate.test(50));
		
		// 5. 입력과 반환 타입이 동일할 때 사용
		UnaryOperator<Integer> unary = x -> x + 20;
		System.out.println("unary : "+unary.apply(100));
		
		/*
		 * 이항 인터페이스
		 */
		
		// 1. BiConsumer : Consumer에 입력 값이 두 개 
		BiConsumer<Integer, Integer> biConsumer = (x,y) -> {System.out.println(x+y);};
		biConsumer.accept(30, 28);
		// 2. BiFunction : Function에 입력 값이 두 개
		// 3. BiPredicate : Predicate에 입력 값이 두 개
		// 4. BinarryOperator : "
		
	}
}
