package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {
    //DECLARE NODES
    int windowWidth = 745;
    int windowHeight = 650;
    GridPane root = new GridPane();
    HBox buttonBox = new HBox();
    HBox addNewPersonBox = new HBox();
    Scene scene = new Scene (root, windowWidth, windowHeight);
    Button findOldest = new Button("Find Oldest");
    Button findYoungest = new Button("Find Youngest");
    Button findRichest = new Button("Find Richest");
    Button sortByFirstName = new Button("Sort By First name");
    Button addPersonButton = new Button("Add Person");
    TextField addFirstnameField = new TextField();
    TextField addSurnameField = new TextField();
    TextField addProfessionField = new TextField();
    TextArea output = new TextArea();
    TextField input = new TextField();

    // CREATE AND FILL TABLE
    TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "System Designer", 350000, 34, "Photoshop"),
            new Person("Isabella", "Johnson", "Developer", 460000, 26, "Java"),
            new Person("Nancy", "Johnson", "Graphical Designer", 10000, 56, "InDesign"),
            new Person("Bernard", "Johansson", "Pixel Artist", 360000, 21, "MS Paint"),
            new Person("Lizzy", "Neil", "CEO", 1460000, 42, "CEO stuff")
            );

    @Override
    public void start(Stage primaryStage) throws Exception{
        //PRINT NODES
        root.add(buttonBox, 0, 1);
        root.add(input, 0, 2);
        root.add(table, 0, 3);
        root.add(output, 0, 4);
        root.add(addNewPersonBox, 0, 5);
        buttonBox.getChildren().addAll(findOldest, findYoungest, findRichest, sortByFirstName);

        //NODE STYLESHEET
        root.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        input.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        table.setPadding(new Insets(10, 10, 10, 10));
        table.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        output.setPadding(new Insets(10, 10, 10, 10));
        output.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        output.setEditable(false);
        output.setMinHeight(100);

        //SET CELL VALUES TO TABLE
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setMaxWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("fName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setMaxWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lName"));
        TableColumn professionCol = new TableColumn("Profession");
        professionCol.setMinWidth(100);
        professionCol.setMaxWidth(100);
        professionCol.setCellValueFactory(
                new PropertyValueFactory<>("profession"));
        TableColumn wageCol = new TableColumn("Wage");
        wageCol.setMinWidth(100);
        wageCol.setMaxWidth(100);
        wageCol.setCellValueFactory(
                new PropertyValueFactory<>("wage"));
        TableColumn ageCol = new TableColumn("Age");
        ageCol.setMinWidth(100);
        ageCol.setMaxWidth(100);
        ageCol.setCellValueFactory(
                new PropertyValueFactory<>("age"));
        TableColumn skillCol = new TableColumn("Skills");
        skillCol.setMinWidth(200);
        skillCol.setMaxWidth(200);
        skillCol.setCellValueFactory(
                new PropertyValueFactory<>("skills"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, professionCol, wageCol, ageCol, skillCol);

        //FILTER PERSON
        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // When filer is empty, show all
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare people.
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getFName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getLName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getProfession().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(person.getWage()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(person.getAge()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (person.getSkills().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Person> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        primaryStage.setTitle("Shapes");
        primaryStage.setScene(scene);
        primaryStage.show();

        //EVENT LISTENERS
        //Find Oldest
        findOldest.setOnAction((event) -> {
            int listSize=data.size();
            int oldestPerson = 0;
            int oldestPos= 0;
            for(int i=0; i<listSize; i++){
                int currentPerson = data.get(i).age;
                if(currentPerson > oldestPerson){
                    oldestPerson = currentPerson;
                    oldestPos = i;
                }
                output.setText("The oldest person in the list is "+data.get(oldestPos).fName+" "+data.get(oldestPos).lName+", at the age of "+String.valueOf(oldestPerson));
            }
        });

        //Find Youngest
        findYoungest.setOnAction((event) -> {
            int listSize=data.size();
            int youngestPerson = 999;
            int youngestPos = 0;
            for(int i=0; i<listSize; i++){
                int currentPerson = data.get(i).age;
                if(currentPerson < youngestPerson){
                    youngestPerson = currentPerson;
                    youngestPos = i;
                }
                output.setText("The youngest person in the list is "+data.get(youngestPos).fName+" "+data.get(youngestPos).lName+", at the age of "+String.valueOf(youngestPerson));
            }
        });


        //Find richest
        findRichest.setOnAction((event) -> {
            int listSize=data.size();
            int richestPersonWage = 0;
            int richestPerson = 0;
            for(int i=0; i<listSize; i++){
                int currentPerson = data.get(i).wage;
                if(currentPerson > richestPersonWage){
                    richestPersonWage = currentPerson;
                    richestPerson = i;
                }
                output.setText("The richest person in the list is "+data.get(richestPerson).fName+" "+data.get(richestPerson).getLName()+" and has a wage of "+String.valueOf(richestPersonWage));
            }
        });


        //Sort By First Name
        sortByFirstName.setOnAction((event) -> {
            int listSize=data.size();
            String message="";
            ArrayList<String> fNameArray = new ArrayList<String>();
            String intoArray = "";
            for(int i=0; i<listSize; i++){
                String currentPerson = data.get(i).fName;
                System.out.println("Current person is" +currentPerson);
                fNameArray.add(currentPerson);
            }
            fNameArray.add(intoArray);
            Collections.sort(fNameArray);
            fNameArray.remove(0);
            for(int i=0; i<listSize; i++) {
                String name = fNameArray.get(i);
                if(message=="") {
                    message = name;
                }
                else {
                    message = message+"\n"+name;
                }
            }
            output.setText(message);
            System.out.println(fNameArray);
        });
    }

    //PERSON CLASS
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


        //GETTERS AND SETTERS
        public String getFName() {
            return fName;
        }

        public void setFName(String fNameSet) {
            this.fName = fNameSet;
        }

        public String getLName() {
            return lName;
        }

        public void setLName(String lNameSet) {
            this.lName = lNameSet;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profSet) {
            this.profession = profSet;
        }

        public int getWage() {
            return wage;
        }

        public void setWage(int wageSet) {
            this.wage = wageSet;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int ageSet) {
            this.age = ageSet;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skillsSet) {
            this.skills = skillsSet;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
