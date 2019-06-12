package com.ankit.spring.camel.api;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootApacheCamelApplication extends RouteBuilder {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApacheCamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		System.out.println("Started.....");
		// moveAllFile();
		// moveSpecificFile("myfile");
		processFile();
		System.out.println("End.....");

	}

	public void moveAllFile() {
		from("file:C:/Users/Admin/Desktop/a").to("file:C:/Users/Admin/Desktop/b");

	}

	public void moveSpecificFile(String type) {
		from("file:C:/Users/Admin/Desktop/a?noop=true").filter(header(Exchange.FILE_NAME).startsWith(type))
				.to("file:C:/Users/Admin/Desktop/b");
	}

	public void processFile() {
		from("file:source?noop=true").unmarshal().csv().split(body().tokenize(",")).choice()
				.when(body().contains("Closed")).to("file:destination?fileName=closed.csv")
				.when(body().contains("Pending")).to("file:destination?fileName=pending.csv")
				.when(body().contains("Interest")).to("file:destination?fileName=interest.csv");
	}

}
