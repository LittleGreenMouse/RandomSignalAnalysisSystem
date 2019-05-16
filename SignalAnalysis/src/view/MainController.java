package view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    public TextField newSignal;
    @FXML
    public TextField outlier;
    @FXML
    public TextField mean;
    @FXML
    public TextField variance;
    @FXML
    public LineChart<Number, Number> chart;
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;

    private double meanValue;
    private double varianceValue;

    private double xLowerBound;
    private double xUpperBound;

    private XYChart.Series<Number, Number> series;
    private int time;

    /**
     * Initialize line chart
     */
    @FXML
    public void initialize() {
        // Set y axis scale
        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1);

        // Set x axis scale
        xLowerBound = 0;
        xUpperBound = 20;
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        xAxis.setLowerBound(xLowerBound);
        xAxis.setUpperBound(xUpperBound);
        xAxis.setTickUnit(1);

        // Set points
        series = new XYChart.Series<>();
        series.setName("信号");
        chart.getData().add(series);

        // Set time to zero
        time = 0;
    }

    /**
     * Setter for new signal
     * Draw it to chart meanwhile
     * @param newSignal the newly coming signal value
     */
    public void setNewSignal(double newSignal) {
        this.newSignal.setText(String.valueOf(newSignal));

        time++;
        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        series.getData().add(new XYChart.Data<>(time, newSignal));
                    }
                });
                return null;
            }
        };
        task.run();
    }

    /**
     * Setter for outlier
     * @param outlier boolean value: if the new signal is an outlier
     */
    public void setOutlier(boolean outlier) {
        this.outlier.setText(String.valueOf(outlier));
    }

    /**
     * Setter for mean
     * @param mean mean value of signals right now
     */
    public void setMean(double mean) {
        this.mean.setText(String.valueOf(mean));
        meanValue = mean;
    }

    /**
     * Setter for variance
     * @param variance variance value of signals right now
     */
    public void setVariance(double variance) {
        this.variance.setText(String.valueOf(variance));
        varianceValue = variance;
    }

    /**
     * Getter for mean
     * @return get mean value
     */
    public double getMean() {
        return meanValue;
    }

    /**
     * Getter for variance
     * @return get variance value
     */
    public double getVariance() {
        return varianceValue;
    }

    /**
     * Slide the to the left
     */
    public void slideLeft() {
        xLowerBound++;
        xUpperBound++;
        xAxis.setLowerBound(xLowerBound);
        xAxis.setUpperBound(xUpperBound);
    }
}
