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
    public partial class Privacy_Statement : PhoneApplicationPage
    {
        public Privacy_Statement()
        {
            InitializeComponent();
            textBlock1.Text = "Privacy Statement :\nThis application does not involve in sending your\ninformation to any third party.Although it is\nmultiuser,for different user's annual income the\nincome tax will be vary.";
        }

        private void textBlock2_ManipulationStarted(object sender, ManipulationStartedEventArgs e)
        {
            this.NavigationService.GoBack();
            e.Complete();
            e.Handled = true;
        }
    }
}