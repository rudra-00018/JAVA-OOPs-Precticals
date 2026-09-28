abstract class Employee {
    String name;
    int id;

    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    abstract double monthlySalary();
}

class FullTime extends Employee {
    double salary;

    FullTime(String name, int id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    @Override
    double monthlySalary() {
        return salary;
    }
}

class PartTime extends Employee {
    double hours;
    double rate;

    PartTime(String name, int id, double hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    double monthlySalary() {
        return hours * rate;
    }
}

class Intern extends Employee {
    double stipend;

    Intern(String name, int id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    double monthlySalary() {
        return stipend;
    }
}

public class Practical5_2 {
    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Rudra", 101, 50000),
            new PartTime("Rahul", 102, 80, 300),
            new Intern("Amit", 103, 15000),
            new FullTime("Jay", 104, 60000)
        };

        double total = 0;

        for (Employee employee : employees) {

            double salary = employee.monthlySalary();

            System.out.println(
                "Name: " + employee.name +
                ", ID: " + employee.id +
                ", Salary: " + salary
            );

            if (employee instanceof Intern) {
                System.out.println("Note: This employee is an Intern.");
            }

            total += salary;
        }

        System.out.println("Total Payroll: " + total);
    }
}