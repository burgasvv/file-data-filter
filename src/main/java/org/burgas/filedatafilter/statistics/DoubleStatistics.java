package org.burgas.filedatafilter.statistics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по вещественным числам;
 */
public final class DoubleStatistics implements Statistics<Double> {

    /**
     * Список значений для обработки и получения статистики по вещественным числам;
     */
    private final LinkedList<Double> values;

    /**
     * Конструктор для создания объекта класса и списка элементов вещественных чисел;
     */
    public DoubleStatistics() {
        this.values = new LinkedList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов вещественных чисел;
     */
    public List<Double> getValues() {
        return this.values;
    }

    /**
     * Метод для добавления данных вещественных чисел;
     * @param item Тип данных вещественного числа;
     */
    @Override
    public void add(Double item) {
        this.values.add(item);
    }

    /**
     * Метод для удаления данных вещественного типа;
     * @param item Параметр удаления данных из статистики;
     */
    @Override
    public void remove(Double item) {
        this.values.remove(item);
    }

    /**
     * Метод получения статистики по вещественным числам с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по вещественным числам;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО ВЕЩЕСТВЕННЫМ ЧИСЛАМ: ";
        StringBuilder stringBuilder = new StringBuilder(title);
        List<String> paramList = Arrays.stream(params).toList();

        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return stringBuilder.append("\nКоличество записанных элементов: ").append(this.getValues().size())
                    .append("\nМаксимальное значение: ").append(this.getDoubleMax())
                    .append("\nМинимальное значение: ").append(this.getDoubleMin())
                    .append("\nСреднее значение: ").append(this.getDoubleAverage())
                    .append("\nСумма записанных элементов: ").append(this.getDoubleSum())
                    .toString();

        else if (paramList.contains("-s"))
            return stringBuilder.append("\nКоличество записанных элементов: ")
                    .append(getValues().size())
                    .toString();

        else
            return stringBuilder.append("Отсутствуют аргументы получения статистики")
                    .toString();
    }

    /**
     * Получение минимального элемента в списке вещественных чисел;
     * @return минимальный элемент в списке;
     */
    public Double getDoubleMin() {
        return this.values.stream()
                .min(Double::compareTo)
                .orElse(0.0);
    }

    /**
     * Получение максимального значения элемента в списке вещественных чисел;
     * @return максимальный элемент в списке;
     */
    public Double getDoubleMax() {
        return this.values.stream()
                .max(Double::compareTo)
                .orElse(0.0);
    }

    /**
     * Получение среднего значения элемента в списке вещественных чисел;
     * @return средний элемент в списке;
     */
    public Double getDoubleAverage() {
        return this.values.stream()
                .mapToDouble(value -> value)
                .average()
                .orElse(0.0);
    }

    /**
     * Расчет суммы значений элементов в списке вещественных чисел;
     * @return сумма элементов списка вещественных чисел;
     */
    public Double getDoubleSum() {
        return this.values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
