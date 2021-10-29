package com.example;

import static com.example.ConfigJson.HINAGATA;
import static com.example.ConfigJson.JSON_PATH;;

public class MessageException extends Exception {

    public static final int HELP_MESSAGE = 0;
    public static final int NOT_FOUND_NENESARY_MESSAGE = 1;
    public static final int IOEXCEPTIN_MESSAGE = 2;
    public static final int ZIPEXCEPTION_MESSAGE = 3;
    public static final int JSON_SYNTAX_MESSAGE = 4;

    private static final String JSON_EXPLAN = JSON_PATH + System.lineSeparator() + HINAGATA + """
            from    ほしいファイルがある場所のカレントディレクトリ
            to      送り先の宛先ディレクトリ
            nece    fromディレクトリ内で指定するディレクトリ又はファイル
            except  neceディレクトリ内で不要なディレクトリまたはファイル
            """;

    private static final String[] MESSAGES = { """
            help
            引数として受け入れられる値は'help'のみでこの文面を表示します。
            以下のJSONにアドレスを書き込んで使用してください。
            """ + JSON_EXPLAN, """
            NotFound config.json
            出力されたファイルから必要な項目を入力してから起動してください。
            """ + JSON_EXPLAN, """
            IOException !
            不明なエラーが発生しています。
            """, """
            ZipException !
            Zip化に失敗しています！
            """, """
            MalformedJsonException !
            Jsonの構文が間違っています！確認してください！
            """ };

    String message;

    @Override
    public String toString() {
        return message;
    }

    public MessageException() {
        this(IOEXCEPTIN_MESSAGE);
        this.printStackTrace();
    }

    public MessageException(final int ERORR_CODE) {
        this.message = MESSAGES[ERORR_CODE];
    }

    public MessageException(final int ERORR_CODE, final String... EXPLAN) {
        StringBuilder messBulder = new StringBuilder();
        messBulder.append(MESSAGES[ERORR_CODE]);
        for (String str : EXPLAN) {
            messBulder.append(System.lineSeparator());
            messBulder.append(str);
        }
        this.message = messBulder.toString();
    }

}
