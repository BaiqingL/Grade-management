package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final String fileName;

    public CSVReader(String fileName) {
        this.fileName = fileName;
    }


    public List<CSVRecord> parse() throws IOException {
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        List<CSVRecord> list = new ArrayList<>();
        for (CSVRecord record : records) {
            list.add(record);
        }
        return list;
    }
}
