package fact.it.restaurantappstart.controller;

import fact.it.restaurantappstart.model.Table;
import fact.it.restaurantappstart.repository.TableRepository;
import org.springframework.stereotype.Controller;

@Controller
public class TableController {
    private TableRepository tableRepository;

    public TableController(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public void fillTables() {
        Table table1 = new Table();
        table1.setCode("T1");
        tableRepository.save(table1);
        Table table2 = new Table();
        table1.setCode("T2");
        tableRepository.save(table2);
        Table table3 = new Table();
        table1.setCode("T3");
        tableRepository.save(table3);
        Table table4 = new Table();
        table1.setCode("T4");
        tableRepository.save(table4);
        Table table5 = new Table();
        table1.setCode("T5");
        tableRepository.save(table5);

    }
}
