package lab.kotlin.coroutine.benchmark.coroutineapp.coroutineapp.infra

import com.mongodb.ConnectionString
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@Configuration
class MongoDBConfig : AbstractReactiveMongoConfiguration() {


    @Bean
    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create(ConnectionString("mongodb://localhost"))
    }

    override fun getDatabaseName(): String {
        return "coroutineapp"
    }
}