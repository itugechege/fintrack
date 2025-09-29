package com.fintrack.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
class AsyncConfig {

    @Bean(name = ["taskExecutor"])
    fun taxExecutor(): Executor{
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 20
        executor.setQueueCapacity(100)
        executor.setThreadNamePrefix("tax-executor-")
        executor.initialize()
        return executor
    }
}