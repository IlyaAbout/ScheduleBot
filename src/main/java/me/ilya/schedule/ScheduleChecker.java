package me.ilya.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleChecker {


	public static List<String> getAllClasses() {
		List<String> actualClass = new ArrayList<>();

		Map<DayOfWeek, List<ClassSchedule>> schedule = new HashMap<>();

		try (InputStream inputStream = ScheduleChecker.class.getResourceAsStream("/schedule.txt");
				 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			if (inputStream == null) {
				throw new IOException("Файл schedule.txt не найден в папке ресурсов.");
			}

			List<String> lines = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			parseScheduleFile(lines, schedule);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DayOfWeek currentDay = LocalDate.now().getDayOfWeek();

		if (schedule.containsKey(currentDay)) {
			List<ClassSchedule> todaysClasses = schedule.get(currentDay);

			for (ClassSchedule cls : todaysClasses) {
				actualClass.add(cls.getClassTimeRange() + " > " + cls.getClassName());
			}
		}
		return actualClass;
	}

	public static List<String> getActualClass() {
		List<String> actualClass = new ArrayList<>();

		Map<DayOfWeek, List<ClassSchedule>> schedule = new HashMap<>();

		try (InputStream inputStream = ScheduleChecker.class.getResourceAsStream("/schedule.txt");
				 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			if (inputStream == null) {
				throw new IOException("Файл schedule.txt не найден в папке ресурсов.");
			}

			List<String> lines = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			parseScheduleFile(lines, schedule);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
		LocalTime currentTime = LocalTime.now();

		if (schedule.containsKey(currentDay)) {
			List<ClassSchedule> todaysClasses = schedule.get(currentDay);
			boolean classInProgress = false;
			for (ClassSchedule cls : todaysClasses) {
				if (cls.isClassInProgress(currentTime)) {
					actualClass.add(cls.getClassTimeRange());
					actualClass.add(cls.getClassName());
					classInProgress = true;
					break;
				}
			}
			if (!classInProgress) {
				return actualClass;
			}
		} else {
			return actualClass;
		}

		return actualClass;
	}

	private static void parseScheduleFile(List<String> lines, Map<DayOfWeek, List<ClassSchedule>> schedule) {
		DayOfWeek currentDay = null;
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) continue;

			if (line.startsWith("{")) {
				String dayString = line.substring(1, line.length() - 1).toUpperCase();
				currentDay = DayOfWeek.valueOf(dayString);
				schedule.putIfAbsent(currentDay, new ArrayList<>());
			} else if (currentDay != null && line.contains(">")) {
				String[] parts = line.split(">");
				String timePart = parts[0].trim();
				String className = parts[1].trim();

				String[] times = timePart.split("-");
				LocalTime startTime = LocalTime.parse(times[0].trim(), timeFormatter);
				LocalTime endTime = LocalTime.parse(times[1].trim(), timeFormatter);

				schedule.get(currentDay).add(new ClassSchedule(startTime, endTime, className));
			}
		}
	}
}