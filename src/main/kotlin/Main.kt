import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private var job1: Job? = null

fun main() {
    job1 = StructureSample().run{
        runJob1()
    }
    runBlocking {
        delay(3_000)
        job1?.cancel()
        delay(10_000)
    }
}

/*
fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("(1) Task from runBlocking")
    }

    coroutineScope { // Creates a new coroutine scope
        launch {
            delay(900L)
            println("(2) Task from nested launch")
        }

        delay(100L)
        println("(3) Task from coroutine scope") // This line will be printed before nested launch
    }

    println("(4) Coroutine scope is over") // This line is not printed until nested launch completes
}
 */

/*
fun main() {
    println("(0)")
    GlobalScope.launch { // this: CoroutineScope
        launch {
            delay(200L)
            println("(1) Task from runBlocking")
        }.also {

        }

        coroutineScope { // Creates a new coroutine scope
            launch {
                delay(900L)
                println("(2) Task from nested launch")
            }

            delay(100L)
            println("(3) Task from coroutine scope") // This line will be printed before nested launch
        }

        println("(4) Coroutine scope is over") // This line is not printed until nested launch completes
    }

    runBlocking {
        delay(3000)
    }
}
*/

/*
fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}
*/