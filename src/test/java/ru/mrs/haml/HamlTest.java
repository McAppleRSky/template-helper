package ru.mrs.haml;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HamlTest {

    ClassLoader classLoader = HamlTest.class.getClassLoader();

//    @Test
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
        Process bash;
        try {
            bash = new ProcessBuilder("bash", "-c", "").start();
            if (bash == null) {
                throw new NullPointerException("Bash process not starting");
            } else {
                String program = "haml";
                String haml = classLoader.getResource("haml/input").getPath();
//        haml = haml.substring(1, haml.length());
                String html = classLoader.getResource("haml/output").getPath();
//        html = html.substring(1, html.length());
                ProcessBuilder processBuilder = new ProcessBuilder(program, haml + "/test.haml", html + "/test.html");
                processBuilder.redirectErrorStream(true);

                // запуск программы
                Process process = null;
                try {
                    process = processBuilder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // читаем стандартный поток вывода
                // и выводим на экран
                InputStream stdout = process.getInputStream();
                InputStreamReader isrStdout = new InputStreamReader(stdout);
                BufferedReader brStdout = new BufferedReader(isrStdout);

                List<String> lines = new ArrayList<>();
                String line = null;
                while(true) {
                    try {
                        if (!((line = brStdout.readLine()) != null)) {
                            lines.add(line);
                            break;
                        }
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
                assertNotNull(exitVal);
                assertEquals(0, exitVal);
                System.out.println(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
