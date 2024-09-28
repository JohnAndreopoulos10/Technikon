package gr.ed.technikon.models;

import java.math.BigDecimal;
import gr.ed.technikon.enums.RepairType;
import gr.ed.technikon.enums.RepairStatus;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long repairId;

    @Column(nullable = true)
    private RepairType repairType;

    @Column(nullable = true)
    private String shortDescription;

    @Column(nullable = true)
    private Date dateOfSubmission;

    @Column(nullable = true)
    private String descriptionOfWork;

    @Column(nullable = true)
    private Date proposedDateOfStart;

    @Column(nullable = true)
    private Date proposedDateOfEnd;

    @Column(nullable = true)
    private BigDecimal proposedCost;

    @Column(nullable = true)
    private boolean acceptance;

    @Column(nullable = true)
    private RepairStatus repairStatus;

    @Column(nullable = true)
    private Date dateOfStart;

    @Column(nullable = true)
    private Date dateOfEnd;

    private boolean deletedRepair;

    @ManyToOne
    @JoinColumn(name = "propertyId")
    private Property property;

    @Override
    public String toString() {
        return "Repair{" + "repairId=" + repairId + ", repairType=" + repairType + ", shortDescription=" + shortDescription + ", dateOfSubmission=" + dateOfSubmission + ", descriptionOfWork=" + descriptionOfWork + ", proposedDateOfStart=" + proposedDateOfStart + ", proposedDateOfEnd=" + proposedDateOfEnd + ", proposedCost=" + proposedCost + ", acceptance=" + acceptance + ", repairStatus=" + repairStatus + ", dateOfStart=" + dateOfStart + ", dateOfEnd=" + dateOfEnd + ", property=" + property + '}';
    }

}
