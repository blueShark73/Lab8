package com.itmo.utils;

public class Queries {
    public static String STUDYGROUPS ="select s.id, s.name, c.x, c.y,\n" +
            "       s.creation_date, s.students_count, s.form_of_education,\n" +
            "       s.semester, p.name, p.height, p.weight, p.passport_id, l.x, l.y, l.name, u.name\n" +
            "from studygroups s\n" +
            "         inner join coordinates c on s.coordinates_id = c.id\n" +
            "         inner join persons p on s.group_admin_id = p.id\n" +
            "         inner join locations l on p.location_id = l.id\n" +
            "inner join users u on s.owner_id = u.id";

    public static String GENERATE_ID = "select nextval(?)";

    public static String INSERT_USER = "insert into users values(?, ?, ?, ?, ?, ?, ?)";

    public static String INSERT_GROUP = "insert into studygroups values(?, ?, ?, ?, ?, cast(? as form_of_education), cast(? as semester), ?, ?)";

    public static String INSERT_COORDINATES = "insert into coordinates values(?, ?, ?)";

    public static String INSERT_LOCATION = "insert into locations values(?, ?, ?, ?)";

    public static String INSERT_PERSON = "insert into persons values(?, ?, ?, ?, ?, ?)";

    public static String DELETE_GROUP = "delete from studygroups where id=?";

    public static String DELETE_ALL_GROUPS = "delete from studygroups where owner_id=?";

    public static String SELECT_USER_BY_NAME = "select * from users where name=?";

    public static String SELECT_USER_BY_PASS = "select * from users where name = ? and password = ?";

    public static String CHECK_UNIQUE_USER_NAME = "select * from users where name = ?";

    public static String SELECT_GROUP_BY_ID = "select * from studygroups where id=?";

    public static String SELECT_PERSON_BY_ID = "select * from persons where id=?";

    public static String UPDATE_GROUP = "update studygroups set name=?, coordinates_id=?,\n" +
            "                       students_count=?, form_of_education=cast (? as form_of_education), semester=cast (? as semester),\n" +
            "                    group_admin_id=? where id=?";
}
