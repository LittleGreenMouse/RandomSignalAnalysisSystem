package analysis;

import view.MainController;

public class OutlierThread extends Thread {

    private double signal;
    private MainController mainController;

    public OutlierThread(double signal, MainController mainController) {
        this.signal = signal;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        double mean = mainController.getMean();
        double standardDeviation = Math.sqrt(mainController.getVariance());


        if(signal >= mean - 3 * standardDeviation && signal <= mean + 3 * standardDeviation) {
            mainController.setOutlier(false);
        } else {
            mainController.setOutlier(true);
        }
    }
}
