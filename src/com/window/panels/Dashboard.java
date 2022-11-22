package com.window.panels;

import com.manage.Chart;
import com.manage.Message;
import com.manage.Waktu;
import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Gemastik Lightning
 */
public class Dashboard extends javax.swing.JPanel {

    private final Chart chart = new Chart();
    
    private final Waktu waktu = new Waktu();
    
    public Dashboard() {
        initComponents();
        
//        this.chart.showPieChart(this.pnlPieChart, "Presentase Penjualan Produk", 40, 20, 15, 25);
//        this.chart.lineChartPenjualan(this.pnlLineChart);
        this.showLineChart();
        
        // mengupdate waktu
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                try{
                    while(isVisible()){
//                        System.out.println("update");
                        lblDate.setText(waktu.getUpdateWaktu() + "  ");
                        Thread.sleep(100);
                    }
                }catch(InterruptedException ex){
                    Message.showException(this, "Terjadi Kesalahan Saat Mengupdate Tanggal!\n" + ex.getMessage(), ex, true);
                }
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDate = new javax.swing.JLabel();
        pembeli = new javax.swing.JLabel();
        saldo = new javax.swing.JLabel();
        pemasukan = new javax.swing.JLabel();
        pengeluaran = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(957, 650));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDate.setText("-");
        add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 260, 20));

        pembeli.setText("jLabel1");
        add(pembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 80, 60, 20));

        saldo.setText("jLabel1");
        add(saldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 60, 20));

        pemasukan.setText("jLabel1");
        add(pemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 60, 20));

        pengeluaran.setText("jLabel1");
        add(pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 60, 20));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-dashboard-075.png"))); // NOI18N
        add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "Makanan" , new Double( 20 ) );  
      barDataset.setValue( "Minuman" , new Double( 20 ) );   
      barDataset.setValue( "Snack" , new Double( 40 ) );    
      barDataset.setValue( "ATK" , new Double( 10 ) );  
      
      //create chart
      JFreeChart piechart = ChartFactory.createPieChart("Penjualan Produk",barDataset, false,true,false);//explain
      piechart.setTitle(new TextTitle("Pie Chart", new java.awt.Font("Ebrima", 1, 22)));
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("Makanan", new Color(226,226,0));
       piePlot.setSectionPaint("Minuman", new Color(52,200,38));
       piePlot.setSectionPaint("Snack", new Color(255,43,237));
       piePlot.setSectionPaint("ATK", new Color(49,165,192));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
//        pnlPieChart.removeAll();
//        pnlPieChart.add(barChartPanel, BorderLayout.CENTER);
//        pnlPieChart.validate();
    }
    
    public void showLineChart(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(170, "Amount", "Kamis");
        dataset.setValue(150, "Amount", "Jumat");
        dataset.setValue(80, "Amount", "Sabtu");
        dataset.setValue(50, "Amount", "Minggu");
        dataset.setValue(180, "Amount", "Senin");
        dataset.setValue(200, "Amount", "Selasa");
        dataset.setValue(200, "Amount", "Rabu");
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Penjualan Produk","Hari","Jumlah", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        linechart.setTitle(new TextTitle("Penjualan Produk Minggu Ini", new java.awt.Font("Ebrima", 1, 21)));
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(255,2,9);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(lineChartPanel, BorderLayout.CENTER);
//        pnlLineChart.validate();
    }

    public void showHistogram(){
        
         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
        
         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
            XYPlot plot= chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(barpChartPanel2, BorderLayout.CENTER);
//        pnlLineChart.validate();
    }

    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart chart = ChartFactory.createBarChart("contribution","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
//        pnlLineChart.removeAll();
//        pnlLineChart.add(barpChartPanel, BorderLayout.CENTER);
//        pnlLineChart.validate();
        
        
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel pemasukan;
    private javax.swing.JLabel pembeli;
    private javax.swing.JLabel pengeluaran;
    private javax.swing.JLabel saldo;
    // End of variables declaration//GEN-END:variables
}
