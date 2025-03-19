import countries.Country;
import countries.CountryRepository;
import countries.Region;

import java.util.*;


class CountyManager extends CountryRepository {

    public void printCountyNames(){
        for (Country country : getAll()) {
            String name = country.name();
            System.out.println(name);
        }
    }

    public void printCapitalsAlphabetical(){
        getAll().stream()
                .map(Country::capital)
                //.filter(Objects::nonNull)
                .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    public OptionalLong getMaxPopulation(){
        return getAll().stream()
                .mapToLong(Country::population)
                .max();
    }

    public OptionalDouble getAvgPopulation(){
        return getAll().stream()
                .mapToLong(Country::population)
                .average();
    }

    public LongSummaryStatistics getPopulatonStats(){
        return getAll().stream()
                .mapToLong(Country::population)
                .summaryStatistics();
    }

    public long getNumberOfCountriesInRegion(Region region){
        return getAll().stream()
                .filter(country -> country.region() == region)
                .count();
    }

    public long getTotalPopulationInRegion(Region region){
        return getAll().stream()
                .filter(country -> country.region() == region)
                .mapToLong(Country::population)
                .sum();
    }

    public void printPopulationsInRegionDescending(Region region){
        getAll().stream()
                .filter(country -> country.region() == region)
                .mapToLong(Country::population)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
    }

    public void printNamesFirstFiveCountries(){
        getAll().stream()
                .limit(5)
                .map(Country::name)
                .forEach(System.out::println);
    }

    public boolean hasEachCountryAtLeastOneTimezone(){
        return getAll().stream()
                .allMatch(country -> country.timezones().size() > 0);
    }

    public long getNumberOfDistinctTimezones(){
        return getAll().stream()
                .flatMap(country -> country.timezones().stream())
                .distinct()
                .count();
    }

    public int getLengthOgLongestCountryName(){
        return getAll().stream()
                .map(Country::name)
                .mapToInt(String::length)
                .max()
                .getAsInt();
    }

    public void printCapitalByLengthAscending(){
        getAll().stream()
                .map(Country::capital)
                .sorted(Comparator.nullsLast(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())))
                .forEach(System.out::println);
    }

    //2. feladatsor
    public Optional<String> getFirstCountryNameContainingTheWorldIslandIgnoringCase(){
        return getAll().stream()
                .map(Country::name)
                .filter(name -> name.toLowerCase().contains("island"))
                .findFirst();
    }

    public void printCountryNamesWidthTheSameFirstAndLastLetter(){
        getAll().stream()
                .map(Country::name)
                .filter(name -> {
                    var s = name.toLowerCase();
                    return s.charAt(0) == s.charAt(s.length()-1);
                })
                .forEach(System.out::println);
    }

    public void printNamesOf10LeastPopulousCountries(){
        getAll().stream()
                .sorted(Comparator.comparingLong(Country::population))
                .limit(10)
                //.map(Country::population)
                .forEach(country -> System.out.printf("%s: %d\n", country.name(), country.population()));
    }

    public void printNumberOfTimezones(){
        getAll().stream()
                .sorted(Comparator.comparingInt(country -> country.timezones().size()))
                .forEach(country -> System.out.printf("%s: %d\n", country.name(),country.timezones().size()  ));
    }

    public static void main(String[] args) {
        var manager  = new CountyManager();
        manager.printCountyNames();
        manager.printCapitalsAlphabetical();
        System.out.printf("Max population: %s\n", manager.getMaxPopulation());
        System.out.printf("Avg population: %s\n", manager.getAvgPopulation());
        System.out.println(manager.getPopulatonStats());
        System.out.printf("Number of European Countries: %d\n", manager.getNumberOfCountriesInRegion(Region.EUROPE));
        System.out.printf("Total population of Europian Countries: %d", manager.getTotalPopulationInRegion(Region.EUROPE));
        manager.printPopulationsInRegionDescending(Region.EUROPE);
        manager.printNamesFirstFiveCountries();
        System.out.println(manager.hasEachCountryAtLeastOneTimezone());
        System.out.println(manager.getNumberOfDistinctTimezones());
        System.out.println(manager.getLengthOgLongestCountryName());
        manager.printCapitalByLengthAscending();
        //2.feladatsor
        System.out.println(manager.getFirstCountryNameContainingTheWorldIslandIgnoringCase());
        manager.printCountryNamesWidthTheSameFirstAndLastLetter();
        manager.printNamesOf10LeastPopulousCountries();
        manager.printNumberOfTimezones();
    }
}