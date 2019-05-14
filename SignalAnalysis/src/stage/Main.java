package stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import analysis.Consumer;
import view.MainController;

import java.io.IOException;

public class Main extends Application {

    private MainController mainController;
    private Parent root;

    // Message broker address tcp://ip:port
    private final String brokerURL = "tcp://localhost:61616";
    private Consumer consumer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set primary stage
        primaryStage.setTitle("随机信号分析系统");
        primaryStage.setResizable(false);

        // Load layout and get controller
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            root = loader.load();
            mainController = loader.getController();
        } catch (IOException e) {
            System.out.println("IO: " + e);
        }

        // Connect to message broker to receive message
        consumer = new Consumer(brokerURL, mainController);

        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }

    /**
     * When close main stage, close connection to message broker
     * @throws Exception if unknown exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        consumer.close();
    }
}
