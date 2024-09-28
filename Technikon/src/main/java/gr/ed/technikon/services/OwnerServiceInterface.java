package gr.ed.technikon.services;

import gr.ed.technikon.models.Property;
import gr.ed.technikon.models.Repair;
import java.util.List;

public interface OwnerServiceInterface {

    boolean acceptance(Repair repair);

    List<Property> getPropertiesByOwnerId(Long ownerId);
}
