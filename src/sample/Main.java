package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.controller.ControllerDev;
import sample.controller.ControllerSef;
import sample.controller.ControllerTester;
import sample.domain.User;
import sample.domain.UserType;
import sample.repository.IssueRepository;
import sample.repository.UserRepository;
import sample.service.ServiceIssue;
import sample.service.ServiceUser;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IssueRepository repoIssue = new IssueRepository(".\\src\\sample\\Issues.txt");
        repoIssue.readFromFile();
        ServiceIssue serviceIssue = new ServiceIssue(repoIssue);

        UserRepository repoUser = new UserRepository(".\\src\\sample\\Users.txt");
        repoUser.readFromFile();
        ServiceUser serviceTest = new ServiceUser(repoUser);

        List<String> l = super.getParameters().getRaw();
        for (String user : l) {
            FXMLLoader loaderT = new FXMLLoader();
            String[] fields = user.split(";");
            String resource = "./views/";
            resource = resource + fields[1] + ".fxml";
            loaderT.setLocation(getClass().getResource(resource));
            User user1 = new User(serviceTest.size() + 1, fields[0], UserType.valueOf(fields[1]));
            serviceTest.save(user1);
            Pane paneT = (Pane) loaderT.load();
            if (fields[1].equals("Dev")) {
                ControllerDev controllerT = loaderT.getController();
                controllerT.setServiceIssue(serviceIssue);
                controllerT.setUser(user1);
                Stage t1 = new Stage();
                t1.setScene(new Scene(paneT));
                t1.show();
            }
            if (fields[1].equals("Tester")) {
                ControllerTester controllerTester = loaderT.getController();
                controllerTester.setServiceIssue(serviceIssue);
                controllerTester.setUser(user1);
                Stage t1 = new Stage();
                t1.setScene(new Scene(paneT));
                t1.show();
            }
            if (fields[1].equals("Sef")) {
                ControllerSef controllerSef = loaderT.getController();
                controllerSef.setServiceIssue(serviceIssue);
                controllerSef.setServiceUser(serviceTest);
                controllerSef.setUser(user1);
                Stage t1 = new Stage();
                t1.setScene(new Scene(paneT));
                t1.show();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
