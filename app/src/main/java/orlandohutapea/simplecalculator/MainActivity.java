package orlandohutapea.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    enum NumberOperation {
        OP_ADD,
        OP_SUB,
        OP_MUL,
        OP_DIV,
        OP_NONE
    }

    double          savedNumber      = .0;
    boolean         isNegated        = false;
    NumberOperation currentOperation = NumberOperation.OP_NONE;

    public String formatNumber(double number) {
        if (number == (long)number)
            return String.format(Locale.getDefault(), "%d", (long)number);
        else
            return String.format(Locale.getDefault(), "%s", number);
    }

    public void appendText(TextView textView, String string) {
        String newText = textView.getText().toString().concat(string);
        textView.setText(newText);
    }

    public void onClearExpressionClick(View view) {
        if (isNegated) isNegated = false;

        TextView numberTextView = findViewById(R.id.numberView);
        numberTextView.setText(R.string.zero);
    }

    public void onClearClick(View view) {
        savedNumber = .0;
        currentOperation = NumberOperation.OP_NONE;
        if (isNegated) isNegated = false;

        TextView numberTextView = findViewById(R.id.numberView),
                 prevNumberTextView = findViewById(R.id.prevNumberView),
                 operationTextView = findViewById(R.id.operationView);
        numberTextView.setText(R.string.zero);
        prevNumberTextView.setText(null);
        operationTextView.setText(null);
    }

    public void onBackspaceClick(View view) {
        TextView numberTextView = findViewById(R.id.numberView);
        CharSequence numberText = numberTextView.getText();
        if (numberText.length() > 0) {
            String minusSymbol = getResources().getString(R.string.minus);
            if (numberText.length() == 1 ||
                    (numberText.length() == 2 && numberText.toString().contains(minusSymbol))) {
                if (numberText.toString().contains(minusSymbol))
                    isNegated = false;

                numberTextView.setText(R.string.zero);
            } else
                numberTextView.setText(numberText.subSequence(0, numberText.length() - 1));
        }
    }

    public void onPlusClick(View view)     { onOperationClick(view, NumberOperation.OP_ADD);}
    public void onMinusClick(View view)    { onOperationClick(view, NumberOperation.OP_SUB);}
    public void onMultiplyClick(View view) { onOperationClick(view, NumberOperation.OP_MUL);}
    public void onObelusClick(View view)   { onOperationClick(view, NumberOperation.OP_DIV);}
    public void onOperationClick(View view, NumberOperation operation) {
        TextView numberTextView = findViewById(R.id.numberView),
                 prevNumberTextView = findViewById(R.id.prevNumberView),
                 operationTextView = findViewById(R.id.operationView);
        CharSequence numberText = numberTextView.getText();

        if (numberText.length() == 0)
            return;

        double number = Double.parseDouble(numberText.toString());

        if (currentOperation != NumberOperation.OP_NONE) {
            double switchNumber = .0;
            switch (currentOperation) {
                case OP_ADD:
                    switchNumber = savedNumber + number;
                    break;
                case OP_SUB:
                    switchNumber = savedNumber - number;
                    break;
                case OP_MUL:
                    switchNumber = savedNumber * number;
                    break;
                case OP_DIV:
                    switchNumber = savedNumber / number;
                    break;
            }
            number = switchNumber;
        }

        if (Double.isNaN(number))
            return;

        savedNumber = number;

        // Set symbol to current operation
        int symbol = 0;
        switch (operation) {
            case OP_ADD:
                symbol = R.string.plus;
                break;
            case OP_SUB:
                symbol = R.string.minus;
                break;
            case OP_MUL:
                symbol = R.string.multiply;
                break;
            case OP_DIV:
                symbol = R.string.obelus;
                break;
        }

        numberTextView.setText(R.string.zero);
        prevNumberTextView.setText(formatNumber(savedNumber));
        operationTextView.setText(symbol);

        currentOperation = operation;
    }

    public void onEqualClick(View view) {
        TextView numberTextView = findViewById(R.id.numberView),
                 prevNumberTextView = findViewById(R.id.prevNumberView),
                 operationTextView = findViewById(R.id.operationView);
        CharSequence numberText = numberTextView.getText();

        if (numberText.length() == 0 || currentOperation == NumberOperation.OP_NONE)
            return;

        double result = .0;
        double number = Double.parseDouble(numberText.toString());
        switch (currentOperation) {
            case OP_ADD:
                result = savedNumber + number;
                break;
            case OP_SUB:
                result = savedNumber - number;
                break;
            case OP_MUL:
                result = savedNumber * number;
                break;
            case OP_DIV:
                result = savedNumber / number;
                break;
        }

        if (Double.isNaN(result))
            return;

        savedNumber = .0;
        currentOperation = NumberOperation.OP_NONE;
        if (isNegated) isNegated = false;

        numberTextView.setText(formatNumber(result));
        prevNumberTextView.setText(null);
        operationTextView.setText(null);
    }

    public void onZeroClick(View view)  { onNumberClick(view, 0); }
    public void onOneClick(View view)   { onNumberClick(view, 1); }
    public void onTwoClick(View view)   { onNumberClick(view, 2); }
    public void onThreeClick(View view) { onNumberClick(view, 3); }
    public void onFourClick(View view)  { onNumberClick(view, 4); }
    public void onFiveClick(View view)  { onNumberClick(view, 5); }
    public void onSixClick(View view)   { onNumberClick(view, 6); }
    public void onSevenClick(View view) { onNumberClick(view, 7); }
    public void onEightClick(View view) { onNumberClick(view, 8); }
    public void onNineClick(View view)  { onNumberClick(view, 9); }
    public void onNumberClick(View view, int numberInput) {
        TextView numberTextView = findViewById(R.id.numberView);
        CharSequence numberText = numberTextView.getText();
        double number = Double.parseDouble(numberText.toString());

        if (number == 0. && !numberText.toString()
                                       .contains(getResources().getString(R.string.dot)))
            numberTextView.setText(String.valueOf(numberInput));
        else if (number >= -Float.MAX_VALUE && number <= Float.MAX_VALUE)
            appendText(numberTextView, String.valueOf(numberInput));
    }

    public void onDotClick(View view) {
        TextView numberTextView = findViewById(R.id.numberView);
        String dotSymbol = getResources().getString(R.string.dot);
        if (!numberTextView.getText().toString().contains(dotSymbol))
            appendText(numberTextView, dotSymbol);
    }

    public void onPlusMinusClick(View view) {
        TextView numberTextView = findViewById(R.id.numberView);
        CharSequence numberText = numberTextView.getText();
        if (numberText.length() > 0) {
            isNegated = !isNegated;
            if (isNegated) {
                numberTextView.setText(R.string.minus);
                appendText(numberTextView, numberText.toString());
            } else
                numberTextView.setText(numberText.subSequence(1, numberText.length()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
