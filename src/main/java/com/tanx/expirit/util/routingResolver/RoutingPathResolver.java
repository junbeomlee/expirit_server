package com.tanx.expirit.util.routingResolver;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static net.sf.rubycollect4j.RubyCollections.hp;
import static net.sf.rubycollect4j.RubyCollections.ra;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.rubycollect4j.RubyArray;
import net.sf.rubycollect4j.block.BooleanBlock;

public final class RoutingPathResolver {

	private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{[^}]+\\}");
	private static final Pattern PATH_VAR = Pattern.compile("\\{[^}]+\\}");
	private static final Pattern ANT_AA = Pattern.compile("\\*\\*");
	private static final Pattern ANT_A = Pattern.compile("\\*");
	private static final Pattern ANT_Q = Pattern.compile("\\?");

	private final Environment env;
	private final Set<RoutingPath> routingPaths = newLinkedHashSet();

	/**
	 * Creates a {@link RoutingPathResolver}.
	 * 
	 * @param appCtx
	 *            the Spring {@link ApplicationContext}
	 * @param basePackages
	 *            packages to be searched
	 */
	public RoutingPathResolver(ApplicationContext appCtx, String... basePackages) {
		env = appCtx.getEnvironment();
		Map<String, Object> beans = appCtx.getBeansWithAnnotation(Controller.class);
		beans.putAll(appCtx.getBeansWithAnnotation(RestController.class));
		retainBeansByPackageNames(beans, basePackages);

		for (Object bean : beans.values()) {
			Class<?> beanClass = null;
			if (bean.getClass().getCanonicalName().contains("$$EnhancerBySpring")) {
				if (bean instanceof Advised) {
					beanClass = ((Advised) bean).getTargetClass();
				}
			} else {
				beanClass = bean.getClass();
			}
			List<Method> mappingMethods = getMethodsListWithAnnotation(beanClass, RequestMapping.class, bean);

			RequestMapping classRM = beanClass.getAnnotation(RequestMapping.class);
			for (Method method : mappingMethods) {
				RequestMapping methodRM = method.getAnnotation(RequestMapping.class);
				for (Entry<String, RequestMethod> rawPathAndMethod : computeRawPaths(classRM, methodRM)) {
					String rawPath = rawPathAndMethod.getKey();
					String path = computePath(rawPath);
					String regexPath = computeRegexPath(path);
					routingPaths
							.add(new RoutingPath(rawPathAndMethod.getValue(), rawPath, path, Pattern.compile(regexPath),
									beanClass.getAnnotations(), method.getAnnotations(), method.getReturnType()));
				}
			}

			// List<Method> mappingMethods =
			// getMethodsListWithAnnotation(bean.getClass(),
			// RequestMapping.class, bean);
			//
			// RequestMapping classRM =
			// bean.getClass().getAnnotation(RequestMapping.class);
			// for (Method method : mappingMethods) {
			// RequestMapping methodRM =
			// method.getAnnotation(RequestMapping.class);
			// for (Entry<String, RequestMethod> rawPathAndMethod :
			// computeRawPaths(classRM, methodRM)) {
			// String rawPath = rawPathAndMethod.getKey();
			// String path = computePath(rawPath);
			// String regexPath = computeRegexPath(path);
			// routingPaths
			// .add(new RoutingPath(rawPathAndMethod.getValue(), rawPath, path,
			// Pattern.compile(regexPath),
			// bean.getClass().getAnnotations(), method.getAnnotations(),
			// method.getReturnType()));
			// }
			// }
		}
	}

	/**
	 * Returns a list of {@link RoutingPath} under given package bases.
	 * 
	 * @return a list of {@link RoutingPath}
	 */
	public List<RoutingPath> getRoutingPaths() {
		return newArrayList(routingPaths);
	}

	/**
	 * Finds {@link RoutingPath}s by given annotation which may show on class or
	 * method level of a {@link RequestMapping}.
	 * 
	 * @param annoType
	 *            the class of an annotation
	 * @return founded {@link RoutingPath}
	 */
	public List<RoutingPath> findByAnnotationType(final Class<? extends Annotation> annoType) {
		return ra(routingPaths).keepIf(new BooleanBlock<RoutingPath>() {

			@Override
			public boolean yield(RoutingPath item) {
				return ra(item.getClassAnnotations()).anyʔ(new BooleanBlock<Annotation>() {

					@Override
					public boolean yield(Annotation item) {
						return annoType.equals(item.annotationType());
					}

				}) || ra(item.getMethodAnnotations()).anyʔ(new BooleanBlock<Annotation>() {

					@Override
					public boolean yield(Annotation item) {
						return annoType.equals(item.annotationType());
					}

				});
			}

		}).toA();
	}

	/**
	 * Finds {@link RoutingPath}s by given annotation which only show on class
	 * level of a {@link RequestMapping}.
	 * 
	 * @param annoType
	 *            the class of an annotation
	 * @return founded {@link RoutingPath}
	 */
	public List<RoutingPath> findByClassAnnotationType(final Class<? extends Annotation> annoType) {
		return ra(routingPaths).keepIf(new BooleanBlock<RoutingPath>() {

			@Override
			public boolean yield(RoutingPath item) {
				return ra(item.getClassAnnotations()).anyʔ(new BooleanBlock<Annotation>() {

					@Override
					public boolean yield(Annotation item) {
						return annoType.equals(item.annotationType());
					}

				});
			}

		}).toA();
	}

	/**
	 * Finds {@link RoutingPath}s by given annotation which only show on method
	 * level of a {@link RequestMapping}.
	 * 
	 * @param annoType
	 *            the class of an annotation
	 * @return founded {@link RoutingPath}
	 */
	public List<RoutingPath> findByMethodAnnotationType(final Class<? extends Annotation> annoType) {
		return ra(routingPaths).keepIf(new BooleanBlock<RoutingPath>() {

			@Override
			public boolean yield(RoutingPath item) {
				return ra(item.getMethodAnnotations()).anyʔ(new BooleanBlock<Annotation>() {

					@Override
					public boolean yield(Annotation item) {
						return annoType.equals(item.annotationType());
					}

				});
			}

		}).toA();
	}

	/**
	 * Finds {@link RoutingPath}s by given path and request method.
	 * 
	 * @param requestPath
	 *            to be found
	 * @param method
	 *            to be matched
	 * @return founded {@link RoutingPath}
	 */
	public RoutingPath findByRequestPathAndMethod(String requestPath, RequestMethod method) {
		for (RoutingPath routingPath : routingPaths) {
			if (routingPath.getPath().equals(requestPath) && routingPath.getMethod().equals(method))
				return routingPath;
		}

		for (RoutingPath routingPath : routingPaths) {
			if (requestPath.matches(routingPath.getRegexPath().pattern()) && routingPath.getMethod().equals(method))
				return routingPath;
		}

		return null;
	}

	/**
	 * Finds {@link RoutingPath}s by given path.
	 * 
	 * @param requestPath
	 *            to be found
	 * @return founded {@link RoutingPath}
	 */
	public List<RoutingPath> findByRequestPath(String requestPath) {
		List<RoutingPath> paths = newArrayList();

		for (RoutingPath routingPath : routingPaths) {
			if (routingPath.getPath().equals(requestPath)) {
				paths.add(routingPath);
			} else if (requestPath.matches(routingPath.getRegexPath().pattern())) {
				paths.add(routingPath);
			}
		}

		return paths;
	}

	private List<Entry<String, RequestMethod>> computeRawPaths(RequestMapping classRM, RequestMapping methodRM) {
		List<Entry<String, RequestMethod>> rawPathsAndMethods = newArrayList();

		RubyArray<String> topPaths = classRM == null ? ra("") : ra(classRM.value()).uniq();
		RubyArray<String> lowPaths = ra(methodRM.value()).uniq();
		if (topPaths.isEmpty())
			topPaths.unshift("");
		if (lowPaths.isEmpty())
			lowPaths.unshift("");

		while (topPaths.anyʔ()) {
			String topPath = topPaths.shift();
			while (lowPaths.anyʔ()) {
				String lowPath = lowPaths.shift();
				if (methodRM.method().length == 0) {
					for (RequestMethod m : RequestMethod.values()) {
						rawPathsAndMethods.add(hp(PathUtils.joinPaths(topPath, lowPath), m));
					}
				} else {
					for (RequestMethod m : methodRM.method()) {
						rawPathsAndMethods.add(hp(PathUtils.joinPaths(topPath, lowPath), m));
					}
				}
			}
		}

		return rawPathsAndMethods;
	}

	private String computeRegexPath(String path) {
		path = Regexs.escapeSpecialCharacters(path, PLACEHOLDER, PATH_VAR, ANT_AA, ANT_A, ANT_Q);
		Matcher m = PATH_VAR.matcher(path);
		while (m.find()) {
			String match = m.group();
			path = path.replaceFirst(Pattern.quote(match), "[^/]+");
		}
		m = ANT_AA.matcher(path);
		while (m.find()) {
			String match = m.group();
			path = path.replaceFirst(Pattern.quote(match), ".\"");
		}
		m = ANT_A.matcher(path);
		while (m.find()) {
			String match = m.group();
			path = path.replaceFirst(Pattern.quote(match), "[^/]*");
		}
		m = ANT_Q.matcher(path);
		while (m.find()) {
			String match = m.group();
			path = path.replaceFirst(Pattern.quote(match), ".");
		}
		path = path.replaceAll(Pattern.quote("\""), "*");

		// the first slash of an URL can be omitted
		path = path.startsWith("/") ? path = "/?" + path.substring(1) : "/?" + path;
		// the last slash of an URL is optional if user not mentions
		if (!path.endsWith("/"))
			path = path + "/?";

		return path;
	}

	private String computePath(String rawPath) {
		String path = rawPath;
		Matcher m = PLACEHOLDER.matcher(rawPath);
		while (m.find()) {
			String placeholder = m.group();
			String trimmedPlaceholder = placeholder.substring(2, placeholder.length() - 1);
			String[] keyAndDefault = trimmedPlaceholder.split(":");
			String key = keyAndDefault[0];
			String deFault = "";
			if (keyAndDefault.length > 1)
				deFault = keyAndDefault[1];
			path = path.replaceFirst(Pattern.quote(placeholder), env.getProperty(key, deFault));
		}
		return path;
	}

	private void retainBeansByPackageNames(Map<String, Object> beans, String... basePackages) {
		Iterator<Object> beansIter = beans.values().iterator();
		while (beansIter.hasNext()) {
			String beanPackage = beansIter.next().getClass().getPackage().getName();
			boolean isKeep = false;
			for (String packageName : basePackages) {
				if (beanPackage.equals(packageName) || beanPackage.startsWith(packageName + ".")) {
					isKeep = true;
				}
			}
			if (!isKeep)
				beansIter.remove();
		}
	}

	private List<Method> getMethodsListWithAnnotationOnProxy(Class<?> cls,
			final Class<? extends Annotation> annotationCls, Object bean) {

		// cls.get
		// System.out.println(cls.getClass().getName());
		// System.out.println(cls.getCanonicalName());
		// if(cls.getInterfaces().length>0){
		// Class<?>[] asd=cls.getInterfaces();
		// asd.toString();
		// for(int i=0;i<asd.length;i++){
		// if(Advised.class.isAssignableFrom(asd[i])){
		// ((Advised)asd[i].getClass()).getTargetClass();
		// }
		// }
		// }

		if (bean.getClass().getCanonicalName().contains("$$EnhancerBySpring")) {
			// System.out.println(cls.getInterfaces());
			if (bean instanceof Advised) {
				// System.out.println(((Advised)bean).getTargetClass());
				cls = ((Advised) bean).getTargetClass();
			}
		}

		Method[] allMethods = cls.getDeclaredMethods();
		List<Method> annotatedMethods = new ArrayList<Method>();
		for (Method method : allMethods) {
			if (method.getAnnotation(annotationCls) != null) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}

	private List<Method> getMethodsListWithAnnotation(final Class<?> cls,
			final Class<? extends Annotation> annotationCls, Object bean) {

		Method[] allMethods = cls.getDeclaredMethods();
		List<Method> annotatedMethods = new ArrayList<Method>();
		for (Method method : allMethods) {
			if (method.getAnnotation(annotationCls) != null) {
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}

}