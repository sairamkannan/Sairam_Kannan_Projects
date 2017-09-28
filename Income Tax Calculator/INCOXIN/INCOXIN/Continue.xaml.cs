using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;

namespace INCOXIN
{
    public partial class Continue : PhoneApplicationPage
    {
        public Continue()
        {
            InitializeComponent();
            SupportedOrientations = SupportedPageOrientation.Portrait | SupportedPageOrientation.Landscape;
        }
        double netincome = 0;
        double salary;
        double netsalary;
        double advtax= 0;
       
        double nettax;

        private void salarytextBox1_TextChanged(object sender, TextChangedEventArgs e)
        {
            try
            {
                salary = Int32.Parse(salarytextBox1.Text);
            }
            catch
            {
                
            }
        }
        
    

        private void listBox1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
                ListBoxItem lbi = ((sender as ListBox).SelectedItem as ListBoxItem);
                resultBlock1.Text = lbi.Content.ToString();
        }

        private void advtaxBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            textBlock2.Text = "";
            try
            {
               advtax = double.Parse(advtaxBox.Text);
               
            }
            catch
            {
                
                
            }
        }

        private void salarybutton1_Click(object sender, RoutedEventArgs e)
        {
           
            {


                if (resultBlock1.Text == "General")
                {

                    if (salary <= 180000)
                        textBlock1.Text = "The total Income Tax : NIL";
                    else if (salary >= 180001 && salary <= 500000)
                    {
                        netincome = ((salary - 180000) * 0.1);

                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else if (salary >= 500001 && salary <= 800000)
                    {

                        netincome = ((320000 * 0.1) + ((salary - 500000) * 0.2));
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else
                    {

                        netincome = ((320000 * 0.1) + (300000 * 0.2) + ((salary - 800000) * 0.3));
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                }
                else if (resultBlock1.Text == "Senior Citizens")
                {
                    if (salary <= 250000)
                        textBlock1.Text = "The total Income Tax : NIL";
                    else if (salary >= 250001 && salary <= 500000)
                    {
                        netincome = ((salary - 250000) * 0.1);
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else if (salary >= 500001 && salary <= 800000)
                    {
                        netincome = (250000 * 0.1) + ((salary - 500000) * 0.2);
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else
                    {

                        netincome = ((250000 * 0.1) + (300000 * 0.2) + ((salary - 800000) * 0.3));
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                }
                else if (resultBlock1.Text == "Woman")
                {
                    if (salary <= 190000)
                        textBlock1.Text = "The total Income Tax : NIL";
                    else if (salary >= 190001 && salary <= 500000)
                    {
                        netincome = ((salary - 190000) * 0.1);
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else if (salary >= 500001 && salary <= 800000)
                    {
                        netincome = ((310000 * 0.1) + ((salary - 500000) * 0.2));
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                    else
                    {

                        netincome = (310000 * 0.1) + (300000 * 0.2) + ((salary - 800000) * 0.3);
                        textBlock1.Text = "The total Income Tax : " + netincome.ToString();
                    }
                }

            }
        }

        private void nettaxbutton_Click(object sender, RoutedEventArgs e)
        {
                if (resultBlock1.Text == "General")
                {
                    if (salary >= 180001)
                    {
                        if (advtax == 0)
                        {
                            nettax = netincome;

                            textBlock2.Text ="Income Tax after Advance paid: " + nettax.ToString();
                        }
                        else
                        {
                            nettax = netincome - advtax;
                            if (nettax > 0)
                                textBlock2.Text ="Income Tax after Advance paid: " + nettax.ToString();
                            else
                            {
                                nettax = advtax - netincome;
                                netsalary = ((salary - netincome) + nettax);
                               
                                textBlock2.Text = "Refunded amount: " + nettax.ToString();
                               
                            }
                        }
                    }

                    else
                    {
                        if (advtax == 0)
                        {
                            textBlock2.Text = "No need to give tax";
                        }
                        else
                        {
                            salary = salary + advtax;
                            textBlock2.Text = "Refunded amount: " + advtax.ToString();
                        }
                    }
                }
                else if (resultBlock1.Text == "Senior Citizens")
                {
                    if (salary >= 250001)
                    {
                        if (advtax == 0)
                        {
                            nettax = netincome;
                            textBlock2.Text = "Income Tax after Advance paid: " + nettax.ToString();
                        }
                        else
                        {
                            nettax = netincome - advtax;
                            if (nettax > 0)
                                textBlock2.Text = "Income Tax after Advance paid: " + nettax.ToString();
                            else
                            {
                                nettax = advtax - netincome;
                                netsalary = ((salary - netincome) + nettax);
                               
                                textBlock2.Text = "Refunded amount: " + nettax.ToString();
                               
                            }
                        }
                    }

                    else
                    {
                        if (advtax == 0)
                        {
                            textBlock2.Text = "No need to give tax";
                        }
                        else
                        {
                            salary = salary + advtax;
                            textBlock2.Text = "Refunded amount: " + advtax.ToString();
                        }
                    }
                }


                else if (resultBlock1.Text == "Woman")
                {
                    if (salary >= 190001)
                    {
                        if (advtax == 0)
                        {
                            nettax = netincome;
                            textBlock2.Text = "Income Tax after Advance paid: " + nettax.ToString();
                        }
                        else
                        {
                            nettax = netincome - advtax;
                            if (nettax > 0)
                                textBlock2.Text = "Income Tax after Advance paid: " + nettax.ToString();
                            else
                            {
                                nettax = advtax - netincome;
                                netsalary = ((salary - netincome) + nettax);
                             
                                textBlock2.Text = "Refunded amount: " + nettax.ToString();
                              
                            }
                        }
                    }

                    else
                    {
                        if (advtax == 0)
                        {
                            textBlock2.Text = "No need to give tax";
                        }
                        else
                        {
                            salary = salary + advtax;
                            textBlock2.Text = "Refunded amount: " + advtax.ToString();
                        }
                    }

                }
            }

        private void resetbutton1_Click(object sender, RoutedEventArgs e)
        {
            textBlock1.Text = " ";
            textBlock2.Text = "";
          
            salarytextBox1.Text = " ";
            advtaxBox.Text = " ";
            resultBlock1.Text = " ";
            
        }

        private void salarytextBox1_LostFocus(object sender, RoutedEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine(" salarytextBox1_LostFocus");
        }

        private void textBlock3_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }

        }
}
