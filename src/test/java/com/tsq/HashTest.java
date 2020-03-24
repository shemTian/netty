package com.tsq;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.junit.Test;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-24 11:36
 */
public class HashTest {

    public void before() {

    }
    @Test
    public void murmur() {
        //会以时间作为seed构造hash,seed 会参与hash计算 只能临时使用不能存储，否则会匹配不到
        HashFunction hashFunction1 = Hashing.goodFastHash(32);
        HashFunction hashFunction = Hashing.murmur3_32();
        String str1 = "abc";
        String str2 = "abd";
        int hash1 = hashFunction.newHasher().putUnencodedChars(str1).hash().asInt();
        int hash2 = hashFunction.newHasher().putUnencodedChars(str2).hash().asInt();
        System.out.println(hash1+"\t"+str1.hashCode());
        System.out.println(hash2+"\t"+str2.hashCode());


        System.out.println(hashFunction.newHasher().putUnencodedChars(str1).hash().asInt()+"\t"+str1.hashCode());
        System.out.println(hashFunction.newHasher().putUnencodedChars(str2).hash().asInt()+"\t"+str2.hashCode());
    }
}
