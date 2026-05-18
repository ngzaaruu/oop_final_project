package Model.research;

import Model.exceptions.NotResearcherException;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String topic;
    private List<ResearchPaper> publishedPapers;
    private List<Researcher> participants;
    private Date startDate;
    private Date endDate;
    private BigDecimal funding;

    public ResearchProject() {
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.funding = BigDecimal.ZERO;
    }

    public ResearchProject(String projectId, String topic, Date startDate) {
        this();
        this.projectId = projectId;
        this.topic = topic;
        this.startDate = new Date(startDate.getTime());
    }

    public void addParticipant(Researcher researcher) throws NotResearcherException {
        if (researcher == null) {
            throw new NotResearcherException("Researcher cannot be null");
        }

        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void addParticipant(Object participant) throws NotResearcherException {
        if (!(participant instanceof Researcher)) {
            throw new NotResearcherException("Only researchers can join a research project");
        }
        addParticipant((Researcher) participant);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }

    public int getTotalPapers() {
        return publishedPapers.size();
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTopic() {
        return topic;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return new ArrayList<>(publishedPapers);
    }

    public List<Researcher> getParticipants() {
        return new ArrayList<>(participants);
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public BigDecimal getFunding() {
        return funding;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = new Date(endDate.getTime());
        }
    }

    public void setFunding(BigDecimal funding) {
        if (funding != null && funding.compareTo(BigDecimal.ZERO) >= 0) {
            this.funding = funding;
        }
    }

    @Override
    public String toString() {
        return "ResearchProject: " + topic + " [" + projectId + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchProject)) return false;
        ResearchProject that = (ResearchProject) o;
        return Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}

