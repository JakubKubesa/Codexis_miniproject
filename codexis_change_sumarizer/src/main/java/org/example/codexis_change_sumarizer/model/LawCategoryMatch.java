package org.example.codexis_change_sumarizer.model;

import jakarta.persistence.*;

@Entity
public class LawCategoryMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Law law;

    @ManyToOne
    private Category category;

    private boolean relevant;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Law getLaw() {
        return law;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isRelevant() {
        return relevant;
    }

    public void setRelevant(boolean relevant) {
        this.relevant = relevant;
    }
}
