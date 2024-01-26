import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppLauncher {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/job-createIndividualFinalAnalysisJob-config.xml");
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("createIndividualFinalAnalysisJob", Job.class);

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        System.out.println("Job Status: " + jobExecution.getStatus());
    }
}
