import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

import tests.*;
import utils.TransCaixa;

public class Main {
    private static final String[] files = {
            "data/transCaixa1M.txt",
            "data/transCaixa2M.txt",
            "data/transCaixa4M.txt",
            "data/transCaixa8M.txt"
    };

    /**
     * @autor FMM
     *
     * @param linha
     * @return
     */
    public static TransCaixa strToTransCaixa(String linha) {
        double preco = 0.0;
        int ano = 0; int mes = 0; int dia = 0;
        int hora = 0; int min = 0; int seg = 0;
        String codTrans, codCaixa;
        // split()
        String[] campos = linha.split("/");
        codTrans = campos[0].trim();
        codCaixa = campos[1].trim();
        try {
            preco = Double.parseDouble(campos[2]);
        }
        catch(InputMismatchException | NumberFormatException e) { return null; }
        String[] diaMesAnoHMS = campos[3].split("T");
        String[] diaMesAno = diaMesAnoHMS[0].split(":");
        String[] horasMin = diaMesAnoHMS[1].split(":");
        try {
            dia = Integer.parseInt(diaMesAno[0]);
            mes = Integer.parseInt(diaMesAno[1]);
            ano = Integer.parseInt(diaMesAno[2]);
            hora = Integer.parseInt(horasMin[0]);
            min = Integer.parseInt(horasMin[1]);
        }
        catch(InputMismatchException | NumberFormatException e) { return null; }
        return TransCaixa.of(codTrans, codCaixa, preco, LocalDateTime.of(ano, mes, dia, hora, min, 0));
    }

    /**
     * @author FMM
     *
     * @param nomeFich
     * @return
     */
    public static List<TransCaixa> setup(String nomeFich) {
        List<TransCaixa> ltc = new ArrayList<>();
        try (Stream<String> sTrans = Files.lines(Paths.get(nomeFich))) {
            ltc = sTrans.map(linha -> strToTransCaixa(linha)).collect(toList());
        }
        catch(IOException exc) { out.println(exc.getMessage()); }
        return ltc;
    }

    public static String[][] runTests(Test ... tests) {
        String[][] table = new String[tests.length + 1][];

        // Generate header
        table[0] = Stream.concat(Stream.of("Input"), Arrays.stream(tests)
                .map(Test::results).flatMap(result -> result.keySet().stream())
                .distinct()
                .sorted()).toArray(String[]::new);

        for (int i = 0; i < tests.length; i++) {
            Map<String, Double> results = tests[i].results();
            table[i + 1] = new String[results.size() + 1];
            table[i + 1][0] = tests[i].input().orElse("");

            for (int j = 0; j < results.size(); j++) {
                Double time = results.get(table[0][j + 1]);
                table[i + 1][j + 1] = String.format("%.6f", time);
            }
        }

        return table;
    }

    public static void printTable(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                out.print("\"" + table[i][j] + "\",");
            }
            out.println();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            out.println("Usage: bjs [test number]");
            System.exit(1);
        }

        int testNumber = Integer.valueOf(args[0]);

        Test[] tests = null;

        if (testNumber == 1) {
            tests = Arrays.stream(files).map(Main::setup).map(T1::new).toArray(Test[]::new);
        } else if (testNumber == 2) {
            tests = Arrays.stream(files).map(Main::setup).map(T2::new).toArray(Test[]::new);
        } else if (testNumber == 3) {
            tests = Stream.of(1000000, 2000000, 4000000, 8000000).map(T3::new).toArray(Test[]::new);
        } else if (testNumber == 4) {
            tests = Stream.of(1000000, 2000000, 4000000, 8000000).map(T4::new).toArray(Test[]::new);
        } else if (testNumber == 5) {
            tests = Arrays.stream(files).map(Main::setup).map(T5::new).toArray(Test[]::new);
        } else if (testNumber == 6) {

        } else if (testNumber == 7) {

        } else if (testNumber == 8) {
            tests = Arrays.stream(files).map(Main::setup).map(T8::new).toArray(Test[]::new);
        } else if (testNumber == 9) {
            tests = Arrays.stream(files).map(Main::setup).map(T9::new).toArray(Test[]::new);
        } else if (testNumber == 10) {

        } else if (testNumber == 11) {

        } else if (testNumber == 12) {

        }

        if (tests == null) {
            out.print("The number of specified test is invalid");
            System.exit(2);
        }

        printTable(runTests(tests));
    }
}
