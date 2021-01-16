package lab4;

import java.util.*;
import java.util.stream.IntStream;

import static util.CalculationUtil.roundNumber;
import static util.FileUtils.extractDataFromJsonInputFile;

public class Lab4 {

    public static void main(String[] args) {

        var phones = extractDataFromJsonInputFile("src/main/resources/lab4/input.json", Footboller[].class);

        preProcessValues(phones);

        var maxPrice = extractMaxPrice(phones);
        var maxBatteryCapacity = getMaxBatteryCapacity(phones);

        reduceToTenPointsGrade(phones, maxPrice, maxBatteryCapacity);

        var experts = fillExperts(phones);
        showTable(experts, phones);
        getAverageExpertPoints(experts);
    }

    private static Double getMaxBatteryCapacity(Footboller[] footbollers) {
        return Arrays.stream(footbollers)
                .map(Footboller::getBatteryCapacity)
                .max(Double::compare)
                .orElseThrow(() -> new RuntimeException("Unexpected data"));
    }

    private static Double extractMaxPrice(Footboller[] footbollers) {
        return Arrays.stream(footbollers)
                .map(Footboller::getPrice)
                .max(Double::compare)
                .orElseThrow(() -> new RuntimeException("Unexpected data"));
    }

    private static void reduceToTenPointsGrade(Footboller[] footbollers, Double maxPrice, Double maxMileage) {
        for (Footboller footboller : footbollers) {
            var newPrice = roundNumber(10 * footboller.getPrice() / maxPrice);
            footboller.setPrice(newPrice);
            var newBatteryCapacity = roundNumber(10 * footboller.getBatteryCapacity() / maxMileage);
            footboller.setBatteryCapacity(newBatteryCapacity);
        }
    }

    private static void preProcessValues(Footboller[] footbollers) {
        for (Footboller footboller : footbollers) {
            footboller.setPrice(1500 - footboller.getPrice());
            footboller.setYear(footboller.getYear() - 2010);
        }
    }

    private static void showTable(List<Expert> experts, Footboller[] footbollers) {
        for (Expert expert : experts) {
            printExpertPart(expert, footbollers);
        }
        System.out.println();
    }

    private static void printExpertPart(Expert expert, Footboller[] footbollers) {

        System.out.println("\n" + expert.getName());
        var optionsStrings = Arrays.asList(
                "Price                           ",
                "Year                            ",
                "Battery Capacity                ",
                "Rating                          ",
                "Expert Assessment                ",
                "Sum                              1"
        );

        var optionsList = Arrays.asList(
                "Price",
                "Year",
                "Battery Capacity",
                "Rating",
                "Expert Assessment"
        );

        System.out.println("Param                          Weight                А               B               C                   D                   E                   F");
        System.out.print("                                        ");
        Arrays.stream(footbollers)
                .map(footboller -> "  |" + footboller.getPhoneName() + "|")
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(0) + expert.getCoefficients().get(optionsList.get(0)));
        Arrays.stream(footbollers)
                .map(footboller -> "               " + footboller.getPrice())
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(1) + expert.getCoefficients().get(optionsList.get(1)));
        Arrays.stream(footbollers)
                .map(element -> "               " + element.getYear())
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(2) + expert.getCoefficients().get(optionsList.get(2)));
        Arrays.stream(footbollers)
                .map(item -> "               " + item.getBatteryCapacity())
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(3) + expert.getCoefficients().get(optionsList.get(3)));
        Arrays.stream(footbollers)
                .map(footboller -> "                " + footboller.getRating())
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(4) + expert.getCoefficients().get(optionsList.get(4)));
        IntStream.range(0, expert.getPreferences().size())
                .mapToObj(j -> "                " + expert.getPreferences().get(footbollers[j].getPhoneName()))
                .forEachOrdered(System.out::print);
        System.out.println();
        System.out.print(optionsStrings.get(5));
        IntStream.range(0, expert.getPreferences().size())
                .mapToObj(j -> "                " + expert.getPhoneSum().get(j))
                .forEachOrdered(System.out::print);
        System.out.println();
    }

    private static List<Expert> fillExperts(Footboller[] footbollers) {
        var preferencesEx1 = Map.of(
                "Apple Iphone 11", 8.0,
                "Apple Iphone 12", 9.0,
                "Samsung s20 ultra", 6.0,
                "Xiaomi Mi 10 Pro", 7.0,
                "Huawei P40 Pro", 4.0,
                "Sony Xperia 1 XQ-AT52", 5.0
        );
        var coefficientsEx1 = Map.of(
                "Price", 0.2,
                "Year", 0.35,
                "Battery Capacity", 0.2,
                "Rating", 0.15,
                "Expert Assessment", 0.1
        );

        var preferencesEx2 = Map.of(
                "Apple Iphone 11", 6.0,
                "Apple Iphone 12", 8.0,
                "Samsung s20 ultra", 7.0,
                "Xiaomi Mi 10 Pro", 8.0,
                "Huawei P40 Pro", 6.0,
                "Sony Xperia 1 XQ-AT52", 7.0
        );

        var coefficientsEx2 = Map.of(
                "Price", 0.1,
                "Year", 0.3,
                "Battery Capacity", 0.3,
                "Rating", 0.1,
                "Expert Assessment", 0.2
        );

        var preferencesEx3 = Map.of(
                "Apple Iphone 11", 8.0,
                "Apple Iphone 12", 7.0,
                "Samsung s20 ultra", 8.0,
                "Xiaomi Mi 10 Pro", 7.0,
                "Huawei P40 Pro", 6.0,
                "Sony Xperia 1 XQ-AT52", 7.0
        );

        var coefficientsEx3 = Map.of(
                "Price", 0.25,
                "Year", 0.15,
                "Battery Capacity", 0.25,
                "Rating", 0.3,
                "Expert Assessment", 0.05
        );

        var paramsList = Arrays.asList(
                "Price",
                "Year",
                "Battery Capacity",
                "Rating",
                "Expert Assessment"
        );

        var sumDecisionsEx1 = calculatePhoneDecisions(paramsList, preferencesEx1, coefficientsEx1, footbollers);
        var sumDecisionsEx2 = calculatePhoneDecisions(paramsList, preferencesEx2, coefficientsEx3, footbollers);
        var sumDecisionsEx3 = calculatePhoneDecisions(paramsList, preferencesEx2, coefficientsEx3, footbollers);

        return Arrays.asList(
                new Expert("Expert #1", preferencesEx1, coefficientsEx1, sumDecisionsEx1),
                new Expert("Expert #2", preferencesEx2, coefficientsEx2, sumDecisionsEx2),
                new Expert("Expert #3", preferencesEx3, coefficientsEx3, sumDecisionsEx3));

    }

    private static ArrayList<Double> calculatePhoneDecisions(List<String> paramsList, Map<String, Double> preferences, Map<String, Double> coefficients, Footboller[] footbollers) {
        var resultList = new ArrayList<Double>();
        var phonesNamesList = new ArrayList<>(preferences.keySet());

        Footboller currentFootboller;
        var counter = 0;
        for (int i = 0; i < preferences.size(); i++) {
            currentFootboller = footbollers[i];
            var sum = calculateSum(paramsList, preferences, coefficients, currentFootboller, phonesNamesList.get(counter));
            resultList.add(sum);
            counter++;
        }
        return resultList;
    }

    private static double calculateSum(List<String> paramsList, Map<String, Double> preferences, Map<String, Double> coefficients, Footboller currentFootboller, String value) {
        var sum = 0.0;
        sum += currentFootboller.getPrice() * coefficients.get(paramsList.get(0));
        sum += currentFootboller.getYear() * coefficients.get(paramsList.get(1));
        sum += currentFootboller.getBatteryCapacity() * coefficients.get(paramsList.get(2));
        sum += currentFootboller.getRating() * coefficients.get(paramsList.get(3));
        sum += preferences.get(value) * coefficients.get(paramsList.get(4));
        sum = roundNumber(sum);
        return sum;
    }

    private static void getAverageExpertPoints(List<Expert> experts) {
        var phoneNamesList = Arrays.asList(
                "Apple Iphone 11",
                "Apple Iphone 12",
                "Samsung s20 ultra",
                "Xiaomi Mi 10 Pro",
                "Huawei P40 Pro",
                "Sony Xperia 1 XQ-AT52"
        );

        var averagePointsList = new ArrayList<Double>();
        for (int iVar = 0; iVar < phoneNamesList.size(); iVar++) {
            int finalIVar = iVar;
            var averageValue = experts.stream()
                    .map(e -> e.getPhoneSum().get(finalIVar))
                    .mapToDouble(e -> e)
                    .average()
                    .orElseThrow(() -> new RuntimeException("There is no average value"));
            averagePointsList.add(averageValue);
            System.out.println("Average value for " + phoneNamesList.get(finalIVar) + ":   " + averagePointsList.get(finalIVar));
        }
        Double maxValue = averagePointsList.stream()
                .max(Double::compareTo)
                .orElseThrow(() -> new RuntimeException("There is no max value"));
        var indexOfBest = averagePointsList.indexOf(maxValue);
        System.out.println("\nBest options is " + phoneNamesList.get(indexOfBest) + " = " + averagePointsList.get(indexOfBest));
    }
}