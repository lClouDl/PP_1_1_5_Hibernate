package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Создаем объект класса контролера, через который будем взаимодействовать с моделью и вью (view)
        UserService userTable = new UserServiceImpl();
//        Создаем таблицу
        userTable.createUsersTable();
//        Сохраняем в нее четырех User'ов
        userTable.saveUser("Виталий", "Петров", (byte) 18);
        userTable.saveUser("Евгений", "Зайцев", (byte) 43);
        userTable.saveUser("Григорий", "Светлов", (byte) 45);
        userTable.saveUser("Иван", "Тапурин", (byte) 25);
//        Вытаскиваем из таблицы всех User'ов и создаем из них список
        List<User> test = userTable.getAllUsers();
//        Выводим каждого User'а из списка на экран
        System.out.println(test.toString());
//        Очищаем таблицу
        userTable.cleanUsersTable();
//        Удаляем таблицу
        userTable.dropUsersTable();
//        Метод удаления User'а по id (в задачи не используется)
//        userTable.removeUserById(1);
        }
    }

