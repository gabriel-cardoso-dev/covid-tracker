package dev.gabrielcardoso.covidtracker.services;

import dev.gabrielcardoso.covidtracker.models.CovidStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String CORONA_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    List<CovidStat> allCovidStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "*/30 * * * * *")
    public void fetchCoronaVirusData() throws IOException, InterruptedException {
        //System.out.println("Fetching Data...");
        List<CovidStat> newCovidStats = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CORONA_VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for(CSVRecord csvRecord: csvRecords){
            CovidStat covidStat = new CovidStat();
            covidStat.setState(csvRecord.get(0));
            covidStat.setCountry(csvRecord.get(1));
            int latestCases = Integer.parseInt(csvRecord.get(csvRecord.size() - 1));
            covidStat.setLatestTotalCases(latestCases);
            int previousCases = Integer.parseInt(csvRecord.get(csvRecord.size() - 2));
            covidStat.setDiffFromPreviousDay(latestCases - previousCases);
            newCovidStats.add(covidStat);
        }
        allCovidStats = newCovidStats;
        //System.out.println(allCovidStats.toString());
    }

    public List<CovidStat> getAllCovidStats() {
        return allCovidStats;
    }
}
