package gr.ed.technikon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long OwnerId;

    @Column(unique = true)
    private long VatNumber;

    @Column(nullable = false)
    private String Name;

    @Column(nullable = false)
    private String SurName;

    @Column(nullable = false)
    private String Address;

    @Column(nullable = false)
    private String PhoneNumber;

    @Column(nullable = false, unique = true)
    private String Email;

    @Column(nullable = false)
    private String Username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Property> propertyList;

    @Column(nullable = false)
    private boolean deletedOwner = false;

    @Override
    public String toString() {
        return "Owner{" + "OwnerId=" + OwnerId + ", VatNumber=" + VatNumber + ", Name=" + Name + ", SurName=" + SurName + ", Address=" + Address + ", PhoneNumber=" + PhoneNumber + ", Email=" + Email + ", Username=" + Username + ", password=" + password + ", propertyList=" + propertyList + ", deletedOwner=" + deletedOwner + '}';
    }

}
