import kotlinx.coroutines.*

class StructureSample {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    fun runJob1(): Job = scope.launch {
        // create an inside launch with current CoroutineScope
        launch {
            println("inner job in job1")
            println("idle...")
            delay(100_000)
        }.apply {
            invokeOnCompletion {
                println("job inside job 1 throwable: $it")
            }
        }
        for (i in 1..10) {
            println("$i second")
            delay(1_000)
        }
    }.apply {
        invokeOnCompletion {
            println("job 1 throwable: $it")
        }
    }

    fun runJob2(): Job = scope.launch {
        // create an inside launch with global CoroutineScope, which would NOT  be canceled if parent job is canceled.
        scope.launch {
            println("inner job in job1")
            println("idle...")
            delay(100_000)
        }.apply {
            invokeOnCompletion {
                println("job inside job 1 throwable: $it")
            }
        }
        for (i in 1..10) {
            println("$i second")
            delay(1_000)
        }
    }.apply {
        invokeOnCompletion {
            println("job 1 throwable: $it")
        }
    }


}