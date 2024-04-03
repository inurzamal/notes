@Component
public class PurgeEscrowAnalysisEmailTasklet implements Tasklet {

    private final Environment environment;
    private final EmailService emailService;

    @Autowired
    public PurgeEscrowAnalysisEmailTasklet(Environment environment, EmailService emailService) {
        this.environment = environment;
        this.emailService = emailService;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append(BatchEmailConstants.PURGE_ESCROW_ANAYSIS_EMAIL_BODY_LINE_1);
        sb.append(BatchEmailConstants.GENERAL_EMAIL_LINE_OUTPUT_DONOT_REPLY);
        sb.append(BatchEmailConstants.GENERAL_EMAIL_LINE_FOR_QUESTIONS);

        String[] activeProfiles = environment.getActiveProfiles();
        String profile = extractDesiredProfile(activeProfiles);

        String subject = BatchEmailConstants.PURGE_ESCROW_ANALYSIS_SUBJECT + profile;

        emailService.sendEmail(subject, sb.toString());

        return RepeatStatus.FINISHED;
    }

    private String extractDesiredProfile(String[] activeProfiles) {
        for (String profile : activeProfiles) {
            if ("dev".equalsIgnoreCase(profile) || "sit".equalsIgnoreCase(profile) || "uat".equalsIgnoreCase(profile) || "prod".equalsIgnoreCase(profile)) {
                return profile;
            }
        }
        return ""; // Return empty string if none of the desired profiles are found
    }
}
