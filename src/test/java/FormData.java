import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FormData {
    private final String validName;
    private final String validCity;
    private final String validPhone;
}
