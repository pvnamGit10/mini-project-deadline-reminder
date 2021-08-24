package com.example.deadlineReminder;

import com.example.deadlineReminder.scheduling.SchedulingComponent;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@AllArgsConstructor
public class DeadlineReminderApplication {
	private final SchedulingComponent schedulingComponent;

	public static void main(String[] args) {
		SpringApplication.run(DeadlineReminderApplication.class, args);
	}
//	@Scheduled(cron = "0 0 7 ")
//	void someTest() throws MessagingException {
//		schedulingComponent.checkStatus();
//		schedulingComponent.sendEmailReminder();
//	}

}
