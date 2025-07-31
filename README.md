
# ___Утилита фильтрации содержимого файлов.___

## Версии
+ OpenJDK 17.0.15
+ Maven 3.9.9

## Сторонние библиотеки
+ Библиотека для чтения pdf файлов ItextPDF 5.5.13.4
    ```
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.4</version>
    </dependency>
  ```
+ Библиотека для чтения docx файлов Apache POI 5.2.5
    ```
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>
  ```
+ Логирование log4j 2.25.1
    ```
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.25.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.25.1</version>
    </dependency>
  ```
+ Тестирование Junit Jupiter 5.12.2
    ```
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.12.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.12.2</version>
        <scope>test</scope>
    </dependency>
  ```
___

## Описание
        Приложение получает на вход аргументы и директории к исходным файлам. (Идет обработка аргументов)
    Затем происходит считывание информации с исходных файлов, распределение данных по типам и запись в соответствующие типам данных результирующие файлы.
    Далее идет считывание данных с результирующих файлов и их отправка в сервис статистики. После происходит расчет и вывод статистики.
___

### Формат исходных файлов
**.txt .pdf .docx** 
___

### Разработанные сервисы
+ Argument Handler - необходим для обработки входящих аргументов
+ Data Filter - сервис фильтрации данных
+ Read File API - интерфейс для чтения pdf и docx файлов
+ Read Write File API - интерфейс для чтения и записи txt файлов
+ Statistics Service - сервис для получения данных и расчета статистики

### Варианты запуска приложения
* Запуск из среды разработки (без jar-файла), с настройкой конфигурации запуска и указанием аргументов
* Запуск jar-файла из терминала или командной строки с аргументами


* Необходимые аргументы
  * ___-a___ - параметр перезаписи файлов
  * ___-o___ - директория результативных файлов для записи
  * ___-p___ - префикс наименования результативных файлов для записи
  * ___-s___ - получение краткой статистики
  * ___-f___ - получение полной статистики

___

### Запуск приложения
* > Запустить командную строку и перейти в корневую директорию приложения
* > Выполнить команду `mvn clean package`
* > Пример команды запуска jar файла приложения: 
  >> `java -jar target/file-data-filter-1.0-jar-with-dependencies.jar -a -f -o src/main/resources/output/ -p result_
src/main/resources/input/in1.txt src/main/resources/input/in2.txt src/main/resources/input/in3.pdf src/main/resources/input/in4.docx`
* > Получить результат выполнения