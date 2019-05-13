package cezbor.WiFispectrometer.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart
{
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	public Chart()
	{
		dataset = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart(
				"", //title
				"d³ugoœæ fali [nm]", //xAxisLabel
				"natezenie", //yAxisLabel
				dataset, //dataset
				PlotOrientation.VERTICAL, //orientation /HORIZONTAL
				true, //legend
				true, //tooltips
				false //urls
			);
		chartPanel = new ChartPanel(chart);
	}
	
	public void generateSeries(float[] luminanceArray, Comparable<String> SeriesKey)
	{
		XYSeries series = new XYSeries(SeriesKey);
		
		int i = 0;
		for(float lum : luminanceArray)
		{
			series.add(Converter.convertPxToNanometers(i), lum);
			i++;
		}
		
		int index = dataset.getSeriesIndex(SeriesKey);
		if (index != -1)
			dataset.removeSeries(index);
		
		dataset.addSeries(series);
	}
	
	public ChartPanel getChartPanel()
	{
		return chartPanel;
	}
	
}
