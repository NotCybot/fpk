package de.thro.inf.prg3.a07.controllers;

import de.thro.inf.prg3.a07.api.OpenMensaAPI;
import de.thro.inf.prg3.a07.model.Meal;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

	private OpenMensaAPI api;
	private ObservableList<Meal> meals = FXCollections.observableArrayList();


	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnClose;

	@FXML
	private ListView<String> mealsList;

	@FXML
	private CheckBox chkVegetarian;

	@FXML
	private DatePicker datePicker;

	public MainController() {
		// initialize Retrofit
		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://openmensa.org/api/v2/")
			.build();

		api = retrofit.create(OpenMensaAPI.class);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the event handler (callback)
		btnRefresh.setOnAction(this::doUpdate);

		chkVegetarian.selectedProperty().addListener((observable, oldValue, newValue) -> doUpdate(null));

		btnClose.setOnAction(this::doClose);
	}

	private void doUpdate(ActionEvent actionEvent) {
		//  implement update logic
		System.out.println("Updating...");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String today = sdf.format(new Date());

		/**
		 * Only works hardcoded
		 */
		String testDate = "2021-11-10";

		System.out.println("Today: " + today);
		api.getMeals(229, testDate).enqueue(new Callback<List<Meal>>() {
			@Override
			public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
				if (!response.isSuccessful() || response.body() == null) {
					System.err.println("Request failed: " + response.message());
					System.err.println("HTTP Status Code: " + response.code());
					return;
				}

				// run async update!
				Platform.runLater(() -> {
					Platform.runLater(() -> {
						meals.clear();
						meals.addAll(response.body());

						// Stringify the meals
						List<String> mealString = meals.stream()
							.filter(meal -> !chkVegetarian.isSelected() || meal.isVegetarian())
							.map(Meal::toString)
							.collect(Collectors.toList());

						mealsList.setItems(FXCollections.observableArrayList(mealString));
					});
				});
			}

			@Override
			public void onFailure(Call<List<Meal>> call, Throwable t) {
				System.out.println("No Meals today :(");
			}
		});
	}
private void doClose(ActionEvent actionEvent) {
		//  implement close logic
		System.out.println("Closing...");
		Platform.exit();
	}
}
