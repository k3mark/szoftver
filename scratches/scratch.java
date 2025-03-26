import countries.Country;
import countries.CountryRepository;
import countries.Region;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

class CountryManager extends CountryRepository {

    public Optional<Country> getLargesCountry(){
        return getAll().stream()
                .filter(country -> country.area() != null)
                .max(Comparator.comparing(Country::area));
    }

    public void printNamesOfCountriesWithNullArea(){
        getAll().stream()
                .filter(country -> country.area() == null)
                .map(Country::name)
                .forEach(System.out::println);
    }

    public DoubleSummaryStatistics getAreaStatsV1(){
        return getAll().stream()
                .map(Country::area)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();
    }

    public DoubleSummaryStatistics getAreaStatsV2(){
        return getAll().stream()
                .map(country -> country.area() != null ? country.area() : BigDecimal.ZERO)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();
    }

    public BigDecimal getTotalAreaV1(){
        return getAll().stream()
                .map(Country::area)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public double getTotalAreaV2(){
        return getAll().stream()
                .map(Country::area)
                .filter(Objects::nonNull)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

    public void printDifferentRegionsTimeszonesOfOceania(Region region){
        getAll().stream()
                .filter(country -> country.region().equals(region))
                .flatMap(country -> country.timezones().stream())
                .distinct()
                .forEach(System.out::println);
    }

    public String getCommaSeparatedCountyNames(){
        return getAll().stream()
                .map(Country::name)
                .sorted()
                .collect(Collectors.joining(","));
    }

    public Map<String, String> getCountryCodeNameMap(){
        return getAll().stream()
                .collect(Collectors.toMap(Country::code, Country::name));
    }

    public Map<String, Country> getCountryMap(){
        return getAll().stream()
                .collect(Collectors.toMap(Country::code, Function.identity()));
    }

    public Map<Boolean, Long> getNumberOfEuropianAndNonEuropianCountries(){
        return getAll().stream()
                .collect(partitioningBy(country -> country.region() == Region.EUROPE, counting()));
    }

    public Map<Region, List<Country>> getCountriesByRegion(){
        return getAll().stream()
                .collect(groupingBy(Country::region));
    }

    public Map<Region, Long> getNumberOfCountriesByRegion(){
        return getAll().stream()
                .collect(groupingBy(Country::region, counting()));
    }

    public Map<Region, Double> getAvgPopulationByRegion(){
        return getAll().stream()
                .collect(groupingBy(Country::region, averagingLong(Country::population)));
    }

    public Map<Region, Double> getRegionAndTimezones(){
        return getAll().stream()
                .collect(groupingBy(Country::region,averagingInt(country -> country.timezones().size())));
    }

    public Map<Region, Optional<Country>> getMostPopulousCountriesByRegion(){
        return getAll().stream()
                .collect(groupingBy(Country::region, maxBy(Comparator.comparing(Country::population))));
    }

    public Map<Region,Country> getMostPopulousCountriesByRegionV2(){
        return getAll().stream()
                .collect(groupingBy(Country::region, collectingAndThen(maxBy(Comparator.comparing(Country::population)),Optional::get)));
    }


    public static void main(String[] args) {
        var manager = new CountryManager();
        //pluszpont
        //System.out.println(manager.getRegionAndTimezones());

        //1.feladat
        // manager.getLargesCountry().ifPresent(System.out::println);

        //2.feladat
        // manager.printNamesOfCountriesWithNullArea();

        //3.fealdat
        //System.out.println(manager.getAreaStatsV1());
        //System.out.println(manager.getAreaStatsV2());

        //4.feladat
        //System.out.println(manager.getTotalAreaV1());
        //System.out.println(manager.getTotalAreaV2());
        //plusz feladat
        //manager.printDifferentRegionsTimeszonesOfOceania(Region.OCEANIA);

        //5.fealdat
        //System.out.println(manager.getCommaSeparatedCountyNames());

        //6.feladat
        //var countryCodeNameMap = manager.getCountryCodeNameMap();
        //System.out.println(countryCodeNameMap);
        //countryCodeNameMap.forEach((code,name) -> System.out.printf("%s: %s\n", code, name));
        //countryCodeNameMap.entrySet().stream()
        //        .sorted(Map.Entry.comparingByKey())
        //        .forEach(entry -> System.out.printf("%s: %s\n", entry.getKey(), entry.getValue()));

        //7.feladat
        //var countryMap = manager.getCountryMap();
        //var hungary = countryMap.get("HU");
        //System.out.println(hungary);

        //9.feladat
        //System.out.println(manager.getNumberOfEuropianAndNonEuropianCountries());

        //10.feladat
        //System.out.println(manager.getCountriesByRegion());

        //11.fealdat
        //System.out.println(manager.getNumberOfCountriesByRegion());
        //manager.getNumberOfCountriesByRegion()
        //        .entrySet()
        //        .stream()
        //        .sorted(Map.Entry.comparingByValue())
        //        .forEach(System.out::println);

        //13.feladat
        //System.out.println(manager.getAvgPopulationByRegion());

        //feladat
        //System.out.println(manager.getMostPopulousCountriesByRegion());
        //System.out.println(manager.getMostPopulousCountriesByRegionV2());
    }
}