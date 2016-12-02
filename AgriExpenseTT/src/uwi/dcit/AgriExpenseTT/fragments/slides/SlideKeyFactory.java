package uwi.dcit.AgriExpenseTT.fragments.slides;

/**
 * Created by Judith on 01/12/2016.
 */
import android.support.v4.app.FragmentActivity;



public class SlideKeyFactory extends AbstractSlideFactory{


    //    @Override
           public SlideKey getSlideKey(String item, FragmentActivity activity) {

            if (item == null) {
                return null;
            }
            if (item.equalsIgnoreCase("ManageResourceSlides")) {
                return new ManageResourceSlides();
            } else if (item.equalsIgnoreCase("CalculateSalesSlides")) {
                return new CalculateSalesSlides();
            } else if (item.equalsIgnoreCase("GeneratingReportSlides")) {
                return new GeneratingReportSlides();
            } else if (item.equalsIgnoreCase("HiringLabourSlides")) {
                return new HiringLabourSlides();
            } else if (item.equalsIgnoreCase("NewPurchaseSlides")) {
                return new NewPurchaseSlides();
            } else if (item.equalsIgnoreCase("IntroductionSlides")) {
                return new IntroductionSlides();
            }

            return null;
        }
 //   }

}
