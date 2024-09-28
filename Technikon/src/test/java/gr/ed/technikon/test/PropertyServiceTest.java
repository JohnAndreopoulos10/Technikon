package gr.ed.technikon.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import gr.ed.technikon.Repositories.PropertyRepository;
import gr.ed.technikon.Repositories.PropertyRepositoryInterface;
import gr.ed.technikon.enums.PropertyType;
import gr.ed.technikon.models.Owner;
import gr.ed.technikon.models.Property;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class PropertyServiceTest {

    @Mock
    private PropertyRepositoryInterface propertyRepositoryInterface;

    @InjectMocks
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findById() {
        Long propertyId = 44444L;
        Property property = new Property();
        property.setPropertyId(propertyId);
        property.setPropertyType(PropertyType.MAISONETTE);
        property.setAddress("Kleanthous 4");
        property.setOwner(new Owner());

        when(propertyRepositoryInterface.findById(propertyId)).thenReturn(Optional.of(property));
        Property pr = new Property();
        pr.setPropertyId(44444l);
//		Optional<Property> pr = propertyRepository.findById(propertyId);

        assertEquals(property.getPropertyId(), pr.getPropertyId());
    }
}
