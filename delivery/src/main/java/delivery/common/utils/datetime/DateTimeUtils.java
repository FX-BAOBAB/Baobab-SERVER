package delivery.common.utils.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class DateTimeUtils {

    private static String startDate;
    private static String dueDate;
    private static DateTimeFormatter formatter;

    public static RequestDateTime  getStartAndDueDate(String date){
        startDate = date + " 00:00:00.000";
        dueDate = date + " 23:59:59.999";
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return RequestDateTime.builder()
            .startDateTime(LocalDateTime.parse(startDate, formatter))
            .dueDateTime(LocalDateTime.parse(dueDate, formatter))
            .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestDateTime{
        private LocalDateTime startDateTime;
        private LocalDateTime dueDateTime;
    }
}
