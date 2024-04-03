import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PurgeEscrowAnalysisEmailTaskletTest {

    @Mock
    private Environment environment;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PurgeEscrowAnalysisEmailTasklet tasklet;

    @BeforeEach
    public void setup() {
        Mockito.when(environment.getActiveProfiles()).thenReturn(new String[]{"dev"});
    }

    @Test
    void testExecute() throws Exception {
        Mockito.doNothing().when(emailService).sendEmail(any(), any());

        StepContribution stepContribution = mock(StepContribution.class);
        ChunkContext chunkContext = mock(ChunkContext.class);

        RepeatStatus repeatStatus = tasklet.execute(stepContribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, repeatStatus);
    }
}
