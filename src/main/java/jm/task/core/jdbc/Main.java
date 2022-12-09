package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Создаем объект класса сервиса
        UserService userService = new UserServiceImpl();
//        Создаем таблицу
        userService.createUsersTable();
//        Сохраняем в нее четырех User'ов
        userService.saveUser("Виталий", "Петров", (byte) 18);
        userService.saveUser("Евгений", "Зайцев", (byte) 43);
        userService.saveUser("Григорий", "Светлов", (byte) 45);
        userService.saveUser("Иван", "Тапурин", (byte) 25);
//        Вытаскиваем из таблицы всех User'ов и создаем из них список
        List<User> userList = userService.getAllUsers();
//        Выводим каждого User'а из списка на экран
        userList.forEach(System.out::println);
//        Очищаем таблицу
        userService.cleanUsersTable();
//        Удаляем таблицу
        userService.dropUsersTable();
//        Метод удаления User'а по id (в задачи не используется)
//        userService.removeUserById(1);
//        Закрываем соединение
        userService.close();
        }
    }

