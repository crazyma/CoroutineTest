import kotlinx.coroutines.*

class ErrorHandlingSample {

    private val regularScope = CoroutineScope(Job())
    private val supervisorScope = CoroutineScope(SupervisorJob())
    private val subJobs = mutableListOf<Job>()

    private val exceptionHandler = CoroutineExceptionHandler { _ , exceptino ->

    }

    fun test() {

        supervisorScope.launch {
            coroutineScope {

            }

            supervisorScope {

            }

            launch {
                println("[job 1] launch")
                delay(5000)
                println("[Job 1] done")
            }.invokeOnCompletion {
                println("[Job 1] completed | $it")
            }
            delay(1000)
            launch {
                println("[job 2] launch")
                delay(1000)
                throw Exception("XDDDDDDDD")
            }.invokeOnCompletion {
                println("[Job 2] completed | $it")
            }
        }.invokeOnCompletion {
            println("[parent job completed] | $it")
        }
    }

    fun regularJobCancelTest() {
        regularScope.launch {
            for (i in 1..5) {
                delay(500)
                launch {
                    subJob(i)
                }.apply {
                    invokeOnCompletion {
                        println("[SubJob $i] completed | $it")
                    }
                }.let {
                    subJobs.add(it)
                }
            }
            delay(10_000)
        }

        regularScope.launch {
            delay(2500)
            subJobs[2].cancel()
            delay(10_000)
        }
    }

    fun regularJobFailedTest() {
        regularScope.launch {
            for (i in 1..5) {
                delay(500)
                launch {
                    if (i == 2)
                        subJobFailed(i)
                    else
                        subJob(i)
                }.apply {
                    invokeOnCompletion {
                        println("[SubJob $i] completed | $it")
                    }
                }.let {
                    subJobs.add(it)
                }
            }
            delay(10_000)
        }
    }

    fun supervisorJobCancelTest() {
        supervisorScope.launch {
            for (i in 1..5) {
                delay(500)
                launch {
                    subJob(i)
                }.apply {
                    invokeOnCompletion {
                        println("[SubJob $i] completed | $it")
                    }
                }.let {
                    subJobs.add(it)
                }
            }
            delay(10_000)
        }

        supervisorScope.launch {
            delay(2500)
            subJobs[2].cancel()
            delay(10_000)
        }
    }

    fun supervisorJobFailedTest() {
        supervisorScope.launch {
            for (i in 1..5) {
                delay(500)
                launch {
                    if (i == 2)
                        subJobFailed(i)
                    else
                        subJob(i)
                }.apply {
                    invokeOnCompletion {
                        println("[SubJob $i] completed | $it")
                    }
                }.let {
                    subJobs.add(it)
                }
            }
            delay(10_000)
        }
    }

    private suspend fun subJob(index: Int) {
        println("[SubJob $index] start")
        delay(1000)
        println("[SubJob $index] delay 1 sec")
        delay(1000)
        println("[SubJob $index] delay 2 secs and finished")
    }

    private suspend fun subJobFailed(index: Int) {
        println("[SubJob $index] start")
        delay(1000)
        println("[SubJob $index] delay 1 sec and throw exception")
        throw Exception("XD")
    }
}