package com.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import static com.example.MessageException.*;

public class CopyAndCompress {
    /**
     * 指定したフォルダをZIPファイルに圧縮
     * 
     * @param destination ZIP保存先ファイル
     * @param dir         圧縮対象のルートフォルダパス
     * @throws MessageException
     */
    public static void compressDirectory(final File destination, final File dir, final ConfigJson necessary)
            throws MessageException {

        // 変数宣言
        byte[] buf = new byte[1024];
        ZipOutputStream zos = null;
        InputStream is = null;

        // ZIP対象フォルダ配下の全ファイルを取得
        List<File> files = new ArrayList<File>();
        getCurrentFiles(dir, files, necessary);
        try {

            // ZIP出力オブジェクトを取得
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destination)),
                    Charset.forName("UTF8"));

            // 全ファイルをZIPに格納
            for (File file : files) {

                // ZIP化実施ファイルの情報をオブジェクトに設定
                ZipEntry entry = new ZipEntry(
                        file.getAbsolutePath().replace(dir.getAbsolutePath() + File.separator, ""));
                zos.putNextEntry(entry);

                // ZIPファイルに情報を書き込む
                is = new BufferedInputStream(new FileInputStream(file));
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }

                // ストリームを閉じる
                is.close();
            }

        } catch (ZipException e) {
            throw new MessageException(ZIPEXCEPTION_MESSAGE);
        } catch (IOException e) {
            throw new MessageException();

            // 処理の最後にストリームは常に閉じる
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    throw new MessageException();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new MessageException();
                }
            }
        }
    }

    /**
     * 検索下層階用の処理<br/>
     * necessaryオブジェクトより不要なファイルを除外したすべてのファイルを選択
     * 
     * @param parentDir ファイル取得対象フォルダ
     * @param files     ファイル一覧
     * @param necessary 設定用Jsonをリスト化したオブジェクト
     */
    private static void getFiles(final File parentDir, final List<File> files, ConfigJson necessary) {

        // ファイル取得対象フォルダ直下のファイル,ディレクトリを走査
        for (File f : parentDir.listFiles()) {

            // ファイルの場合はファイル一覧に追加
            if (f.isFile()) {

                // exceptに表記されていないファイルのみ選択
                if (!necessary.getExcept().contains(f.getName())) {
                    files.add(f);
                }

                // ディレクトリの場合は再帰処理
            } else if (f.isDirectory()) {
                getFiles(f, files, necessary);
            }
        }
    }

    /**
     * 検索最上階用の処理<br/>
     * necessaryオブジェクトからneceのListを取得してList範囲内のディレクトリのみを選択する
     * 
     * @param parentDir ファイル取得対象フォルダ
     * @param files     ファイル一覧
     * @param necessary 設定用Jsonをリスト化したオブジェクト
     */
    private static void getCurrentFiles(final File parentDir, final List<File> files, ConfigJson necessary) {

        // ファイル取得対象フォルダ直下のファイル,ディレクトリを走査
        for (File f : parentDir.listFiles()) {

            // Necessary.jsonのnece配列に存在しないデータをスキップ
            if (!necessary.getNece().contains(f.getName())) {
                continue;
            }

            // ファイルの場合はファイル一覧に追加
            if (f.isFile()) {
                files.add(f);

                // 下層は別メソッドで除外ファイル以外を取得
            } else if (f.isDirectory()) {
                getFiles(f, files, necessary);
            }
        }
    }
}
