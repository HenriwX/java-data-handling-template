package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src/main/resources/" + path);
        long count = 0;

        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                count += countFilesInDirectory(path + "/" + file.getName());
            }
        } else {
            count++;
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File("src/main/resources/" + path);
        long count = 0;

        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                count += countDirsInDirectory(path + "/" + file.getName());
            }
            count++;
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File source = new File(from);
        File copyFile = new File(to);
        File copyFileDirectory = new File(copyFile.getParent());
        if (!copyFileDirectory.isDirectory()) {
            copyFileDirectory.mkdirs();
        }
        try {
            Files.copy(source.toPath(), copyFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File dirPath = new File(getClass().getResource("/").getPath() + "//" + path);
        if (!dirPath.exists()) dirPath.mkdir();

        File newFile = new File(dirPath + "//" + name);
        try {
            return newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        File myFile = new File(new File(".").getAbsolutePath() + "//src//main//resources//" + fileName);
        String content = "";

        try {
            FileReader fr = new FileReader(myFile);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                content += line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
