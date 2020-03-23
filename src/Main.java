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
        int k =Integer.parseInt(myScanner.nextLine());
        System.out.println();

        //String irisTrainingPath = "C:\\Users\\Michał\\Desktop\\iris_training.txt";
        //String irisTestPath = "C:\\Users\\Michał\\Desktop\\iris_test.txt";
        List<Iris> trainingList = readFile(irisTrainingPath);
        List<Iris> testList = readFile(irisTestPath);
        int correctMatch = 0;
        Map<String, Integer> hashMap = new HashMap<>();


        for (Iris testIris : testList) {

            for (Iris trainingIris : trainingList)
                trainingIris.setRange(testIris);

            Collections.sort(trainingList, new Comparator<Iris>() {
                @Override
                public int compare(Iris i, Iris ii) {
                    return (i.getRange() > ii.getRange() ? 1 : -1);
                }
            });


            for (int i = 0; i < k; i++) {

                String type = trainingList.get(i).getType();

                hashMap.putIfAbsent(type, 0);

                hashMap.put(type, hashMap.get(type)+1);

            }

            int matched = 0;
            String matchedType = "";

            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {

                if (entry.getValue() > matched) {
                    matched = entry.getValue();
                    matchedType = entry.getKey();
                } else if (entry.getValue() == matched) {

                    double coinFlip = Math.random() * 2;

                    //jesli wylosuje się 1 to zamieniam wartosci na nowe, jesli wylosuje sie 0 to zostawiam stare wartosci
                    if (coinFlip > 0.5) {
                        matched = entry.getValue();
                        matchedType = entry.getKey();
                    }
                }
            }
                if (matchedType.equals(testIris.getType()))
                    correctMatch++;

        }


        double accuracy = (correctMatch * 1.0 / testList.size() * 1.0) * 100.0;
        System.out.println("Ilość poprawnych sąsiadów: " + correctMatch + " || Dokładność: " + accuracy + "%");
        }






    public static List<Iris> readFile(String path) {

        List<Iris> tempList = new ArrayList<>();

        try {
            BufferedReader br= new BufferedReader(new FileReader(path));
            String line;

            while( (line= br.readLine()) != null ) {

                line=line.replaceAll(",",".");
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
