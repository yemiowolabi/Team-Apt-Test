import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TeamAptTest {
    private Map<String, Integer> summary = new HashMap<String, Integer>(5);

    private List<String> validMtnPrefixes = Arrays.asList("0703", "0706", "0803", "0806", "0810", "0813", "0814",
            "0816", "0903",
            "0906", "0913", "0916", "07025", "07026", "0704");
    private List<String> validAirtelPrefixes = Arrays.asList("0701", "0708", "0802", "0808", "0812", "0901", "0902",
            "0904", "0907",
            "0912");
    private List<String> validGlobacomPrefixes = Arrays.asList("0705", "0805", "0807", "0811", "0815", "0905", "0915");
    private List<String> validNineMobilePrefixes = Arrays.asList("0809", "0817", "0818", "0909", "0908");
    private List<String> validMtelPrefixes = Arrays.asList("0804");
    private Boolean hasParsedTxt = false;

    /**
     * Populates the summary variable with the required providers.
     */
    public void initialiseSummary() {
        this.summary.put("MTN", 0);
        this.summary.put("GLOBACOM", 0);
        this.summary.put("MTEL", 0);
        this.summary.put("9MOBILE", 0);
        this.summary.put("AIRTEL", 0);
    }

    /**
     * Parses the txt file and get the total phone numbers count for each provider.
     *
     * @throws FileNotFoundException
     */
    public void parseTxtFile() throws FileNotFoundException {
        File txtFile = new File("PhoneNumbers.txt");
        Scanner scan = new Scanner(txtFile);
        while (scan.hasNextLine()) {
            String newLine = scan.nextLine();
            if (this.validAirtelPrefixes.stream().anyMatch(s -> newLine.startsWith(s))) {
                Integer previousValue = this.summary.get("AIRTEL");
                this.summary.put("AIRTEL", previousValue + 1);
            } else if (this.validMtelPrefixes.stream().anyMatch(s -> newLine.startsWith(s))) {
                Integer previousValue = this.summary.get("MTEL");
                this.summary.put("MTEL", previousValue + 1);
            } else if (this.validMtnPrefixes.stream().anyMatch(s -> newLine.startsWith(s))) {
                Integer previousValue = this.summary.get("MTN");
                this.summary.put("MTN", previousValue + 1);
            } else if (this.validGlobacomPrefixes.stream().anyMatch(s -> newLine.startsWith(s))) {
                Integer previousValue = this.summary.get("GLOBACOM");
                this.summary.put("GLOBACOM", previousValue + 1);
            } else if (this.validNineMobilePrefixes.stream().anyMatch(s -> newLine.startsWith(s))) {
                Integer previousValue = this.summary.get("9MOBILE");
                this.summary.put("9MOBILE", previousValue + 1);
            }
        }
        scan.close();
        this.hasParsedTxt = true;
    }

    /**
     * Pretty print the summary report of the operation of parsing through the txt
     * file to
     * the terminal.
     *
     * @return Map<String, Integer>
     * @throws Exception
     */
    public void getSummaryReport() throws Exception {
        // first check if the txt file has been parsed.
        if (!this.hasParsedTxt) {
            throw new Exception("You must parse the txt file before getting summary.");
        }
        String leftAlignFormat = "| %-20s | %-21s |%n";
        System.out.format("+----------------------+-----------------------+%n");
        System.out.format("| NETWORK PROVIDER     | PHONE NUMBERS COUNT   |%n");
        System.out.format("+----------------------+-----------------------+%n");
        for (Map.Entry<String, Integer> entry : this.summary.entrySet()) {
            String provider = entry.getKey();
            String formattedPhoneNumbersCount = String.format("%,d", entry.getValue());
            System.out.format(leftAlignFormat, provider, formattedPhoneNumbersCount);
        }
        System.out.format("+----------------------+-----------------------+%n");

    }

    public static void main(String[] args) throws FileNotFoundException {
        TeamAptTest test = new TeamAptTest();
        test.initialiseSummary();
        test.parseTxtFile();
        try {
            test.getSummaryReport();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}