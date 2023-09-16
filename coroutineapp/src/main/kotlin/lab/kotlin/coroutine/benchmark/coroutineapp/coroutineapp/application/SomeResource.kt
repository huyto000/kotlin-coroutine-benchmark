package lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp.application

import lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp.domain.Result
import lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp.domain.ResultA
import lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class SomeResource {

    @Value("\${external.service.url}")
    private lateinit var externalServiceUrl: String

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val webClient by lazy {
        webClientBuilder.build()
    }

    @Autowired
    private lateinit var service: Service

    @GetMapping("/execute")
    suspend fun execute() = service.execute()

    @GetMapping("/executeAsync")
    suspend fun executeAsync() = service.executeAsync()

    @GetMapping(value = arrayOf("/get"), produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    suspend fun get() : Mono<Result> {
        println("Starting NON-BLOCKING Controller!");
         val mono = webClient
            .get()
            .uri(externalServiceUrl)
             .retrieve()
             .bodyToMono(Result::class.java)

        mono.subscribe{println(it.toString())};
        println("Exiting NON-BLOCKING Controller!");
        return mono;
    }


    @GetMapping("/executeAsyncReactor")
    fun reactiveWithOutCoroutines(): Mono<Result> =
            service.executeAsyncWithReactor()
}