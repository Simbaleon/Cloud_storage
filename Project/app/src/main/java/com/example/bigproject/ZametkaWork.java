package com.example.bigproject;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Работа с заметками
 */
public class ZametkaWork {

    private Context context;
    private Bitmap bitmap = null;
    private Zametka zametka;
    private Uri uri = null; //Uri фото, которое нужно сохранить
    private String inf = ""; //Строка, которая приходит в методы save...
    private Thread thread; //Тут происходит вся работа

    /**
     * Instantiates a new Zametka work.
     *
     * @param context the context
     */
    ZametkaWork(Context context){
        this.context = context;
    }


    /**
     * De serialization bitmap bitmap.
     *
     * @param str the str
     * @return the bitmap
     * Десериализация картинки
     */
    public static Bitmap deSerializationBitmap(String str)
    {
        if(str.length() == 0) return null;
        byte[] decodedBytes = Base64.decode(str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * Serialization bitmap string.
     *
     * @param uri the uri
     * @return the string
     * Сериализация картикни
     */
    public String serializationBitmap(Uri uri)
    {

        ContentResolver resolver = context.getContentResolver();
        try {
            InputStream stream = resolver.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(),0);
    }

    /**
     * Создание заметки
     */
    private Zametka makeZametka()
    {
        Zametka zametka = new Zametka();

        //Сохраняем дату
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        zametka.setData(dateText);

        //По дефолту имя заметки = дата
        zametka.setName(dateText);

        return zametka;
    }


    /**
     * Создание и сохранение текстовой заметки
     *
     * @param str the str
     */
//Создаём и сохраняем заметку на телефоне
    //Если заметка была вызвана соханением картинки
    public void MakeAndSaveTextZam(String str)
    {
        inf = str.substring(0,str.indexOf("|"));

        //Теперь мы сериализуем изображение в поток байтов,а его в строку. Это довольно тяжёлая задача, поэтому она в отдельном потоке
        thread =  new Thread(new Runnable() {
            @Override
            public void run() {

                //Создаём заметку
                zametka = makeZametka();

                //Добавляем к заметке наш текст
                zametka.setValue(inf);

                //Фото нет
                zametka.setBitmap("");

                //Сохраняемя заметку в файл
                LocalBase.save(zametka);

                String v = "4";
                thread.interrupt();
            }
        });
        thread.start();
    }

    /**
     * Создание и сохранение заметки с картинкой
     *
     * @param ArgUri the arg uri
     */
//Если заметка была вызвана сохраением текста
    public void MakeAndSaveImageZam(Uri ArgUri)
    {
        uri = ArgUri;
        //Теперь мы сериализуем изображение в поток байтов,а его в строку. Это довольно тяжёлая задача, поэтому она в отдельном потоке
        thread =  new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //Создаём заметку
                zametka = makeZametka();

                //Раз мы сохраняем картинку,текста нет
                zametka.setValue("");

                //Сериализуем фото
                String bitmapStr = serializationBitmap(uri);

                //Добавляем фото к заметке(в виде строки)
                zametka.setBitmap(bitmapStr);

                //Сохраняемя заметку в файл
                LocalBase.save(zametka);

                thread.interrupt();
                }
            });
        thread.start();
    }

}
