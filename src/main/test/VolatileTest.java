import com.nix.util.HttpsClient;
import org.springframework.security.access.method.P;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class VolatileTest {
    private static final int THREADS_COUNT = 50;

    private static final List KEYS = Arrays.asList("孙坤","舒红","唐凌霄","汪玉岑","大吉波","陈丰","王万里","吴林勇","潘登");
    public static ConcurrentHashMap<String,Long> KEY_COUNT = new ConcurrentHashMap();
    public static AtomicLong[] ATOMIC_LONGS = new AtomicLong[9];

    public static  void addVoye(String key){
        synchronized (KEY_COUNT){
            if (KEY_COUNT.containsKey(key)){
                long count = KEY_COUNT.get(key) + 1;
                KEY_COUNT.put(key,count);
            }else KEY_COUNT.put(key,1L);
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0;i < ATOMIC_LONGS.length;i ++)
            ATOMIC_LONGS[i] = new AtomicLong(0);
        for (int i = 0;i < THREADS_COUNT;i ++) {
            threads[i] = new Thread(() -> {
                try {
                    Map map = new HashMap();
                    for (int j = 0;j < 100;j ++){
                        int index = (int) (Math.random()*9);
                        map.put("key",KEYS.get(index));
                        HttpsClient.doGet("http://59.110.234.213/vote/system/vote.do?key=",map);
                        addVoye((String) map.get("key"));
                        ATOMIC_LONGS[index].getAndIncrement();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }
        //等待所有线程累加结束
        while(Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("AtomicLong记录");
        for (int i = 0;i < ATOMIC_LONGS.length;i ++){
            System.out.println(KEYS.get(i) + "得票" + ATOMIC_LONGS[i].longValue());
        }
        System.out.println("map记录");
        for (Map.Entry<String,Long> entry:KEY_COUNT.entrySet()){
            System.out.println(entry.getKey() + "得票" + entry.getValue());
        }
    }
}