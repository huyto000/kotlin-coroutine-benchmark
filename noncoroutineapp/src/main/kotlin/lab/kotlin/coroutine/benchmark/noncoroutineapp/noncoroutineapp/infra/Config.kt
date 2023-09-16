package lab.kotlin.coroutine.benchmark.noncoroutineapp.noncoroutineapp.infra


import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.apache.hc.client5.http.io.HttpClientConnectionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Executor


@Configuration
class Config {

    @Value("\${async.pool-max-size}")
    private var asyncMaxPoolSize: Int = 1

    @Value("\${async.pool-core-size}")
    private var asyncCorePoolSize: Int = 1

    @Value("\${async.queue-size}")
    private var asyncQueueSize: Int = 1

    @Bean
    fun httpClient(): HttpClient {
        val poolingConnManager: HttpClientConnectionManager = PoolingHttpClientConnectionManager()
        return HttpClients.custom().setConnectionManager(poolingConnManager)
                .build()
    }

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun asyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = asyncCorePoolSize
        executor.maxPoolSize = asyncMaxPoolSize
        executor.setQueueCapacity(asyncQueueSize)
        executor.setThreadNamePrefix("AsyncExecutor-")
        executor.initialize()
        return executor
    }
}