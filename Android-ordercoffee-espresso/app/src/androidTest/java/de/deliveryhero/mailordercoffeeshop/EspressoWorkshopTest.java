package de.deliveryhero.mailordercoffeeshop;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoWorkshopTest {

    @Rule
    public ActivityTestRule<MainActivity> mainHomePage = new ActivityTestRule<>(MainActivity.class);
    public HashMap<String, Object> orderOptionsMap = orderSelections();

    ViewInteraction closeButton;
    ViewInteraction editTextField_Name;
    ViewInteraction editTextField_Email;

    public static final String NAME = "Sweta Katkoria";
    public static final String EMAIL = "skatkoria@gmail.com";
    public static final String CUSTOM_ORDER = "Enjoying Delivery Hero Test";
    public static final String NAME_PLACEHOLDER = "Enter your name";
    public static final String EMAIL_PLACEHOLDER = "Enter email address";
    public static final String CUSTOM_ORDER_PLACEHOLDER = "Give your custom order a name";

    public static HashMap<String, Object> orderSelections() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("espresso", 2);
        map.put("hot", false);
        map.put("choco", true);
        map.put("milk option", "Low fat");
        map.put("milk type", "Steamed");
        return map;
    }

    public void setUp() {
        closeOnBoardingScreen();
    }

    @Test
    public void createEspressoOrder() {

        selectCoffeeShots(Integer.parseInt(orderOptionsMap.get("espresso").toString()));

        selectCoffeeType((Boolean) orderOptionsMap.get("hot"));

        selectChocolate((Boolean) orderOptionsMap.get("choco"));

        selectMilk(orderOptionsMap.get("milk option").toString(), orderOptionsMap.get("milk type").toString());

        clickReviewOrder();

        fillCustomerForm(NAME, EMAIL, CUSTOM_ORDER);

        submitOrder();

    }

    public void closeOnBoardingScreen() {
        closeButton = onView((withId(R.id.close_button)));
        closeButton.check(matches(isDisplayed()));

        closeButton.perform(click());
        onView(withId(R.id.use_menu))
                .check(matches(isEnabled()));

        onView(withId(R.id.use_custom))
                .check(matches(not(isEnabled())));
    }

    public void closeOnboardingScreen() {
        onView(withId(R.id.close_button))
                .perform(click());

        onView(withId(R.id.use_menu))
                .check(matches(isEnabled()));

        onView(withId(R.id.use_custom))
                .check(matches(not(isEnabled())));
    }

    public void selectCoffeeShots(int count) {
        for (int i = 0; i < count; i++) {
            onView(withId(R.id.more_espresso))
                    .perform(click());
        }
        onView(withId(R.id.espresso_shot_counter))
                .check(matches(withText(String.valueOf(count))));
    }

    public void selectCoffeeType(boolean temp) {
        onView(withId(R.id.beverage_temperature)).check(matches(isChecked()));
        if (!temp) {
            onView(withId(R.id.beverage_temperature))
                    .perform(click())
                    .check(matches(isNotChecked()));
        }
    }

    public void selectChocolate(boolean choco) {
        onView(withId(R.id.chocolate)).check(matches(isNotChecked()));
        if (choco) {
            onView(withId(R.id.chocolate))
                    .perform(click())
                    .check(matches(isChecked()));
        }
    }

    public void selectMilk(String milkSelection, String milkType) {
        onView(withId(R.id.milk_type))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)),
                is(milkSelection)))
                .perform(click());

        onView(withText(milkType))
                .perform(click())
                .check(matches(isChecked()));
    }

    public void clickReviewOrder() {
        onView(withId(R.id.review_order_button))
                .perform(scrollTo(), click());
    }

    public void fillCustomerForm(String name, String emailAddress, String customOrder) {
        onView(withId(R.id.name_text_box)).check(matches(withHint(NAME_PLACEHOLDER)));
        onView(withId(R.id.name_text_box))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.name_text_box)).check(matches(withText(name)));

        onView(withId(R.id.email_text_box)).check(matches(withHint(EMAIL_PLACEHOLDER)));
        onView(withId(R.id.email_text_box))
                .perform(typeText(emailAddress), closeSoftKeyboard());

        onView(withId(R.id.custom_order_name_box)).check(matches(withHint(CUSTOM_ORDER_PLACEHOLDER)));
        onView(withId(R.id.custom_order_name_box))
                .perform(typeText(customOrder), closeSoftKeyboard());
        onView(withId(R.id.custom_order_name_box)).check(matches(withText(customOrder)));
    }

    public void submitOrder() {
        ViewInteraction testOrderField = onView(allOf(withId(R.id.mail_order_button), withText("Submit order")));
        testOrderField.perform(scrollTo(), click());
    }

    @After
    public void clearFields() {

        editTextField_Name.perform(clearText());
        editTextField_Email.perform(clearText());

    }
}