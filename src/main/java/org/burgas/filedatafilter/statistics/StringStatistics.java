package org.burgas.filedatafilter.statistics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по строковым значениям;
 */
public final class StringStatistics implements Statistics<String> {

    /**
     * Список значений для обработки и получения статистики по строковым значениям;
     */
    private final LinkedList<String> strings;

    /**
     * Конструктор для создания объекта класса и списка элементов строковых значений;
     */
    public StringStatistics() {
        this.strings = new LinkedList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов строковых значений;
     */
    public List<String> getStrings() {
        return this.strings;
    }

    /**
     * Метод для добавления данных строковых значений;
     * @param item Тип данных строка;
     */
    @Override
    public void add(String item) {
        this.strings.add(item);
    }

    /**
     * Метод для удаления данных строковых значений;
     * @param item Тип данных строка;
     */
    @Override
    public void remove(String item) {
        this.strings.remove(item);
    }

    /**
     * Метод получения статистики по строковым значениям с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по строковым значениям;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО СТРОКАМ: ";
        List<String> paramList = Arrays.stream(params).toList();
        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return title +
                   "\nКоличество записанных элементов: " + this.getStrings().size() +
                   "\nСамая короткая строка: " + this.getMinLengthString() +
                   "\nСамая длинная строка: " + this.getMaxLengthString();

        else if (paramList.contains("-s"))
            return title + "\nКоличество записанных элементов: " + this.getStrings().size();

        else
            return title + "Отсутствуют аргументы получения статистики";
    }

    /**
     * Метод поиска элемента строки с наименьшей длинной;
     * @return получение элемента с наименьшей длиной;
     */
    public Integer getMinLengthString() {
        return this.strings.stream()
                .mapToInt(String::length)
                .min()
                .orElse(0);
    }

    /**
     * Метод поиска элемента строки с наибольшей длинной;
     * @return получение элемента с наименьшей длиной;
     */
    public Integer getMaxLengthString() {
        return this.strings.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
}
