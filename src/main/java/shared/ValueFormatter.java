package shared;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;


public class ValueFormatter {
	
	public static final DecimalFormatSymbols DECIMAL_FORMAT = new DecimalFormatSymbols(Locale.ENGLISH);

	
	public static String format(int value) {
		
//		return MessageFormat.format("{0}", value);
		return NumberFormat.getNumberInstance(Locale.US).format(value);
	}
//	public static String format(double value, BidAsk  bidAsk) {
//
//		return String.format(Locale.ENGLISH, "%1$."+ExchangeDataManager.getSharedInstance().getExchange(bidAsk.exchange).decimalPlaces+"f", value);
////		return String.valueOf(value);
//	}
	
	public static String addPostFix(double number) {
		String formattedValue = "";
		if (number >= 1000000) {
			formattedValue = formatDecimal(number / 1000000, 2);
			return (formattedValue + " M");
		} else if (number >= 1000000000) {
			formattedValue = formatDecimal(number / 1000000000, 2);
			return (formattedValue + " B");
		} else
			return formatDecimal(number, 0);
	}

	
	public static String formatDecimal(double number, int decimalPoints) {
		String pattern = "";
		switch (decimalPoints) {
		case -2:
			pattern = "#.##########";
			break;
		case -1:
			pattern = "###,##0.#";
			break;
		case 1:
			pattern = "###,##0.0";
			break;
		case 2:
			pattern = "###,##0.00";
			break;
		case 3:
			pattern = "###,##0.000";
			break;
		case 4:
			pattern = "###,##0.0000";
			break;
		case 5:
			pattern = "###,##0.00000";
			break;
		case 6:
			pattern = "###,##0.000000";
			break;
		case 7:
			pattern = "###,##0.0000000";
			break;
		case 8:
			pattern = "###,##0.00000000";
			break;
		case 9:
			pattern = "###,##0.000000000";
			break;
		case 10:
			pattern = "###,##0.0000000000";
			break;
		case 11:
			pattern = "###,##0.00000000000";
			break;
		default:
			pattern = "###,##0";
			break;
		}
		DecimalFormat myFormatter = new DecimalFormat(pattern, DECIMAL_FORMAT );
		String output = myFormatter.format(number);
		return output;
	}
}
