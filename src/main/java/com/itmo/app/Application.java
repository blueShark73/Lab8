package com.itmo.app;

import com.itmo.client.User;
import com.itmo.exceptions.InputFormatException;
import com.itmo.exceptions.SameIdException;
import com.itmo.server.Session;
import com.itmo.utils.DataBaseManager;
import com.itmo.utils.FieldsValidator;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * класс приложения, в котором содержится ссылка на коллекцию в памяти, производится выборка элементов из нее
 * также тут хранятся активные сессии пользователей которые сейчас работают с приложением
 * синглтон для работы с коллекцией элементов
 */
@Getter
@Setter
public class Application {
    private CopyOnWriteArraySet<StudyGroup> collection;
    private CopyOnWriteArraySet<Long> idList = new CopyOnWriteArraySet<>();
    private ZonedDateTime initializationDate;
    private ConcurrentHashMap<User, Session> activeSessions = new ConcurrentHashMap<>();
    private DataBaseManager dataBaseManager;


    /**
     * загружаем коллекцию из базы данных, валидация данных из таблицы
     */
    public Application() throws SQLException {
        //Test for a local database
        dataBaseManager = new DataBaseManager();
        collection = dataBaseManager.getCollectionFromDatabase();
        for (StudyGroup studyGroup : collection) {
            try {
                //валидация данных из таблицы
                if (!FieldsValidator.checkNumber((long) studyGroup.getName().length(), 2, 19, "У элемента некорректное имя, id: " + studyGroup.getId(), false)
                        || !FieldsValidator.checkNumber(studyGroup.getStudentsCount(), 0, 50, "У элемента некорректное кол-во студентов, id: " + studyGroup.getId(), true)
                        || !FieldsValidator.checkNumber((long) studyGroup.getGroupAdmin().getName().length(), 2, 19, "У элемента некорректное имя админа, id: " + studyGroup.getId(), false)
                        || !FieldsValidator.checkNumber(studyGroup.getGroupAdmin().getHeight(), 0, 300, "У элемента некорректный рост админа, id: " + studyGroup.getId(), true)
                        || !FieldsValidator.checkNumber((long) studyGroup.getGroupAdmin().getPassportID().length(), 6, 24, "У элемента некорректный пасспортный id админа, id: " + studyGroup.getId(), false)
                        || !FieldsValidator.checkNumber(studyGroup.getGroupAdmin().getWeight(), 0, 300, "У элемента некорректный вес админа, id: " + studyGroup.getId(), false)
                        || !FieldsValidator.checkNumber(studyGroup.getId(), 0, Long.MAX_VALUE, "У элемента некорректный id, имя элемента: " + studyGroup.getName(), false)
                        || studyGroup.getFormOfEducation() == null
                        || !FieldsValidator.checkNumber(studyGroup.getCoordinates().getX(), Long.MIN_VALUE, Long.MAX_VALUE, "У элемента некорректная координата, id: " + studyGroup.getId(), false))
                    throw new InputFormatException();
                if (!idList.add(studyGroup.getId()))
                    throw new SameIdException("В коллекции присутствуют элементы с одинаковыми id, будет загружен только один!!!");
                this.collection.add(studyGroup);
            } catch (InputFormatException e) {
                System.out.println("Ошибка в бд, элемент с некорректными полями не будет добавлен в коллекцию!!!");
            } catch (SameIdException e) {
                System.out.println(e.getMessage());
            }
        }
        setInitializationDate(ZonedDateTime.now());
    }

    /**
     * сортируем коллекцию в алфавитном порядке, чтобы потом передавать ее отсортированную клиенту
     *
     * @return отсортированная коллекция
     */
    public CopyOnWriteArraySet<StudyGroup> getSortedCollection() {
        return collection.stream().sorted(Comparator.comparing(StudyGroup::getName)).collect(Collectors.toCollection(CopyOnWriteArraySet::new));
    }

    /**
     * используется для команды sum_of_students_count
     */
    public long getSumOfStudentsCount() {
        return collection.stream().mapToLong(StudyGroup::getStudentsCount).sum();
    }

    /**
     * используется для команды add_if_max
     */
    public StudyGroup getMaxStudyGroup() {
        return collection.stream().max(StudyGroup::compareTo).orElse(null);
    }

    /**
     * используется для команды add_if_min
     */
    public StudyGroup getMinStudyGroup() {
        return collection.stream().min(StudyGroup::compareTo).orElse(null);
    }

    /**
     * возвращает сессию из активных по пользователю
     * @param user - текущий пользователь
     * @return сессия
     */
    public Session getSession(User user) {
        if (user == null) return null;
        return activeSessions.get(user);
    }

    public boolean containsUserName(String name) {
        return activeSessions.keySet().stream().anyMatch(user -> user.getName().equals(name));
    }

    //добавляем новую сессию, с уникальным ключом!!!
    public boolean addSession(User user, Session session) {
        if (activeSessions.containsKey(user)) return false;
        activeSessions.put(user, session);
        return true;
    }

    //удаление сессии
    public void removeSession(User user, Session session) {
        activeSessions.remove(user, session);
    }
}
