package com.itmo.app.builder;

import java.util.Scanner;

public interface Builder {
    void setCoordinates();

    void setName();

    void setCreationDate();

    void setStudentsCount();

    void setFormOfEducation();

    void setSemesterEnum();

    void setGroupAdmin();

    void setScanner(Scanner scanner);

    void setId(long id);
}
