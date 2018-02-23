package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.domain.*;
import sample.service.ServiceIssue;
import sample.service.ServiceUser;
import sample.utils.ListEvent;
import sample.utils.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerSef implements Observer<Issue> {
    @FXML
    TableView tableIssue;
    //cu coloanele
    @FXML
    TableColumn columnSummary;
    @FXML
    TableColumn columnDesc;
    @FXML
    TableColumn columnType;
    @FXML
    TableColumn columnAssign;
    @FXML
    TableColumn columnRegistered;
    @FXML
    TableColumn columnStatus;
    @FXML
    TableColumn columnDate;

    @FXML
    TableView tableDev;
    //cu coloana
    @FXML
    TableColumn columnName;
    //for creating issues
    @FXML
    TextField txtSummary;
    @FXML
    TextField txtDesc;

    //windows owner
    private User user = null;

    private ServiceIssue serviceIssue;
    private ServiceUser serviceUser;
    private ObservableList<Issue> model;
    private ObservableList<User> modelUser;

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        ArrayList<User> m = new ArrayList<>();
        for (User user : serviceUser.getAll())
            if (user.getType().equals(UserType.Dev))
                m.add(user);
        modelUser = FXCollections.observableArrayList(m);
        tableDev.setItems(FXCollections.observableArrayList(modelUser));
    }

    public void setServiceIssue(ServiceIssue serviceIssue) {
        this.serviceIssue = serviceIssue;
        serviceIssue.addObserver(this);
        model = FXCollections.observableArrayList(serviceIssue.getAll());
        tableIssue.setItems(model);

    }

    public ControllerSef() {
    }

    @FXML
    public void initialize() {
        columnDesc.setCellValueFactory(new PropertyValueFactory<Issue, String>("description"));
        columnType.setCellValueFactory(new PropertyValueFactory<Issue, IssueType>("type"));
        columnSummary.setCellValueFactory(new PropertyValueFactory<Issue, String>("summary"));
        columnAssign.setCellValueFactory(new PropertyValueFactory<Issue, String>("assignTo"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Issue, Status>("status"));
        columnRegistered.setCellValueFactory(new PropertyValueFactory<Issue, String>("registeredBy"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Issue, String>("registerDate"));

        columnName.setCellValueFactory(new PropertyValueFactory<User, String>("nume"));
    }

    @Override
    public void notifyEvent(ListEvent<Issue> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(), false)
                .collect(Collectors.toList()));
    }

    /**
     * expected behaviour:
     * complete the text fields of abose , chose a dev from devs table and click the button to add an unassigned task
     * @param event
     */
    public void registerTask(MouseEvent event) {
        Issue task = new Issue(serviceIssue.size() + 1, txtSummary.getText(), txtDesc.getText(),
                IssueType.Task, "", user.getNume(), Status.Open,
                LocalDate.now().toString());
        serviceIssue.save(task);
    }

    /*
        expected behaviour:
        select an issue from issues table, a dev from devs table and click the button to assign the issue to dev
     */
    public void assignTaskBug(MouseEvent event) {
        Issue issue = (Issue) tableIssue.getSelectionModel().getSelectedItem();
        User dev = (User) tableDev.getSelectionModel().getSelectedItem();
        issue.setAssignTo(dev.getNume());
        serviceIssue.put(issue);
    }
}
