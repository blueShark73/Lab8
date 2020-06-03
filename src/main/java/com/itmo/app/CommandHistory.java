package com.itmo.app;


import com.itmo.commands.Command;
import com.itmo.utils.DateTimeAdapter;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * класс для ведения истории команд
 */
public class CommandHistory implements Serializable {
    private HashMap<Date, Command> history;
    private int size;
    public static final int DEFAULT_HISTORY_SIZE = 9;

    public CommandHistory(int size) {
        this.size = size;
        history = new HashMap<>(size);
    }

    /**
     * добавляет команду в историю, если она заполнена, то удалим следующую по очереди команду
     *
     * @param command - команда, которую добавляем
     */
    public void add(Command command) {
        if (history.size() == size) history.remove(Objects.requireNonNull(history.entrySet().stream().min(Map.Entry.comparingByKey()).orElse(null)).getKey());
        history.put(new Date(), command);
    }

    public Command getLastCommand() {
        return Objects.requireNonNull(history.entrySet().stream().max(Map.Entry.comparingByKey()).orElse(null)).getValue();
    }

    /**
     * выводит историю
     */
    public String getHistory() {
        StringBuilder result = new StringBuilder();
        result.append("Последние ").append(size).append(" команд: ").append("\n");
        history.entrySet().stream().sorted((t1,t2) -> -t1.getKey().compareTo(t2.getKey())).collect(Collectors.toList()).
                forEach(c -> result.append(DateTimeAdapter.parseToString(c.getKey())).append(" ").
                        append(c.getValue().toString()).append("\n"));
        return result.deleteCharAt(result.length() - 1).toString();
    }
}
