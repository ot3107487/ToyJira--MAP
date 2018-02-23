package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.domain.Issue;
import sample.domain.IssueType;
import sample.domain.Status;
import sample.domain.User;
import sample.service.ServiceIssue;
import sample.utils.ListEvent;
import sample.utils.Observer;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerTester implements Observer<Issue> {
    @FXML
    TableView table;
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
    TextField txtSummary;
    @FXML
    TextField txtDesc;
    private User user = null;

    private ServiceIssue serviceIssue;
    private ObservableList<Issue> model;

    public void setUser(User user) {
        this.user = user;
    }

    public void setServiceIssue(ServiceIssue serviceIssue) {
        this.serviceIssue = serviceIssue;
        serviceIssue.addObserver(this);
        model = FXCollections.observableArrayList(serviceIssue.getAll());
        table.setItems(model);

    }

    public ControllerTester() {
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
    }

    @Override
    public void notifyEvent(ListEvent<Issue> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(), false)
                .collect(Collectors.toList()));
    }
    /*
        expected behaviour:
        complete the textfields above and click the button to add a new unassigned issue with "now" as registerDate
     */
    public void registerBug(MouseEvent event) {
        Issue bug = new Issue(serviceIssue.size() + 1, txtSummary.getText(), txtDesc.getText(),
                IssueType.Bug, "", user.getNume(), Status.Open,
                LocalDate.now().toString());
        serviceIssue.save(bug);
    }
}
