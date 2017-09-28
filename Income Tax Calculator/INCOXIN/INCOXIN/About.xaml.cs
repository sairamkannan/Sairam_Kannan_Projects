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
    public partial class About : PhoneApplicationPage
    {
        public About()
        {
            InitializeComponent();
            textBlock1.Text = "Application Name :\nINCOXIN";
            textBlock2.Text = "Description :\nHelps you to calculate your income tax\nbased on AY 2012-2013 as per Indian laws.\n";

        }

        private void textBlock3_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }
    }
}