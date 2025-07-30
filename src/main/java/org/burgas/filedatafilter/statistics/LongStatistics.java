package org.burgas.filedatafilter.statistics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по целым числам;
 */
public final class LongStatistics implements Statistics<Long> {

    /**
     * Список значений для обработки и получения статистики по целым числам;
     */
    private final LinkedList<Long> values;

    /**
     * Конструктор для создания объекта класса и списка элементов целых чисел;
     */
    public LongStatistics() {
        this.values = new LinkedList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов целых чисел;
     */
    public List<Long> getValues() {
        return this.values;
    }

    /**
     * Метод для добавления данных целых чисел;
     * @param item Тип данных целого числа;
     */
    @Override
    public void add(Long item) {
        this.values.add(item);
    }

    /**
     * Метод для удаления данных целых чисел;
     * @param item Тип данных целого числа;
     */
    @Override
    public void remove(Long item) {
        this.values.remove(item);
    }

    /**
     * Метод получения статистики по вещественным числам с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по целым числам;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО ЦЕЛЫМ ЧИСЛАМ: ";
        StringBuilder stringBuilder = new StringBuilder(title);
        List<String> paramList = Arrays.stream(params).toList();

        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return stringBuilder.append("\nКоличество записанных элементов: ").append(this.getValues().size())
                    .append("\nМаксимальное значение: ").append(this.getLongMax())
                    .append("\nМинимальное значение: ").append(this.getLongMin())
                    .append("\nСреднее значение: ").append(this.getLongAverage())
                    .append("\nСумма записанных элементов: ").append(this.getLongSum()).toString();

        else if (paramList.contains("-s"))
            return stringBuilder.append("\nКоличество записанных элементов: ")
                    .append(this.getValues().size())
                    .toString();

        else
            return stringBuilder.append("Отсутствуют аргументы получения статистики").toString();
    }

    /**
     * Получение минимального элемента в списке целых чисел;
     * @return минимальный элемент в списке;
     */
    public Long getLongMin() {
        return this.values.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0L);
    }

    /**
     * Получение максимального значения элемента в списке целых чисел;
     * @return максимальный элемент в списке;
     */
    public Long getLongMax() {
        return this.values.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);
    }

    /**
     * Получение среднего значения элемента в списке целых чисел;
     * @return средний элемент в списке;
     */
    public Long getLongAverage() {
        return (long) this.values.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Расчет суммы значений элементов в списке целых чисел;
     * @return сумма элементов списка целых чисел;
     */
    public Long getLongSum() {
        return this.values.stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}
