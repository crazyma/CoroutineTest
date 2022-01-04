import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class FlowSample {

    val flowCollector = object: FlowCollector<Int>{
        override suspend fun emit(value: Int) {

        }

    }


    fun getSequence() : Flow<Int> {
        flow<Int>(block = {

        })
        return flow {
            for(i in 1 .. 3){
                delay(500)
                emit(i)
            }
        }
    }

}