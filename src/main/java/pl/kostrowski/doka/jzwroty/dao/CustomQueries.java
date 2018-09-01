package pl.kostrowski.doka.jzwroty.dao;

import org.springframework.stereotype.Repository;
import pl.kostrowski.doka.jzwroty.dto.ProjectLinesDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomQueries {


    @PersistenceContext
    private EntityManager em;

    public List<ProjectLinesDto> getLinesForSalesman(String salesmanId) {

        StringBuilder sb = new StringBuilder();

        sb.append("Select ");
        sb.append("dd.customer_number, ");
        sb.append("dd.customer_name, ");
        sb.append("dd.project_number, ");
        sb.append("p.rental_project_name, ");
        sb.append("dd.job_site_number, ");
        sb.append("dd.job_site_name, ");
        sb.append("dd.main_product_group_code, ");
        sb.append("mpg.MAIN_PRODUCT_GROUP_TEXT, ");
        sb.append("sum(dd.material_value_per_unit * dd.quantity), ");
        sb.append("sum(dd.total_weight), ");
        sb.append("pts.commission_PERCENTAGE, ");
        sb.append("pts.TURNOVER_PERCENTAGE ");
        sb.append("from ");
        sb.append("discos_data dd ");
        sb.append("left join project p on p.rental_project_number = dd.project_number ");
        sb.append("left join main_product_group mpg on dd.MAIN_PRODUCT_GROUP_CODE = mpg.MAIN_PRODUCT_GROUP_CODE ");
        sb.append("left join PROJECT_TO_SALESMAN  pts on pts.RENTAL_PROJECT_NUMBER = dd.project_number ");
        sb.append("where ");
        sb.append("pts.SALESMAN_CODE ='").append(salesmanId).append("' ");
        sb.append("group by ");
        sb.append("dd.customer_name, ");
        sb.append("dd.customer_number, ");
        sb.append("dd.project_number, ");
        sb.append("p.rental_project_name, ");
        sb.append("dd.job_site_number, ");
        sb.append("dd.job_site_name, ");
        sb.append("dd.main_product_group_code ");
        sb.append("order by ");
        sb.append("dd.customer_number, ");
        sb.append("dd.project_number, ");
        sb.append("dd.job_site_number, ");
        sb.append("dd.main_product_group_code ");

        String myQuery = sb.toString();

        Query query = em.createNativeQuery(myQuery);
        List<Object[]> tempResults = query.getResultList();

        List<ProjectLinesDto> orderDtos = new LinkedList<>();

        for (Object[] tempResult : tempResults) {
            int i = 0;
            ProjectLinesDto projectLinesDto = new ProjectLinesDto();

            projectLinesDto.setCustomerNumber((String) tempResult[i++]);
            projectLinesDto.setCustomerName((String) tempResult[i++]);
            projectLinesDto.setProjectNumber((String) tempResult[i++]);
            projectLinesDto.setProjectName((String) tempResult[i++]);
            projectLinesDto.setSiteNumber((String) tempResult[i++]);
            projectLinesDto.setSiteName((String) tempResult[i++]);
            projectLinesDto.setMainProductGroup((String) tempResult[i++]);
            projectLinesDto.setMainProductGroupName((String) tempResult[i++]);
            projectLinesDto.setMaterialValue(((Double) tempResult[i++]));
            projectLinesDto.setTotalWeight(((Double) tempResult[i++]));
            projectLinesDto.setCommissionPercentage(((Double) tempResult[i++]));
            projectLinesDto.setTurnoverPercentage(((Double) tempResult[i++]));
            orderDtos.add(projectLinesDto);

        }

        return orderDtos;
    }

}
