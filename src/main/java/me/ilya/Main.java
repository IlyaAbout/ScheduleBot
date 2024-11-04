package me.ilya;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
	public static void main(String[] args) throws TelegramApiException {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		try {
			botsApi.registerBot(new Bot());
			System.out.println("Бот запущен!");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}