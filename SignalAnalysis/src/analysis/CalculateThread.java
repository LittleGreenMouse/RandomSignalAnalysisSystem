package analysis;

import view.MainController;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class CalculateThread extends Thread {

    private ArrayList<Double> data;
    private MainController mainController;

    public CalculateThread(ArrayList<Double> d, MainController mainController) {
        data = d;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        double mean = 0;
        for(Double d : data) {
            mean += d;
        }
        mean = mean / data.size();

        double variance = 0;
        for(Double d : data) {
            variance += (d - mean) * (d - mean);
        }
        variance = variance / (data.size() - 1);
        if(data.size() == 1) variance = 0;

        mainController.setMean(mean);
        mainController.setVariance(variance);
    }
}
