# BigProject
Клиентская часть клиент-серврного приложения (облачные заметки) для Android.
Поддерживает авторизацию и регистрацию. Возможность добавления кратинки к заметки.
Данные на устройстве шифруются с помощью SHA-256.

Для работы необходимы следующие зависимости:
implementation 'com.google.android.material:material:1.1.0' - материал дизайн

implementation 'com.yakivmospan:scytale:1.0.1',
implementation 'com.scottyab:aescrypt:0.0.1' - 2 библиотеки, обеспечивающие работу шифрования.

implementation 'com.squareup.retrofit2:retrofit:2.5.0',
implementation 'com.squareup.retrofit2:converter-gson:2.0.0' - библиотеки для работы с сервером

implementation 'com.android.support:cardview-v7:28.0.0' - элемент для визуального представления заметок
implementation 'com.android.support:recyclerview-v7:28.0.0' - визуальное расположение заметок