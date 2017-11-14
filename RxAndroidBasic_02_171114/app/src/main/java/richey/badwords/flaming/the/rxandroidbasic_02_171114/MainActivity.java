package richey.badwords.flaming.the.rxandroidbasic_02_171114;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    List<String> months = new ArrayList<>();

    String[] monthString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 1월부터 12월 가져오기
        DateFormatSymbols dfs = new DateFormatSymbols();
        monthString = dfs.getMonths();


        // 1. 발행자
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    for (String month : monthString) {
                        e.onNext(month);
                        Thread.sleep(1000);
                    }
                    e.onComplete();     // 완료
                } catch (Exception ex) {
                    e.onError(ex.getCause());
                }
            }
        });

        // 2. 구독자 - 발행자에서 onNext호출하면서 구독자의 onNext가 호출된다.
        observable
                .subscribeOn(Schedulers.io())   // Observable의 스레드를 지정
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                str -> {
                        months.add(str);
                        adapter.setDataAndRefresh(months);
                }
        );       // str은 JAN을 가져온다.
    }
}
