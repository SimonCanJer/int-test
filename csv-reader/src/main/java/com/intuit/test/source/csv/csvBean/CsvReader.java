package com.intuit.test.source.csv.csvBean;

import com.intuit.test.source.csv.api.IReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unchecked")
public class CsvReader<T, IMPL extends T> implements IReader<T> {

    private final String file;
    private final Class<? extends T> type;
    private final Function<T, String> mapper;

    public CsvReader(String file, Class<IMPL> c, Function<T,String> mapper) {
        this.file = file;
        this.type = c;
        this.mapper=mapper;

    }

    @Override
    public Map<String, T> readContent() throws FileNotFoundException {
        log.info("environment is {} ", System.getenv("ENV_DATA_SOURCE_FILE"));
        List<T> coll = new CsvToBeanBuilder<T>(new FileReader(file))
                .withType(type)
                .build().parse();
        return coll.stream().collect(Collectors.toMap(mapper, (v) -> v));
    }


}
