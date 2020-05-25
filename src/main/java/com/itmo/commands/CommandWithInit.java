package com.itmo.commands;

import java.io.IOException;
import java.util.Scanner;

/**
 * команды, которые нужно инициализировать на клиенте
 */
public interface CommandWithInit {
    void init(String argument, Scanner scanner) throws IOException;
}
