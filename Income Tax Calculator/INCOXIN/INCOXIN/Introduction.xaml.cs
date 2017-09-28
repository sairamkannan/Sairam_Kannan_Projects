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
    public partial class Introduction : PhoneApplicationPage
    {
        Random rand = new Random();
        public Introduction()
        {
            InitializeComponent();
            textBlock2.Text = " \"INCOXIN\" , \"Income Tax in India\"\nAn Income Tax  Calculator,instantly estimates\nyour income tax for the fiscal year 2012-2013\nas per INDIAN laws.\nIt automatically computes TAX for General,\nWoman and Senior Citizens.\nThis application enables you in tax assesment\nat any moment irrespective of where you are.\n\"I am thankful for the taxes I pay because it\nmeans I am employed.\"  ";
        }

        private void textBlock3_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.Navigate(new Uri("/Continue.xaml", UriKind.Relative));
            e.Complete();
            e.Handled = true;
        }
     
        protected override void OnManipulationStarted(ManipulationStartedEventArgs args)
        {
            ContentPanel.Background = new SolidColorBrush(
            Color.FromArgb(255, (byte)rand.Next(255),
            (byte)rand.Next(255),
            (byte)rand.Next(255)));
            base.OnManipulationStarted(args);
        }

        private void textBlock1_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }

        private void Image_ImageFailed(object sender, ExceptionRoutedEventArgs e)
        {

        }
    }
}