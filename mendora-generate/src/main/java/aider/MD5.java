package aider;

import com.hazelcast.util.MD5Util;
import org.mendora.util.generate.IDUtils;

/**
 * Created by kam on 2018/4/15.
 */
public class MD5 {
    public static void main(String[] args) {
        String originStr = "";
        System.out.println(MD5Util.toMD5String(originStr));
        System.out.println(IDUtils.salt(6));
    }
}
