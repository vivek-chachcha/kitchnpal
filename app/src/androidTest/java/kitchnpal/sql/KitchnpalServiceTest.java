package kitchnpal.sql;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.servicerequest.KitchnPalService;

/**
 * Created by matt on 2017-04-03.
 */
public class KitchnpalServiceTest extends AndroidTestCase {
    private KitchnPalService service;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        service = new KitchnPalService();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseInstructions() {
        String raw = "   ";
        ArrayList<String> instructions;
        instructions = service.parseInstructions(raw);
        assertEquals(0, instructions.size());

        raw = "Turn on the oven.   \n";
        instructions = service.parseInstructions(raw);
        assertEquals(1, instructions.size());

        raw = "PreparationFor spice rub:                                        Combine all ingredients in small bowl.                                                                            Do ahead: Can be made 2 days ahead. Store airtight at room temperature.                                    For chimichurri sauce:                                        " +
                "Combine first 8 ingredients in blender; blend until almost smooth. Add 1/4 of parsley, 1/4 of cilantro, and 1/4 of mint; blend until incorporated. Add remaining herbs in 3 more additions, pureeing until almost smooth after each addition.                                                                            Do ahead: Can be made 3 hours ahead. Cover; chill.                                    For beef tenderloin:                                        Let beef stand at room temperature 1 hour.                                                                            " +
                "Prepare barbecue (high heat). Pat beef dry with paper towels; brush with oil. Sprinkle all over with spice rub, using all of mixture (coating will be thick). Place beef on grill; sear 2 minutes on each side. Reduce heat to medium-high. Grill uncovered until instant-read thermometer inserted into thickest part of beef registers 130F for medium-rare, moving beef to cooler part of grill as needed to prevent burning, and turning occasionally, about 40 minutes. Transfer to platter; cover loosely with foil and let rest 15 minutes. Thinly slice beef crosswise. Serve with chimichurri sauce.                                                                            *Available at specialty foods stores and from tienda.com.";
        instructions = service.parseInstructions(raw);
        assertEquals(19, instructions.size());
    }

    public void testParseIngredients() {
        try {
            JSONObject temp = new JSONObject();
            temp.put("name", "Oranges");
            temp.put("amount", 1.5);
            temp.put("unit", "grams");
            JSONArray array = new JSONArray();
            array.put(temp);
            ArrayList<Ingredient> ingredients = service.parseIngredients(array);
            for (Ingredient i: ingredients) {
                assertEquals(i.getIngredientAmount(), 1.5);
                assertEquals(i.getIngredientQuantityType(), QuantityType.GRAMS);
                assertEquals(i.getIngredientName(), "Oranges");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
