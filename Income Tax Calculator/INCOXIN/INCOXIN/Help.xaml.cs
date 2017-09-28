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
    public partial class Help : PhoneApplicationPage
    {
        Random rand = new Random();
        public Help()
        {
            InitializeComponent();
        }

        private void textBlock1_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.Navigate(new Uri("/About.xaml", UriKind.Relative));
            e.Complete();
            e.Handled = true;
        }

        private void textBlock2_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.Navigate(new Uri("/Detail.xaml", UriKind.Relative));
            e.Complete();
            e.Handled = true;
        }

        private void textBlock3_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.Navigate(new Uri("/Privacy Statement.xaml", UriKind.Relative));
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

        private void textBlock4_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }
    }
}