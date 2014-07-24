package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

public class Execute extends Application {
	private LineChart<String, Number> chart;
	private BorderPane root = new BorderPane();
	private int sliderMax = 8;
	private int sliderMin = 1;
	private String seriesName;
	private Slider slider = new Slider();
	private CategoryAxis cAxis = new CategoryAxis();
	private NumberAxis nAxis = new NumberAxis();
	private XYChart.Series<String, Number> series = new XYChart.Series<>();

	@Override
	public void start(Stage primaryStage) {
		try {
			int sliderIndex = 1;
			sliderIndex = sliderMin;
			slider.setMin(sliderMin);
			slider.setValue(sliderMin);
			cAxis.setLabel("年齢");
			nAxis.setLabel("総数");

			LinkedList<PopulationBar> bars = Execute.this.readPopulation(sliderIndex);
			ListIterator<PopulationBar> li = bars.listIterator();
			if (!li.hasNext()) {
				System.out.println("error");
				return;
			}
			li.next();
			ObservableList<String> cList = FXCollections.observableArrayList();
			int i = 1;
			while (li.hasNext()) {
				PopulationBar bar = li.next();
				cList.add(bar.getAgeText());

				series.getData().add(
						new XYChart.Data<String, Number>(String.valueOf(i), bar
								.getPopulationInt()));
				i++;
			}
			cAxis.setCategories(cList);
			chart = new LineChart<>(cAxis, nAxis);
			chart.setTitle("年齢別人口");
			series.setName(seriesName);
			chart.getData().addAll(series);
			root.setCenter(chart);

			slider.valueProperty().addListener(new PopulationChangeListener());
			slider.setMax(sliderMax);
			root.setBottom(slider);
			Scene scene = new Scene(root, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("人口動態ビューア");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() {
		System.out.println("終了します");
	}

    /**
     * 初期化　csvファイル読み込み
     * @param String fileName csvファイル名, String charset 文字コード
     * @throws IOException 
     */
	private LinkedList<PopulationBar> readPopulation(int sliderIndex) {
		LinkedList<PopulationBar> bars = null;
		Population population = null;
		try {
			InputStream input = getClass().getResourceAsStream("population.csv");
			SliderUtil util = new SliderUtil(input,"UTF-8");
			List<String> yearIndexes = util.readYearIndex();
			sliderMax = yearIndexes.size() - 1;
			seriesName = yearIndexes.get(sliderIndex);
			population = util.readPopulation(sliderIndex);
			bars = population.getBars();

		} catch (IOException e) {
			System.out.println("ファイルが読み込めませんでした");
			Platform.exit();
		}
		return bars;
	}

    /**
     * スライダーの値をかえた時のイベントリスナークラス
     */
	private class PopulationChangeListener implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> ov,
				Number old_val, Number new_val) {
			int sliderIndex = new_val.intValue();
			XYChart.Series<String, Number> series = new XYChart.Series<>();
			LinkedList<PopulationBar> bars = Execute.this
					.readPopulation(sliderIndex);
			ListIterator<PopulationBar> li = bars.listIterator();
			if (!li.hasNext()) {
				System.out.println("error");
				return;
			}
			li.next();
			ObservableList<String> cList = FXCollections
					.observableArrayList();
			int i = 1;
			while (li.hasNext()) {
				PopulationBar bar = li.next();
				cList.add(bar.getAgeText());

				series.getData().add(
						new XYChart.Data<String, Number>(String
								.valueOf(i), bar.getPopulationInt()));
				i++;
			}
			cAxis.setCategories(cList);
			chart = new LineChart<>(cAxis, nAxis);
			chart.setTitle("年齢別人口");
			series.setName(seriesName);
			chart.getData().addAll(series);
			root.setCenter(chart);
		}

	}
}
