package pl.kostrowski.doka.jzwroty.converters.exceldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.db.MainProductGroupDb;
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertMainProductGroup {

    private final static Logger LOG = LoggerFactory.getLogger(ConvertMainProductGroup.class);


    public List<MainProductGroupDb> convert(List<MainProductGroupExcel> dirtyInput) {

        List<MainProductGroupDb> result = new LinkedList<>();

        List<MainProductGroupExcel> input = cleanInputList(dirtyInput);

        for (MainProductGroupExcel mainProductGroupExcel : input) {

            MainProductGroupDb mainProductGroupDb = new MainProductGroupDb();

            mainProductGroupDb.setMainProductGroupCode(mainProductGroupExcel.getMainProductGroupCode());
            mainProductGroupDb.setMainProductGroupText(mainProductGroupExcel.getMainProductGroupText());

            result.add(mainProductGroupDb);

            LOG.debug(mainProductGroupDb.toString());
        }
        return result;
    }

    private List<MainProductGroupExcel> cleanInputList(List<MainProductGroupExcel> input) {

        List<MainProductGroupExcel> collect = input.stream()
                .filter(s -> s.getMainProductGroupCode() != null
                        && !s.getMainProductGroupCode().equals("")
                        && !s.getMainProductGroupCode().equals("Total"))
                .collect(Collectors.toList());

        return collect;
    }
}
