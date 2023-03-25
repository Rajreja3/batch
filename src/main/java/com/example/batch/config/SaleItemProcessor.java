package com.example.batch.config;

import com.example.batch.model.Sale;
import org.springframework.batch.item.ItemProcessor;

public class SaleItemProcessor implements ItemProcessor<Sale,Sale> {

    @Override
    public Sale process(Sale sale) throws Exception {

        return sale;
    }
}
