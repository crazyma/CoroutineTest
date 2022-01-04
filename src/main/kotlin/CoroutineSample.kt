import kotlinx.coroutines.*
import java.lang.RuntimeException

class CoroutineSample {

    fun runTest() = runBlocking(Dispatchers.Default) {
        val start = System.currentTimeMillis()
        try {
            repeat(10) {
                try {
                    val job = if (it != 5) async { loopAndReturnInt() }
                    else async { loopAndErrorTest() }
//            delay(100)
//            job.cancel()
                    println("defered result : ${job.await()}")
                } catch (t: Throwable) {
                    println("catch error(${t.message}) inside")
                }
            }
        } catch (t: Throwable) {
            println("run test error: ${t.message}")
        } finally {
            val duration = System.currentTimeMillis() - start
            println("[duration: $duration]")
        }

    }

    fun runTest2() = runBlocking(Dispatchers.Default) {
        test2()
    }

    private suspend fun test2() {

        supervisorScope {
            val context = newFixedThreadPoolContext(4, "")
            val start = System.currentTimeMillis()
            try {
                repeat(10) {
                    try {
                        val job = if (it != 5) async(context) { loopAndReturnInt() }
                        else async(context) { loopAndErrorTest() }
                        println("defered result : ${job.await()}")
                    } catch (t: Throwable) {
                        println("catch error(${t.message}) inside")
                    }
                }
            } catch (t: Throwable) {
                println("run test error: ${t.message}")
            } finally {
                val duration = System.currentTimeMillis() - start
                println("[duration: $duration]")
            }
        }
    }

    private suspend fun loopTask() = withContext(Dispatchers.IO) {
        while (isActive) {
            if (!isActive) {
                println("loop task is NOT active anymore")
            }
        }
    }

    private suspend fun createTask() {
        withContext(Dispatchers.IO) {
            async {

            }
        }
    }

    private suspend fun loopAndReturnIntAsync() = withContext(Dispatchers.IO) {
        async(start = CoroutineStart.LAZY) {
            var i = 0L
            while (++i <= 500_000_000 && isActive) {
            }

            if (isActive) (Math.random() * 100).toInt()
            else -1
        }
    }

    private suspend fun loopAndReturnInt() = withContext(Dispatchers.IO) {
        var i = 0L
        while (++i <= 500_000_000 && isActive) {
        }

        if (isActive) (Math.random() * 100).toInt()
        else -1
    }


    private suspend fun loopAndErrorTest() = withContext(Dispatchers.IO) {
        var i = 0L
        while (++i <= 500_000_000 && isActive) {
        }

        throw RuntimeException("testing error")

        if (isActive) (Math.random() * 100).toInt()
        else -1
    }

}