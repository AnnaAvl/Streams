package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.ResourceBundle.getBundle;

public class Main {

    public static void sorting(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String str;
            var arr = new ArrayList<String>();
            while ((str = br.readLine()) != null) {
                arr.add(str);
            }
            var rule = new RuleBasedCollator(getBundle("Delimiters").getString("ORDER"));
            arr.stream().sorted(Comparator.comparing(String::length).thenComparing(rule::compare).reversed()).forEach(System.out::println);
//            arr.stream().sorted(rule.reversed()).sorted(Comparator.comparing((String::length)).reversed()).forEach(System.out::println);
//            arr.stream().sorted(rule::compare).sorted(Comparator.comparing(String::length)).forEach(System.out::println);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
	/*
	ЗАДАНИЕ 1
	В решениях применять функциональные интерфейсы и лямбда-выражения. Там, где
    это возможно, использовать Stream API.

    Напишите программу, которая считывает текстовый файл в список. Имя файла вводится в консоли.
    Программа должна напечатать слова из файла, отсортированные по длине. Если длина
    одинакова, использовать сортировку по алфавиту. Сортировка должна корректно поддерживать
    кириллицу (буква «ё» должна идти после «е» и перед «ж»).
    */

        System.out.println("ЗАДАНИЕ 1\nВведите имя файла:");
        Scanner s = new Scanner(System.in);
        //sorting(s.nextLine());
        sorting("text.txt");

    /*
    ЗАДАНИЕ 2
    В решениях применять синхронизацию только там, где это оправдано. При
    необходимости использовать функционал пакета java.util.concurrent и его подпакетов.

    Создать класс "Ящик", который вмещает 100 формуляров. Ящик одновременно наполняют 3
    клерка, каждый формуляром своего вида. По заполнении ящика вывести на экран, сколько
    формуляров каждого вида находится в ящике.
    */
        System.out.println("\nЗАДАНИЕ 2");
        Box box = new Box();
        ExecutorService ex = Executors.newFixedThreadPool(3);
        List<Future<Map<String, Integer>>> f = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();
        Callable<Map<String, Integer>> cl = new Clerk(box, lock);
        for (int i = 0; i < 3; i++) {
            Future<Map<String, Integer>> ft;
            ft = ex.submit(cl);
            f.add(ft);
        }
        for (Future<Map<String, Integer>> ft : f) {
            System.out.println(ft.get());
        }
        ex.shutdown();
    }
}
