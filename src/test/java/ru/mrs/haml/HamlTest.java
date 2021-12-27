package ru.mrs.haml;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class HamlTest {

    @Test
    void bashTest(){
        Process bash = null;
        try {
            bash = new ProcessBuilder("bash", "-c", "").start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertNotNull(bash);
        }
    }

    @Test
    void hamlTest(){
        Process bash = null;
        try {
            bash = new ProcessBuilder("haml", "-v", "").start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertNotNull(bash);
        }

        // перенаправляем стандартный поток ошибок на
        // стандартный вывод
        bash.redirectErrorStream(true);

        // запуск программы
        Process process = null;
        try {
            process = procBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // читаем стандартный поток вывода
        // и выводим на экран
        InputStream stdout = process.getInputStream();
        InputStreamReader isrStdout = new InputStreamReader(stdout);
        BufferedReader brStdout = new BufferedReader(isrStdout);

        String line = null;
        while(true) {
            try {
                if (!((line = brStdout.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ждем пока завершится вызванная программа
        // и сохраняем код, с которым она завершилась в
        // в переменную exitVal
        Integer exitVal = null;
        try {
            exitVal = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitVal;

    }


}
