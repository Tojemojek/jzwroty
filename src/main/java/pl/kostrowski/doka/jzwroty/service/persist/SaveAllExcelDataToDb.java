package pl.kostrowski.doka.jzwroty.service.persist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveAllExcelDataToDb {

    private PersistSalesman persistSalesman;
    private PersistProjects persistProjects;
    private PersistProjectsToSalesManWithDivisions persistProjectsToSalesManWithDivisions;
    private PersitDiscos persitDiscos;
    private PersistMainProductGroup persistMainProductGroup;

    @Autowired
    public SaveAllExcelDataToDb(PersistSalesman persistSalesman,
                                PersistProjects persistProjects,
                                PersistProjectsToSalesManWithDivisions persistProjectsToSalesManWithDivisions,
                                PersitDiscos persitDiscos,
                                PersistMainProductGroup persistMainProductGroup) {
        this.persistSalesman = persistSalesman;
        this.persistProjects = persistProjects;
        this.persistProjectsToSalesManWithDivisions = persistProjectsToSalesManWithDivisions;
        this.persitDiscos = persitDiscos;
        this.persistMainProductGroup = persistMainProductGroup;
    }

    public void convertExcelToDb() {
        persistSalesman.persist();
        persistMainProductGroup.persist();
        persistProjects.persist();
        persistProjectsToSalesManWithDivisions.persist();
        persitDiscos.persist();
    }
}
