import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter iris_training.txt path");
        String irisTrainingPath = myScanner.nextLine().trim();
        System.out.println("Enter iris_test.txt path");
        String irisTestPath = myScanner.nextLine();
        System.out.println("Enter k parameter");
        int k = Integer.parseInt(myScanner.nextLine());
        System.out.println();

        //C:\Users\Michał\Desktop\iris_training.txt
        //C:\Users\Michał\Desktop\iris_test.txt
        List<Iris> trainingList = readFile(irisTrainingPath);
        List<Iris> testList = readFile(irisTestPath);
        int correctMatch = 0;


        for (Iris testIris : testList) {

            //obliczam odległość danego rekordu testIris od każdego elementu trainingList
            for (Iris trainingIris : trainingList)
                trainingIris.setRange(testIris);

            //sortuję elementy trainingList według odległości rosnąco
            Collections.sort(trainingList, new Comparator<Iris>() {
                @Override
                public int compare(Iris i, Iris ii) {
                    return (i.getRange() > ii.getRange() ? 1 : -1);
                }
            });

            Map<String, Integer> hashMap = new HashMap<>();

            //wkładam typ i ilość wystąpień k sąsiadów testIris
            for (int i = 0; i < k; i++) {

                String type = trainingList.get(i).getType();

                hashMap.putIfAbsent(type, 0);

                hashMap.put(type, hashMap.get(type) + 1);

            }

            int matched = 0;
            String matchedType = "";

            //determinuję najczęściej występujący typ
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {

                if (entry.getValue() > matched) {
                    matched = entry.getValue();
                    matchedType = entry.getKey();
                } else if (entry.getValue() == matched) {

                    double coinFlip = Math.random() * 2;

                    //jesli wylosuje się > 0.5 to zamieniam wartosci na nowe, jesli wylosuje sie <=0.5 to zostawiam stare wartosci
                    if (coinFlip > 0.5) {
                        matched = entry.getValue();
                        matchedType = entry.getKey();
                    }
                }

            }

            //Jeśli najczęstszy jest taki sam jak typ testIris, to znaczy, że jest poprawnie
            if (testIris.getType().equals(matchedType))
                correctMatch++;

        }


        double accuracy = (correctMatch * 1.0 / testList.size() * 1.0) * 100.0;

        System.out.println("Ilość poprawnych sąsiadów: " + correctMatch + " || Dokładność: " + accuracy + "%");







        while (true) {
            System.out.println("Enter parameters");
            String manualInsert = myScanner.nextLine();
            manualInsert = manualInsert.replaceAll(",", ".").trim();
            String[] tmp = manualInsert.split("\\s+");
            double[] manualvalues = new double[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                if (!tmp[i].isEmpty())
                    manualvalues[i] = Double.parseDouble(tmp[i]);
            }

            String manualType = "";

            for (Iris trainingIris : trainingList) {

                int checker = 0;

                for (int i = 0; i < trainingIris.getValues().length; i++) {

                    if (trainingIris.getValues()[i] == manualvalues[i])
                        checker++;

                    if (manualvalues.length == checker)
                        manualType = trainingIris.getType();
                }
            }
            System.out.println("Typ: " + manualType);

        }
    }




    public static List<Iris> readFile(String path) {

        List<Iris> tempList = new ArrayList<>();

        try {
            BufferedReader br= new BufferedReader(new FileReader(path));
            String line;

            while( (line= br.readLine()) != null ) {

                line=line.replaceAll(",",".").trim();
                String[] tmp = line.split("\\s+");
                double[] values= new double[tmp.length -1];
                String typeOfIris= tmp[tmp.length-1];

                for(int i=0; i<tmp.length-1 ; i++){
                    if(!tmp[i].isEmpty())
                        values[i]=Double.parseDouble(tmp[i]);
                }


                tempList.add(new Iris(values, typeOfIris));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tempList;
    }
}

