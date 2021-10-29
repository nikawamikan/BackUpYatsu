package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.example.MessageException.*;

public class Main {

    /**
     * テスト実行用メソッド
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String... args) {

        final DateTimeFormatter logFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        System.out.println(LocalDateTime.now().format(logFormat) + "Zip copy start !!");

        try {

            if (args.length != 0) {
                if (args[0].toLowerCase() == "help") {
                    throw new MessageException(HELP_MESSAGE);
                }
            }

            ConfigJson necessary = new ConfigJson();
            necessary = new JsonChecker<ConfigJson>(necessary).getJsonObj();

            // 現時刻を取得してファイル名とする
            final String copyFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                    + ".zip";

            final File dir = new File(necessary.getFrom());

            final Path p = Paths.get(necessary.getTo(), "old_data");

            if (!Files.exists(p)) {
                try {
                    Files.createDirectories(p);
                } catch (IOException e) {
                    throw new MessageException();
                }
            }

            // ファイルがあったらそのまま使うしファイルがなかったら生成するメソッドなので気にせずそのまま仕様
            final File destination = new File(p + File.separator + copyFileName);

            // ToDo Try構文でメッセージ表示後終了させる

            // コピーと圧縮するヤツ
            CopyAndCompress.compressDirectory(destination, dir, necessary);
        } catch (MessageException e) {
            System.out.println(e);
        } finally {
            System.out.println(LocalDateTime.now().format(logFormat) + "Zip copy done !!");
        }
    }
}