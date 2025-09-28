package org.example.codexis_change_sumarizer.model;

import jakarta.persistence.*;

@Entity
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private State state; // NEW / OLD / EXPIRED

    private Long originalId; // odkaz na původní zákon (může být null)

    @Lob
    private String diffSummary;
    public Law() {}

    public Law(String title, String content, State state, Long originalId) {
        this.title = title;
        this.content = content;
        this.state = state;
        this.originalId = originalId;
    }

    // getters a setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
    public Long getOriginalId() { return originalId; }
    public void setOriginalId(Long originalId) { this.originalId = originalId; }

    public String getDiffSummary() {
        return diffSummary;
    }

    public void setDiffSummary(String diffSummary) {
        this.diffSummary = diffSummary;
    }

    public enum State {
        NEW,
        OLD
    }
}
