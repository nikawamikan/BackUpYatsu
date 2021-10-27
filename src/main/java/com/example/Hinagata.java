package com.example;

/**
 * Jsonオブジェクトを保存するクラスにimplimentしたいinterfaceです これ入れて雛形とgetClazzを書くとスムーズに動くハズ
 */
public interface Hinagata<T> {

    String getHinagata();

    Class<T> getClazz();

    String jsonPath();
}
