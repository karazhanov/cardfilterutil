# Утилита для копирования данных в файлах

Приложен файл '''card_filter_util.jar'''

Для сборки из исходного кода в корне проекта выполнить:
```
mvn clean compile assembly:single
```

Пример запуска:
```
java -jar card_filter_util.jar src=testdata/src dest=testdata/dest thread=2
```

Для проверки корректности номеров карт используется [Алгоритм Луна](https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D1%83%D0%BD%D0%B0)