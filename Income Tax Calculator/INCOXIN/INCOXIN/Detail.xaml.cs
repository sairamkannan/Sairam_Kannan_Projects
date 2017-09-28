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
    public partial class Detail : PhoneApplicationPage
    {
        public Detail()
        {
            InitializeComponent();
            textBlock1.Text = "With given income per annum, income tax can\nbe calculated easily for \"General\",\"Woman\" and\n\"Senior Citizens\".Usually there is a procedure\nbeing followed by most of the people who pay\nAdvance Tax where you pay the round up value\nfor the income tax in advance.At the end of the\nyear you have to pay only remaining tax.If\nAdvance Tax will be more than Income Tax then\nremaining balance from Advance Tax will be\ncredited in remaining untaxed salary.";
        }

        private void textBlock2_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }

        private void textBlock3_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.Navigate(new Uri("/Continue.xaml", UriKind.Relative));
            e.Complete();
            e.Handled = true;
        }
    }
}