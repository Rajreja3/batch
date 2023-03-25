package com.example.batch.config;

import com.example.batch.model.Sale;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Sale> reader(){
        FlatFileItemReader<Sale> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("salesRecords.csv"));
        reader.setLineMapper(getLineMapper());
        reader.setLinesToSkip(1);
        return reader;

    }

    private LineMapper<Sale> getLineMapper() {
        DefaultLineMapper<Sale> lineMapper =new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
        lineTokenizer.setNames("Region","Country","Units Sold","Unit x","Total Revenue");
        lineTokenizer.setIncludedFields(0,1,8,9,11);

        BeanWrapperFieldSetMapper<Sale> fieldSetMapper= new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Sale.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }

    @Bean
    public SaleItemProcessor processor(){
        return new SaleItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Sale> writer(){
        JdbcBatchItemWriter<Sale> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into sale(region,country,unitsSold,unitPrice,totalRevenue) values (:region,:country,:unitsSold,:unitPrice,:totalRevenue)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importSaleJob(){

        return this.jobBuilderFactory.get("SALE_IMPORT_JOB")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();

    }
    @Bean
    public Step step1(){
        return this.stepBuilderFactory.get("step1")
                .<Sale,Sale>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();

    }


}
