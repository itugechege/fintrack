import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

/**
 * Spring Batch configuration class.
 *
 * Defines a simple batch job with one step.
 * - Each execution is stored in JobRepository
 * - Requires transaction manager for step execution
 */
@Configuration
@EnableBatchProcessing
class BatchConfig {

    /**
     * Defines a sample step that prints a message once and finishes.
     */
    @Bean
    fun sampleStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step =
        StepBuilder("sampleStep", jobRepository)
            .tasklet({ _, _ ->
                println(">> Running sample batch step")
                RepeatStatus.FINISHED
            }, transactionManager)
            .build()

    /**
     * Defines a sample job that runs [sampleStep].
     * Each run gets a unique ID via [RunIdIncrementer].
     */
    @Bean
    fun sampleJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Job  =
        JobBuilder("sampleJob", jobRepository)
            .incrementer(RunIdIncrementer()) // ensures unique job instance each run
            .start(sampleStep(jobRepository, transactionManager))
            .build()


}