package sample;


//import static icons.DatabaseIcons.Table;

/**
 * Created by Andreas on 2017-03-22.
 */


public class Person {
    String fName;
    String lName;
    String profession;
    int wage;
    int age;
    String skills;

    private Person(String infName, String inlName, String inProf, int inWage, int inAge, String inSkill) {
        this.fName = infName;
        this.lName = inlName;
        this.profession = inProf;
        this.wage = inWage;
        this.age = inAge;
        this.skills = inSkill;

    }
}

