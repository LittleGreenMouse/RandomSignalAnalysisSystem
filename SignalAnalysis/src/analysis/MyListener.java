package analysis;

import javafx.scene.chart.XYChart;
import view.MainController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import java.util.ArrayList;

public class MyListener implements MessageListener {

    private ArrayList<Double> data;
    private MainController mainController;

    /**
     * Constructor
     * Initialize data list
     */
    public MyListener(MainController mainController) {
        data = new ArrayList<>();
        this.mainController = mainController;
    }

    @Override
    public void onMessage(Message message) {
        try {
            // receive new signal
            String m = ((TextMessage) message).getText();
            double tmp = Double.valueOf(m);
            data.add(tmp);
            mainController.setNewSignal(tmp);

            // calculate mean and variance
            CalculateThread calculate = new CalculateThread(data, mainController);
            calculate.start();

            // judge if new signal is outlier
            if(data.size() == 1){
                mainController.setOutlier(false);
            } else {
                OutlierThread outlier = new OutlierThread(tmp, mainController);
                outlier.start();
            }
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }
}
