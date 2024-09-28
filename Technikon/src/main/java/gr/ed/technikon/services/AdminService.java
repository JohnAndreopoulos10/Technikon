package gr.ed.technikon.services;

import gr.ed.technikon.Repositories.RepairRepository;
import gr.ed.technikon.Repositories.RepairRepositoryInterface;
import gr.ed.technikon.enums.RepairStatus;
import gr.ed.technikon.models.Repair;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminService implements AdminServiceInterface {

    @Override
    public List<Repair> getPendingRepairs() {
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        return allRepairs.stream().filter((Repair pendingRepair) -> RepairStatus.PENDING.equals(pendingRepair.getRepairStatus())).collect(Collectors.toList());

    }

    @Override
    public void proposeCost(Long repairId, BigDecimal proposedCost) {
        Repair rp = new Repair();
        RepairRepositoryInterface rr = new RepairRepository();
        rr.findById(repairId);
        rp.setProposedCost(proposedCost);

    }

    public List<BigDecimal> getProposedCost() {
        Repair rp = new Repair();
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        BigDecimal BDCost;
        List<BigDecimal> Costs = new ArrayList();
        for (Repair repair : allRepairs) {
            BDCost = rp.getProposedCost();
            Costs.add(BDCost);

        }
        return Costs;

    }

    @Override
    public List<Optional> proposedStartEndDates(Date proposedDateOfStart, Date proposedDateOfEnd) {
        RepairRepositoryInterface rri = new RepairRepository();
        return rri.findByRangeDates(proposedDateOfStart, proposedDateOfEnd);

    }

    public List<Repair> getAllRepairs() {
        RepairRepository getRepairs = new RepairRepository();
        List<Repair> allRepairs = getRepairs.findAll();
        return getRepairs.findAll();
    }

    @Override
    public List<Date> checkActuallDate(Long repairId) {
        RepairRepository rr = new RepairRepository();
        List<Date> rDates = new ArrayList<>();

        Optional<Repair> optionalRepair = rr.findById(repairId);
        if (optionalRepair.isPresent()) {
            Repair rp = optionalRepair.get();
            if (rp.getRepairStatus().equals(RepairStatus.COMPLETE)) {
                rDates.add(rp.getDateOfStart());
                rDates.add(rp.getDateOfEnd());
            }
        }

        return rDates;
    }

    public void proposeCostsAndDates(Long repairId, BigDecimal proposedCost, Date proposedStartDate, Date proposedEndDate) {
        RepairRepositoryInterface rri = new RepairRepository();
        Optional<Repair> optionalRepair = rri.findById(repairId);
        if (optionalRepair.isPresent()) {
            Repair repair = optionalRepair.get();
            if (RepairStatus.PENDING.equals(repair.getRepairStatus())) {
                repair.setProposedCost(proposedCost);
                repair.setProposedDateOfStart(proposedStartDate);
                repair.setProposedDateOfEnd(proposedEndDate);
            }
        }
    }

}
