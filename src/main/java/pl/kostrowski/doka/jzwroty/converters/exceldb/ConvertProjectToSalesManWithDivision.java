package pl.kostrowski.doka.jzwroty.converters.exceldb;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.db.DivisionId;
import pl.kostrowski.doka.jzwroty.model.db.ProjectToSalesManWithDivision;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConvertProjectToSalesManWithDivision {

    private final static Logger LOG = LoggerFactory.getLogger(ConvertProjectToSalesManWithDivision.class);

    public List<ProjectToSalesManWithDivision> convert(List<ProjectExcel> dirtyInput) {

        List<ProjectToSalesManWithDivision> result = new LinkedList<>();
        List<ProjectExcel> cleanInput = cleanInputList(dirtyInput);

        for (ProjectExcel projectExcel : cleanInput) {

            String projectId = projectExcel.getRentalProjectNumber();
            String commisionDivision = projectExcel.getSalesManCommissionDivision();
            String turoverDivision = projectExcel.getSalesManTurnoverDivision();

            List<ProjectToSalesManWithDivision> projectToSalesManWithDivisions = parseCommissionAndTurnover(projectId, commisionDivision, turoverDivision);

            LOG.trace(projectToSalesManWithDivisions.toString());

            result.addAll(projectToSalesManWithDivisions);
        }

        return result;
    }


    private List<ProjectToSalesManWithDivision> parseCommissionAndTurnover(String projectId, String commisionDivision, String turoverDivision) {

        List<ProjectToSalesManWithDivision> result = new LinkedList<>();

        Map<String, Double> commision = divideStrings(commisionDivision, ",");
        Map<String, Double> turnover = divideStrings(turoverDivision, "\\)");

        List<String> salesmansInProject = new LinkedList<>();
        salesmansInProject.addAll(commision.keySet());
        salesmansInProject.addAll(turnover.keySet());

        for (String salesmnanId : salesmansInProject) {
            ProjectToSalesManWithDivision pts = new ProjectToSalesManWithDivision();

            DivisionId divisionId = new DivisionId();
            divisionId.setSalesmanCode(salesmnanId);
            divisionId.setRentalProjectNumber(projectId);

            pts.setDivisionId(divisionId);
            pts.setCommissionPercentage(commision.get(salesmnanId));
            pts.setTurnoverPercentage(turnover.get(salesmnanId));

            result.add(pts);
        }
        return result;
    }

    private Map<String, Double> divideStrings(String commisionDivision, String splitOn) {

        Map<String, Double> result = new HashMap<>();

        String[] commisionSplit = commisionDivision.split(splitOn);

        String salesman;
        Double commission;

        for (String s : commisionSplit) {
            int pl = s.indexOf("PL");
            int dwukropek = s.indexOf(":");
            int nawias = s.indexOf("(") + 1;
            int procent = s.indexOf("%");

            salesman = s.substring(pl, dwukropek);

            try {
                commission = Double.parseDouble(StringUtils.chomp(s.substring(nawias, procent)));
            } catch (NumberFormatException e) {
                LOG.warn("Nie udało się sparsować procentów marży " + s.substring(nawias, procent) + " u handlowca " + salesman);
                commission = 0D;
            }

            result.put(salesman, commission);

        }
        return result;
    }

    private List<ProjectExcel> cleanInputList(List<ProjectExcel> input) {

        List<ProjectExcel> collect = input.stream()
                .filter(s -> s.getRentalProjectNumber() != null
                        && !s.getRentalProjectNumber().equals("")
                        && !s.getRentalProjectNumber().equals("Total"))
                .collect(Collectors.toList());

        return collect;
    }

}



