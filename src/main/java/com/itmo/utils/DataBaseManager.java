package com.itmo.utils;

import com.itmo.app.*;
import com.itmo.client.User;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Синглтон для работы с базой данных
 * Исполнение запросов и т.п.
 */
public class DataBaseManager {
    //For Database
    //private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String USER;
    private static String PASS;
    private static final String FILE_WITH_ACCOUNT = "account";
    private static final String TABLE_NAME = "studygroups";
    private static final String USERS_TABLE = "users";
    private static final String pepper = "1@#$&^%$)3";

    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
             BufferedReader reader = new BufferedReader(fileReader)) {
            USER = reader.readLine();
            PASS = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PassEncoder passEncoder;

    public DataBaseManager(String dbUrl, String user, String pass) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            passEncoder = new PassEncoder(pepper);
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }

    public DataBaseManager(String address, int port, String dbName, String user, String pass) {
        this("jdbc:postgresql://" + address + ":" + port + "/" + dbName, user, pass);
    }

    public DataBaseManager() {
        this(DB_URL, USER, PASS);
    }

    //загрузка коллекции в память
    public CopyOnWriteArraySet<StudyGroup> getCollectionFromDatabase() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Queries.STUDYGROUPS);
        ResultSet resultSet = statement.executeQuery();
        CopyOnWriteArraySet<StudyGroup> collection = new CopyOnWriteArraySet<>();
        while (resultSet.next()) {
            Coordinates coordinates = new Coordinates(resultSet.getLong(3), resultSet.getLong(4));
            Long studentsCount = resultSet.getLong(6) == 0 ? null : resultSet.getLong(6);
            Long height = resultSet.getLong(10) == 0 ? null : resultSet.getLong(10);
            Person person = new Person(
                    resultSet.getString(9),
                    height,
                    resultSet.getLong(11),
                    resultSet.getString(12),
                    new Location(resultSet.getDouble(13), resultSet.getLong(14), resultSet.getString(15))
            );
            User user = new User(resultSet.getString(16));
            Color userColor = getUserColor(user);
            user.setColor(userColor.getRed(), userColor.getGreen(), userColor.getBlue());
            StudyGroup studyGroup = new StudyGroup(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    coordinates,
                    DateTimeAdapter.parseToZonedDateTime(resultSet.getTimestamp(5)),
                    studentsCount,
                    FormOfEducation.valueOf(resultSet.getString(7)),
                    Semester.valueOf(resultSet.getString(8)),
                    person,
                    user,
                    new Scanner(System.in)
            );
            collection.add(studyGroup);
        }
        return collection;
    }

    //добаление нового элемента
    public boolean addGroup(StudyGroup studyGroup) {
        try {
            long groupId = generate_id("studygroups_id");
            long personId = generate_id("persons_id");
            long coordinatesId = generate_id("coordinates_id");
            long locationId = generate_id("locations_id");
            Person admin = studyGroup.getGroupAdmin();
            Coordinates coordinates = studyGroup.getCoordinates();
            Location location = admin.getLocation();

            PreparedStatement statement = connection.prepareStatement(Queries.INSERT_GROUP);
            statement.setLong(1, groupId);
            statement.setString(2, studyGroup.getName());
            statement.setLong(3, coordinatesId);
            statement.setObject(4, DateTimeAdapter.parseToTimesTamp(studyGroup.getCreationDate()));
            statement.setLong(5, studyGroup.getStudentsCount());
            statement.setObject(6, studyGroup.getFormOfEducation().getEnglish());
            statement.setObject(7, studyGroup.getSemesterEnum().getEnglish());
            statement.setLong(8, personId);
            statement.setLong(9, studyGroup.getOwner().getId());
            statement.execute();

            insertCoordinates(coordinates, coordinatesId);
            insertPerson(admin, personId, locationId);
            insertLocation(location, locationId);

            studyGroup.setId(groupId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertPerson(Person admin, long personId, long locationId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Queries.INSERT_PERSON);
        statement.setLong(1, personId);
        statement.setString(2, admin.getName());
        statement.setLong(3, admin.getHeight());
        statement.setLong(4, admin.getWeight());
        statement.setString(5, admin.getPassportID());
        statement.setLong(6, locationId);
        statement.execute();
    }

    public void insertCoordinates(Coordinates coordinates, long coordinatesId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Queries.INSERT_COORDINATES);
        statement.setLong(1, coordinatesId);
        statement.setLong(2, coordinates.getX());
        statement.setLong(3, coordinates.getY());
        statement.execute();
    }

    public void insertLocation(Location location, long locationId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Queries.INSERT_LOCATION);
        statement.setLong(1, locationId);
        statement.setDouble(2, location.getX());
        statement.setLong(3, location.getY());
        statement.setString(4, location.getName());
        statement.execute();
    }

    //удаление элемента по id
    public int remove(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.DELETE_GROUP);
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //добавление нового пользователя
    public void addUser(User user) {
        String salt = new SimplePasswordGenerator(true, true, true, true).generate(10, 10);
        String hash = passEncoder.getHash(user.getPass() + salt);
        Color userColor = user.getColor();
        try {
            long userId = generate_id("users_id");
            PreparedStatement statement = connection.prepareStatement(Queries.INSERT_USER);
            statement.setLong(1, userId);
            statement.setString(2, user.getName());
            statement.setString(3, hash);
            statement.setString(4, salt);
            statement.setDouble(5, userColor.getRed());
            statement.setDouble(6, userColor.getGreen());
            statement.setDouble(7, userColor.getBlue());
            statement.execute();
            user.setId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Color getUserColor(User user){
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER_BY_NAME);
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            Color color = null;
            if(resultSet.next()){
                color = Color.color(resultSet.getDouble("red"), resultSet.getDouble("green"), resultSet.getDouble("blue"));
            }
            return color;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //ищем пользователя
    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER_BY_NAME);
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString("salt");
            String hash = passEncoder.getHash(user.getPass() + salt);
            statement = connection.prepareStatement(Queries.SELECT_USER_BY_PASS);
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                user.setId(resultSet.getLong("id"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //ищем пользователя только по имени
    public boolean containsUserName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.CHECK_UNIQUE_USER_NAME);
            statement.setString(1, name);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //генерируем id с помощью sequence
    public long generate_id(String sequence) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Queries.GENERATE_ID);
        statement.setString(1, sequence);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong("nextval");
    }

    //удаляем все элементы, принадлежащие пользователю
    public boolean removeAll(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(Queries.DELETE_ALL_GROUPS);
            statement.setLong(1, user.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //обновляем поля элемента
    public int update(long id, StudyGroup studyGroup) {
        Person admin = studyGroup.getGroupAdmin();
        Location location = admin.getLocation();
        Coordinates coordinates = studyGroup.getCoordinates();
        try {
            long personId = generate_id("persons_id");
            long coordinatesId = generate_id("coordinates_id");
            long locationId = generate_id("locations_id");

            insertPerson(admin, personId, locationId);
            insertCoordinates(coordinates, coordinatesId);
            insertLocation(location, locationId);

            PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_GROUP);
            statement.setString(1, studyGroup.getName());
            statement.setLong(2, coordinatesId);
            statement.setLong(3, studyGroup.getStudentsCount());
            statement.setObject(4, studyGroup.getFormOfEducation().getEnglish());
            statement.setObject(5, studyGroup.getSemesterEnum().getEnglish());
            statement.setLong(6, personId);
            statement.setLong(7, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

