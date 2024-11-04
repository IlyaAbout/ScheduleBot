package me.ilya;

import me.ilya.schedule.ScheduleChecker;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


public class Bot extends TelegramLongPollingBot {


	@Override
	public String getBotUsername() {
		return "PO2ScheduleBot";
	}

	@Override
	public String getBotToken() {
		return "7762552965:AAHAQkt3V194uKrs_IeYy748B2jnlw_Y2jM";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
			SendMessage message = new SendMessage();
			message.setChatId(String.valueOf(chatId));

			if (messageText.equalsIgnoreCase("Текущая пара")) {
				message.setText(ScheduleChecker.getActualClass().isEmpty() ? "Пар сейчас нет." : ScheduleChecker.getActualClass().get(0) + " > " + ScheduleChecker.getActualClass().get(1));

				executeMessage(message);
			}

			if (messageText.equalsIgnoreCase("Расписание")) {
				StringBuilder s = new StringBuilder();
				s.append(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")) + ":\n");
				for (String c : ScheduleChecker.getAllClasses()) {
					 s.append(c).append("\n");
				}

				message.setText(String.format(s.toString()));

				executeMessage(message);
			}
		}
	}




	private void executeMessage(SendMessage message) {
		try {
			execute(message); // Отправляем сообщение
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
