package gr.ed.technikon.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import gr.ed.technikon.Repositories.OwnerRepositoryInterface;
import gr.ed.technikon.models.Owner;
import gr.ed.technikon.services.OwnerService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OwnerServiceTest {

    @Mock
    private OwnerRepositoryInterface ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll() {
        List<Owner> owner = ownerRepository.findAll();
        assertEquals(0, owner.size());
    }

}
