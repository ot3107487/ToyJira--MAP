package sample.domain;

public class Issue {
    private int id;
    private String summary;
    private String description;
    private IssueType type;
    private String assignTo; //dev id;
    private String registeredBy; //tester name;
    private Status status;
    private String registerDate;

    public Issue(int id,String summary, String description, IssueType type,String assignTo, String registeredBy,Status status, String registerDate) {
        this.id=id;
        this.summary = summary;
        this.description = description;
        this.type = type;
        this.assignTo = assignTo;
        this.registeredBy = registeredBy;
        this.status = status;
        this.registerDate= registerDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getId() {
        return id;
    }
}
