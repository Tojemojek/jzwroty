package pl.kostrowski.doka.jzwroty.service.excel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.dto.DiscosResultLinesDto;

import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDiscosCreatorTest {

    @Autowired
    ToDiscosCreator toDiscosCreator;

    @Test
    public void toDiscosDataCreator() {
        System.out.println("List\n");
        List<DiscosResultLinesDto> inputToDiscos = toDiscosCreator.createInputToDiscos("20180831");
        inputToDiscos.forEach(System.out::println);

        System.out.println("\n\nSet\n");
        Set<DiscosResultLinesDto> uniqueInputToDiscos = toDiscosCreator.createUniqueInputToDiscos("20180831");
        uniqueInputToDiscos.forEach(System.out::println);
    }

    @Test
    public void createResultFile() {
        toDiscosCreator.createResultFile("20180831");
    }
}
