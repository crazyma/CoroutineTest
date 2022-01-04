import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

class AsyncSample {

    fun runTest() = runBlocking {
        val time = System.currentTimeMillis() / 1000
        println("run test: $time")
        task1()
        task2()
    }

    fun runAsyncTest() = runBlocking {
        val time = System.currentTimeMillis() / 1000
        println("run test: $time")
        try {
            val deferred1 = async { task1() }
            val deferred2 = async { task2() }

            val time1 =  deferred1.await()
            val time2 = deferred2.await()
            delay(5000)
            val result = time1 + time2
            println("result: $result")
            val endTime = System.currentTimeMillis() / 1000
            println("end time: $endTime")
        } catch (t: Throwable) {
            println("error: $t")
        }

    }

    private suspend fun task1(): Long {
        delay(2000)
        val time = System.currentTimeMillis() / 1000
        println("task 1 end time: $time")
        return time
    }

    private suspend fun task2(): Long {
        delay(2000)
        val time = System.currentTimeMillis() / 1000
        println("task 1 end time: $time")
        return time
    }

}