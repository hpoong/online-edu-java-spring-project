package com.hopoong.featureflag_app;

import com.hopoong.featureflag_adapter.FeatureflagAdapterConfig;
import com.hopoong.featureflag_usecase.FeatureflagUsecaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import({FeatureflagAdapterConfig.class, FeatureflagUsecaseConfig.class})
@SpringBootApplication
public class FeatureflagAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureflagAppApplication.class, args);
	}

}
