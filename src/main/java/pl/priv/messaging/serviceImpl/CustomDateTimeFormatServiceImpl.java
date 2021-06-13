package pl.priv.messaging.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import pl.priv.messaging.service.CustomDateTimeFormatService;

@Service
public class CustomDateTimeFormatServiceImpl  implements CustomDateTimeFormatService
{
	@Override
	public String getFormattedDate(String dateAsString) 
	{
		DateTimeFormatter formatDateFrom = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatDateTo = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		String formattedDate = formatDateTo.format(LocalDate.parse(dateAsString, formatDateFrom));
		return formattedDate;
	}
	
	@Override
	public String getFormattedTime(String timeAsString) 
	{
		DateTimeFormatter formatTimeFrom = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter formatTimeTo = DateTimeFormatter.ofPattern("h:mm a");
		String formattedTime = formatTimeTo.format(LocalTime.parse(timeAsString, formatTimeFrom));
		return formattedTime;
	}
}
