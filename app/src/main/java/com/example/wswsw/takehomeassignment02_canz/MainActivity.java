package com.example.wswsw.takehomeassignment02_canz;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    int quantity=1;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //save user's name in a variable name
        EditText name = (EditText) findViewById(R.id.name_field);
        String userName = name.getText().toString();

        //find 2 toppings CheckBox, save their boolean value
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream__checkbox);
        boolean hasWhippedCream= whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //calculate the price after toppings
        int price=calculatePrice(hasWhippedCream, hasChocolate);

        //create price message including all the info
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, userName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_title,userName));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        Toast.makeText(MainActivity.this,"Thank you!", Toast.LENGTH_SHORT).show();
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String userName) {

        String message;
        message = getString(R.string.order_summary_name, userName);
        message += "\n" + getString(R.string.order_summary_cream, addWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        message += "\n" + getString(R.string.order_summary_quantity, quantity);
        message += "\n " + getString(R.string.order_summary_total,NumberFormat.getCurrencyInstance().format(price)) ;
        message += "\n" + getString(R.string.thank_you);


        return message;


    }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method displays the given price on the screen.
     */

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
}