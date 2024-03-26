import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobLaunchControllerTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock(name = "individualFinalAnalysisJob")
    private Job finalAnalysisJob;

    @Mock(name = "purgeEscrowAnalysisJob")
    private Job purgeJob;

    @InjectMocks
    private JobLaunchController controller;

    @Test
    void testLaunchFinalAnalysisJob_Success() throws Exception {
        // Arrange
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(null);

        // Act
        ResponseEntity<String> response = controller.launchFinalAnalysisJob();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("finalAnalysisJob Launched successfully", response.getBody());
    }

    @Test
    void testLaunchFinalAnalysisJob_Failure() throws Exception {
        // Arrange
        String errorMessage = "Some error message";
        doThrow(new RuntimeException(errorMessage)).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // Act
        ResponseEntity<String> response = controller.launchFinalAnalysisJob();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error Launching finalAnalysisJob: " + errorMessage, response.getBody());
    }

    @Test
    void testLaunchPurgeJob_Success() throws Exception {
        // Arrange
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(null);

        // Act
        ResponseEntity<String> response = controller.launchPurgeJob();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("purgeJob Launched successfully", response.getBody());
    }

    @Test
    void testLaunchPurgeJob_Failure() throws Exception {
        // Arrange
        String errorMessage = "Some error message";
        doThrow(new RuntimeException(errorMessage)).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // Act
        ResponseEntity<String> response = controller.launchPurgeJob();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error Launching purgeJob: " + errorMessage, response.getBody());
    }
}
