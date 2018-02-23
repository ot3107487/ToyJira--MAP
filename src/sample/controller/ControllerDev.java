package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.domain.Issue;
import sample.domain.IssueType;
import sample.domain.Status;
import sample.domain.User;
import sample.service.ServiceIssue;
import sample.utils.ListEvent;
import sample.utils.ListEventType;
import sample.utils.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerDev implements Observer<Issue> {

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

    public ControllerDev() {
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

    public void completeTask(MouseEvent event) {
        Issue issue=(Issue)table.getSelectionModel().getSelectedItem();
        if(issue.getAssignTo().equals(user.getNume())){
            issue.setStatus(Status.Closed);
            serviceIssue.put(issue);
        }
    }
}
