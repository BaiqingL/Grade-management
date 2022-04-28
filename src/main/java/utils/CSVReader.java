package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVReader {
    private String fileName;

    public CSVReader(String fileName) {
        this.fileName = fileName;
    }

    public void parse() throws IOException {
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        for (CSVRecord record : records) {
            System.out.println(record.stream().toList());
        }
    }
}
