package arithmetic

import java.math.BigInteger

//initVal依然是初始值，tempResult表示上一次计算的结果,初始值是1
tailrec fun tailRec(initVal: BigInteger, tempResult: BigInteger): BigInteger {
    if (initVal == BigInteger.ONE || initVal == BigInteger.ZERO)
        return tempResult
    return tailRec(initVal - BigInteger.ONE, initVal * tempResult)
}

//main方法
fun main(args: Array<String>) {
    println(tailRec(BigInteger("10000"), BigInteger.ONE))//运行正确！不在抛出异常
}