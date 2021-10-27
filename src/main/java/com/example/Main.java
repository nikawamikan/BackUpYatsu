package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    /**
     * テスト実行用メソッド
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String... args) throws IOException {

        // 現時刻を取得してファイル名とする
        final String copyFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".zip";

        final File dir = new File(".");

        final Path p = Paths.get(".", "old_data");

        if (!Files.exists(p)) {
            Files.createDirectories(p);
        }

        // ファイルがあったらそのまま使うしファイルがなかったら生成するメソッドなので気にせずそのまま仕様
        final File destination = new File(p + "\\" + copyFileName);

        // ToDo Try構文でメッセージ表示後終了させる
        Necessary necessary = new Necessary();
        necessary = new JsonChecker<Necessary>(necessary).getJsonObj();

        // コピーと圧縮するヤツ
        CopyAndCompress.compressDirectory(destination, dir, necessary);
    }
}