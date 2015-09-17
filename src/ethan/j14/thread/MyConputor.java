package ethan.j14.thread;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.jcip.annotations.GuardedBy;

/**
 * @author ethan
 * @since 2014-7-19
 */

public class MyConputor<A, V>/* implements Computable<A, V>*/ {

    @GuardedBy("this")
    private Map<A, V> cache = new HashMap<A, V>();
    private IComputable<A, V> iComputable;

    public synchronized V computer(A value) throws InterruptedException {
        //^^ 问题：其他线程严重被阻塞，伸缩性差
        //^^ 缓存对比器
        V result = cache.get(value);
        if (result == null) {
            result = iComputable.compute(value);
            cache.put(value, result);
        }
        return result;
    }

    public MyConputor(IComputable<A, V> iComputable) {
        this.iComputable = iComputable;
    }

    public static void main(String[] args) {
        ComputableImpl computableImpl = new ComputableImpl();
        MyConputor conputor = new MyConputor<String, BigInteger>(computableImpl);
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(conputor.computer("555555"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ComputableImpl implements IComputable<String, BigInteger> {

    @Override
    public BigInteger compute(String str) throws InterruptedException {
        Thread.sleep(1000);
        return new BigInteger(str);
    }

}

interface IComputable<A, V> {
    V compute(A argu) throws InterruptedException;
} 