import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StateAnalysisDateService {

    @Autowired
    private StateAnalysisDateRepository stateAnalysisDateRepository;

    public void updateStateAnalysisDate(List<StateAnalysisDate> stateAnalysisDateList) {
        Calendar calCurrentDate = Calendar.getInstance();

        for (StateAnalysisDate stateAnalysisDate : stateAnalysisDateList) {
            if (stateAnalysisDate != null) {
                // Check analysisMonthGt6
                Date analysisMonthGt6 = stateAnalysisDate.getAnalysisMonthGt6();
                if (analysisMonthGt6 != null && isSameMonthYear(analysisMonthGt6, calCurrentDate)) {
                    // Update analysisMonthGt6 (example: increment by 1 year)
                    Calendar calGt6 = Calendar.getInstance();
                    calGt6.setTime(analysisMonthGt6);
                    calGt6.add(Calendar.YEAR, 1);
                    stateAnalysisDate.setAnalysisMonthGt6(calGt6.getTime());
                }

                // Check analysisMonthLt6
                Date analysisMonthLt6 = stateAnalysisDate.getAnalysisMonthLt6();
                if (analysisMonthLt6 != null && isSameMonthYear(analysisMonthLt6, calCurrentDate)) {
                    // Update analysisMonthLt6 (example: set to current date)
                    stateAnalysisDate.setAnalysisMonthLt6(new Date());
                }

                // Check massDt
                Date massDt = stateAnalysisDate.getMassDt();
                if (massDt != null && isSameMonthYear(massDt, calCurrentDate)) {
                    // Update massDt (example: add 1 day)
                    Calendar calMassDt = Calendar.getInstance();
                    calMassDt.setTime(massDt);
                    calMassDt.add(Calendar.DAY_OF_MONTH, 1);
                    stateAnalysisDate.setMassDt(calMassDt.getTime());
                }

                // Save the updated entity using Spring Data JPA save method
                stateAnalysisDateRepository.save(stateAnalysisDate);
            }
        }
    }

    private boolean isSameMonthYear(Date date1, Calendar cal2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
