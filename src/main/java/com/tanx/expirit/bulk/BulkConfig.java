package com.tanx.expirit.bulk;

import static net.sf.rubycollect4j.RubyCollections.ra;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.tanx.expirit.util.routingResolver.RoutingPathResolver;

import net.sf.rubycollect4j.block.TransformBlock;
import rx.Observable;
import rx.Single;

@Configuration
public class BulkConfig {

	@Autowired
	@Bean
	public RoutingPathResolver getRoutingPathResolver(ApplicationContext appCtx){
		
		Map<String, Object> bulkableBeans = appCtx.getBeansWithAnnotation(RestController.class);
		List<String> basePackageNames = ra(bulkableBeans.values()).map(new TransformBlock<Object, String>() {
			@Override
			public String yield(Object item) {
				return item.getClass().getPackage().getName();
			}
		});
		
		basePackageNames.forEach(System.out::println);
		RoutingPathResolver pathRes = new RoutingPathResolver(appCtx,
				basePackageNames.toArray(new String[basePackageNames.size()]));

		pathRes.getRoutingPaths().forEach(System.out::println);
		return pathRes;
	}
}
