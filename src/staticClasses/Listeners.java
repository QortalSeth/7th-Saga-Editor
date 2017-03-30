package staticClasses;

import java.math.BigInteger;
import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Listeners
{

    public static void removeNonNumbers(KeyEvent event, List<Character> exceptions)
    {
	char keyTyped = event.getCharacter().charAt(0);

	boolean exception = false;

	if (exceptions != null)
	{
	    for (Character c : exceptions)
	    {
		if (keyTyped == c)
		{
		    exception = true;
		    break;
		}
	    }
	}

	if (!Character.isDigit(keyTyped) && exception == false)
	{
	    event.consume();
	}
    }

    public static ChangeListener<Boolean> textFieldFocusListener(final int maxValue)
    {
	ChangeListener<Boolean> threeDigitListener = new ChangeListener<Boolean>()
	{

	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
	    {
		if (!newValue)
		{
		    ReadOnlyBooleanProperty a = (ReadOnlyBooleanProperty) observable;
		    TextField tf = (TextField) a.getBean();

		    if (tf.getText().isEmpty())
		    {
			tf.setText("0");
			return;
		    }

		    BigInteger bigValue = new BigInteger(tf.getText());
		    int value = bigValue.min(BigInteger.valueOf(maxValue)).intValueExact();

		    if (value < 0)
		    {
			value = 0;
		    }

		    if (value > maxValue)
		    {
			value = maxValue;
		    }

		    tf.setText(Integer.toString(value));
		}

	    }
	};
	return threeDigitListener;
    }
}
