package gr.ed.technikon.models;

import gr.ed.technikon.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "property_id")
    private long propertyId;
//    @Column(unique = true)
    private long E9;
    private long propertyCode;
    private String address;
    private int yearOfConstruction;
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Owner owner;

    @OneToMany(mappedBy = "property")
    private List<Repair> repairList;

    @Column(name = "is_deleted")
    private boolean deletedProperty;

    public Property(long propertyCode, String address, int yearOfConstruction, PropertyType propertyType, Owner owner) {
//        this.E9 = E9;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
        this.owner = owner;
//        this.propertyId = E9;
        this.propertyCode = propertyCode;
        this.E9 = this.propertyCode;

    }
}
