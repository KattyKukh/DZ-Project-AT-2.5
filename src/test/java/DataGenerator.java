import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import javax.management.StringValueExp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static FormData generate() {
            Faker faker = new Faker(new Locale("ru"));
            String City = generateCity();
            //   можно еще так генерировать телефонный номер - faker.numerify("+79#########"))
            return new FormData(faker.name().fullName(), City, faker.phoneNumber().phoneNumber());
        }

        public static LocalDate generateDate(long countDays) {
            return LocalDate.now().plusDays(countDays);
        }

        public static String generateCity() {
            Faker faker = new Faker();
            String[] cityList = {"Абакан",
                    "Анадырь",
                    "Архангельск",
                    "Астрахань",
                    "Барнаул",
                    "Белгород",
                    "Биробиджан",
                    "Благовещенск",
                    "Брянск",
                    "Великий Новгород",
                    "Владивосток",
                    "Владикавказ",
                    "Владимир",
                    "Волгоград",
                    "Вологда",
                    "Воронеж",
                    "Горно-Алтайск",
                    "Грозный",
                    "Екатеринбург",
                    "Иваново",
                    "Ижевск",
                    "Иркутск",
                    "Йошкар-Ола",
                    "Казань",
                    "Калининград",
                    "Калуга",
                    "Кемерово",
                    "Киров",
                    "Кострома",
                    "Краснодар",
                    "Красноярск",
                    "Курган",
                    "Курск",
                    "Кызыл",
                    "Липецк",
                    "Магадан",
                    "Магас",
                    "Майкоп",
                    "Махачкала",
                    "Москва",
                    "Мурманск",
                    "Нальчик",
                    "Нарьян-Мар",
                    "Нижний Новгород",
                    "Новосибирск",
                    "Омск",
                    "Орёл",
                    "Оренбург",
                    "Пенза",
                    "Пермь",
                    "Петрозаводск",
                    "Петропавловск-Камчатский",
                    "Псков",
                    "Ростов-на-Дону",
                    "Рязань",
                    "Салехард",
                    "Самара",
                    "Санкт-Петербург",
                    "Саранск",
                    "Саратов",
                    "Севастополь",
                    "Симферополь",
                    "Смоленск",
                    "Ставрополь",
                    "Сыктывкар",
                    "Тамбов",
                    "Тверь",
                    "Томск",
                    "Тула",
                    "Тюмень",
                    "Улан-Удэ",
                    "Ульяновск",
                    "Уфа",
                    "Хабаровск",
                    "Ханты-Мансийск",
                    "Чебоксары",
                    "Челябинск",
                    "Черкесск",
                    "Чита",
                    "Элиста",
                    "Южно-Сахалинск",
                    "Якутск",
                    "Ярославль"};
            int i = faker.number().numberBetween(0, cityList.length - 1);
            return cityList[i];
        }
    }
}