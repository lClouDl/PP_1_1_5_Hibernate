package jm.task.core.jdbc.dao;
//Класс - модель, который работает с бд - получает и отправляет новых юзеров в таблицу
//DAO (Data Access Object) — это объект, основная задача которого сохранять данные в базу данных, а также извлекать их из неё.
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private Util util;
    public Connection connection;
    public UserDaoJDBCImpl() {
        this.util = new Util();
        this.connection = util.getConnection();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age TINYINT);");
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы, не удалось настроить соединение с базой данных.");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            System.err.println("Во время удаления таблицы, не удалось настроить соединение с базой данных.");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastName, age) VALUES(?, ?, ?);")){
            connection.setAutoCommit(false);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.execute();
                System.out.printf("User с именем – %s добавлен в базу данных \n", name);
                connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Во время сохранинеия User'а в таблицу, не удалось настроить соединение с базой данных.");
            }
            System.err.println("Во время сохранинеия User'а в таблицу, не удалось настроить соединение с базой данных.");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Во время сохранинеия User'а в таблицу, не удалось настроить соединение с базой данных.");
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?;")){
            connection.setAutoCommit(false);
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
                connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Во время удаления User'а по id, не удалось настроить соединение с базой данных.");
            }
            System.err.println("Во время удаления User'а по id, не удалось настроить соединение с базой данных.");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Во время удаления User'а по id, не удалось настроить соединение с базой данных.");
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")){
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
                userList.get(userList.size() - 1).setId(resultSet.getLong("id"));
            }
            connection.commit();
            } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Во время изъятия всех User'ов из таблицы в список, не удалось настроить соединение с базой данных.");
            }
            System.err.println("Во время изъятия всех User'ов из таблицы в список, не удалось настроить соединение с базой данных.");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Во время изъятия всех User'ов из таблицы в список, не удалось настроить соединение с базой данных.");
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM users;");
        } catch (SQLException e) {
            System.err.println("Во время очистки таблицы, не удалось настроить соединение с базой данных.");
        }
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Возникла ошибка при закрытии соединения с базой данных.");
        }
    }
}
