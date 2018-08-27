package pl.kostrowski.doka.tools;

import java.util.List;

public class EntityCreator {



    public static void main(String[] args) throws Exception {

        MappingUtil mappingUtil = new MappingUtil();
        List<String> fileNames = mappingUtil.getFileNames();

        //regenerating ExcelModelFiles
//        for (String fileName : fileNames) {
//            mappingUtil.createFromExcelEntity(fileName);
//        }
        //regenerating ExcelColumnMappings
//        mappingUtil.createColumnMappingFromExcel(fileNames);

        //regenerating ExcelConverters
        mappingUtil.createConverterClassesFromExcel(fileNames);


//        createDtoEntity();
//        createMappingFromExcelToDto();
//        createColumnMappingsForDto();
//        createEntity();
    }


}
