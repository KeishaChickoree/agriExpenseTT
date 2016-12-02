package uwi.dcit.AgriExpenseTT.fragments.slides;

/**
 * Created by Judith on 01/12/2016.
 */

public class FactoryProducer {
    public static AbstractSlideFactory getSlideFactory(String type) {
    if(type.equalsIgnoreCase("SlideKey")) {
        return new SlideKeyFactory();
    }
    return null;
}

}
