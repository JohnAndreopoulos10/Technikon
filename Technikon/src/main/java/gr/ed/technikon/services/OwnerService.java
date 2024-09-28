package gr.ed.technikon.services;

import gr.ed.technikon.Repositories.PropertyRepositoryInterface;
import gr.ed.technikon.models.Property;
import gr.ed.technikon.models.Repair;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class OwnerService implements OwnerServiceInterface {

    private final PropertyRepositoryInterface propertyRepository;
    //private final OwnerRepositoryInterface ownerRepository;

    @Override
    public boolean acceptance(Repair repair) {
        System.out.println(repair.toString());
        System.out.println("Do you accept the proposed repair ?");
        Scanner scanner = new Scanner(System.in);
        boolean acceptance = scanner.nextBoolean();
        repair.setAcceptance(acceptance);
        return acceptance;

    }

    public OwnerService(PropertyRepositoryInterface propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
}
