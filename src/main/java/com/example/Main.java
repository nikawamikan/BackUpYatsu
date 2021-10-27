package com.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        final Necessary necessary = JsonChecker.getNecessary("necessary.json");

        CopyAndCompress.compressDirectory(destination, dir, necessary);
    }
}