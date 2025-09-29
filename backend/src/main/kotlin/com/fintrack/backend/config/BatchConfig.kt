import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

/**
 * Spring Batch configuration class.
 *
 * This class defines a very simple batch job with a single step,
 * and integrates with Spring Boot's Batch infrastructure.
 *
 * - Each job is stored in the JobRepository
 * - Batch tables are auto-created if `spring.batch.jdbc.initialize-schema=always`
 * - Actuator exposes batch-related metrics under `/actuator/metrics`
 */
@Configuration
@EnableBatchProcessing
class BatchConfig {

    /**
     * Defines a sample batch **Step**.
     *
     * A step is the smallest unit in a batch job. Here we define a simple
     * [tasklet] that prints a message once and then finishes.
     *
     * @param jobRepository Stores metadata about jobs/steps (execution history, status, etc.)
     * @param transactionManager Manages transactions for batch processing
     * @return a [Step] object ready to be used inside a [Job]
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
     * Defines a sample batch **Job**.
     *
     * A job is a container for one or more steps.
     * - Here, it only runs [sampleStep].
     * - [RunIdIncrementer] ensures that each job run is uniquely identified.
     *
     * @param jobRepository Stores metadata about jobs
     * @param transactionManager Required for step execution
     * @return a [Job] object
     */
    @Bean
    fun sampleJob(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Job =
        JobBuilder("sampleJob", jobRepository)
            .incrementer(RunIdIncrementer()) // ensures unique job instance each run
            .start(sampleStep(jobRepository, transactionManager))
            .build()
}