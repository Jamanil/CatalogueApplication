package ru.jamanil.catalog;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jamanil.catalog.util.ClientGui;

@SpringBootApplication
public class CatalogApplicationClient {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(CatalogApplicationClient.class)
				.headless(false)
				.run(args);
		ClientGui gui = context.getBean(ClientGui.class);
		gui.showClientGui();
	}
}
