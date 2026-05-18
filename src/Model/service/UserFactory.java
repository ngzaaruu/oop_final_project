package Model.service;

import Model.enums.*;
import Model.users.*;

import java.math.BigDecimal;

public class UserFactory {

    private UserFactory() { }

    public static User createUser(UserType type,
                                  int id,
                                  String fullName,
                                  String email,
                                  String password,
                                  String phoneNumber,
                                  Language language,
                                  Object... extra) {

        switch (type) {

            case STUDENT:
                return new Student(
                        id, fullName, email, password, phoneNumber, language,
                        "ST" + id, "CS", 1
                );

            case GRADUATE_STUDENT:
                return new GraduateStudent(
                        id, fullName, email, password, phoneNumber, language,
                        "ST" + id, "CS", 1,
                        "Thesis", "MASTER"
                );

            case TEACHER:
                return new Teacher(
                        id, fullName, email, password, phoneNumber, language,
                        "EMP" + id, BigDecimal.ZERO,
                        "A1", "CS",
                        "T" + id, TeacherPosition.LECTOR
                );

            case ADMIN:
                return new Admin(
                        id, fullName, email, password, phoneNumber, language,
                        "EMP" + id, "ADMIN" + id, "HIGH"
                );

            case MANAGER:
                return new Manager(
                        id, fullName, email, password, phoneNumber, language,
                        "EMP" + id, "M" + id, ManagerType.DEPARTMENT
                );

            case TECH_SUPPORT_SPECIALIST:
                return new TechSupportSpecialist(
                        id, fullName, email, password, phoneNumber, language,
                        "EMP" + id, BigDecimal.ZERO,
                        "A1", "IT",
                        "TS" + id
                );

            case RESEARCHER:
                return new ResearchEmployee(
                        id, fullName, email, password, phoneNumber, language,
                        "EMP" + id, BigDecimal.ZERO,
                        "R1", "Research Center"
                );

            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }
}

