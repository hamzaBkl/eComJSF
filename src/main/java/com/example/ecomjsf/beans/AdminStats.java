package com.example.ecomjsf.beans;

import com.example.ecomjsf.model.Category;
import com.example.ecomjsf.model.Product;
import com.example.ecomjsf.service.CategoryDAOImpl;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@ManagedBean(name = "stats", eager = true)
@SessionScoped
public class AdminStats implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Category> allCategories;


	private CategoryDAOImpl categDao = new CategoryDAOImpl();

	private BarChartModel barModel;
	private PieChartModel pieModel;

	public BarChartModel getBarModel() {
		createBarModel();
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}


	public PieChartModel getPieModel() {
		createPieModel();
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	private CategoryDAOImpl categService;
	{
		categService = new CategoryDAOImpl();
	}

	public void setAllCategories(List<Category> allCategories) {
		this.allCategories = allCategories;
	}

	@PostConstruct
	public void init(){
		allCategories =getAllCategories();
		createPieModel();
	}

	public void createBarModel() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Categories Prices Avg");

		List<Number> values = new ArrayList<>();
		List<String> bgColor = new ArrayList<>();
		List<String> borderColor = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		getAllCategories().forEach(category -> {
			//values.add(category.getProducts()..stream().)
			float avg=0;

			for (Product p:category.getProducts()){
				avg+=p.getPrice();
			}
			avg/=category.getProducts().size();
			values.add(avg);

			Random rand = new Random();

			float r = rand.nextInt(255);
			float g = rand.nextInt(255);
			float b = rand.nextInt(255);

			bgColor.add("rgba("+r+", "+g+", "+b+", 0.2)");
			borderColor.add("rgb("+r+","+g+","+b+")");
			labels.add(category.getNameCat());


		});


		barDataSet.setData(values);

		barDataSet.setBackgroundColor(bgColor);

		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		data.setLabels(labels);
		barModel.setData(data);

		//Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setOffset(true);
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Category Prices Avg");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		// disable animation
//		Animation animation = new Animation();
//		animation.setDuration(0);
//		options.setAnimation(animation);

		barModel.setOptions(options);
	}


	private void createPieModel() {

		pieModel = new PieChartModel();
		ChartData data = new ChartData();

		List<String> labels = new ArrayList<>();
		PieChartDataSet dataSet = new PieChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> bgColors = new ArrayList<>();

		getAllCategories().forEach(category->{
			labels.add(category.getNameCat());
			int productsCount= category.getProducts().size();
			values.add(productsCount);

			Random rand = new Random();

			float r = rand.nextInt(255);
			float g = rand.nextInt(255);
			float b = rand.nextInt(255);
			bgColors.add("rgb("+r+","+g+","+b+")");

		});


		dataSet.setBackgroundColor(bgColors);
		dataSet.setData(values);


		data.addChartDataSet(dataSet);
		data.setLabels(labels);

		pieModel.setData(data);
	}




		
	public List<Category> getAllCategories() {
		allCategories = categDao.listCategories();
		return allCategories;
	}
	


}
