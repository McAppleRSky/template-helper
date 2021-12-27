package ru.mrs.haml;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Haml {

    private final ClassLoader classLoader = Haml.class.getClassLoader();
    private final String listJsonFilePath = classLoader.getResource("haml/list.json").getPath();
    private final String hamlInputDirPath = classLoader.getResource("haml/input").getPath();
    private final String hamlOutputDirPath = classLoader.getResource("haml/output").getPath();


    public List<Integer> convert() {
        List<Integer> result = new ArrayList<>();
        try {
            Map<String,Object> ioFiles = new ObjectMapper().readValue(
                    new String( Files.readAllBytes( Paths.get(listJsonFilePath) ) ),
                    HashMap.class
            );
            Iterator<String> iterator = ioFiles.keySet().iterator();
            while(iterator.hasNext()){
                String hamlFilenameKey = iterator.next()
                        ,htmlFilenameValue
                        ;
                if(!hamlFilenameKey.trim().isEmpty()){
                    htmlFilenameValue = ioFiles.get(hamlFilenameKey).toString();
                    result.add(
                            externalProgramLauncher("haml",
                            Paths.get(hamlInputDirPath, hamlFilenameKey).toString(),
                            Paths.get(hamlOutputDirPath, htmlFilenameValue).toString() )
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Integer externalProgramLauncher(String program, String haml, String html){
        ProcessBuilder procBuilder = new ProcessBuilder(program, haml, html);

        // перенаправляем стандартный поток ошибок на
        // стандартный вывод
        procBuilder.redirectErrorStream(true);

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
