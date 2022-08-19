package ru.netology.delivery.data;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormData {
    private final String validName;
    private final String validCity;
    private final String validPhone;
}
