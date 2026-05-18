package Model.users;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import Model.enums.Language;

public abstract class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String employeeId;
    protected BigDecimal salary;
    protected LocalDate hireDate;
    protected String officeNumber;
    protected String department;

    public Employee(int id, String fullName, String email, String password,
                    String phoneNumber, Language language,
                    String employeeId, BigDecimal salary,
                    String officeNumber, String department) {

        super(id, fullName, email, password, phoneNumber, language);

        this.employeeId = employeeId;
        this.salary = (salary != null) ? salary : BigDecimal.ZERO;
        this.officeNumber = officeNumber;
        this.department = department;
        this.hireDate = LocalDate.now();
    }

    public BigDecimal getAnnualSalary() {
        return salary.multiply(BigDecimal.valueOf(12));
    }

    public void receiveRaise(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            salary = salary.add(amount);
        }
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + department + ")";
    }
}
