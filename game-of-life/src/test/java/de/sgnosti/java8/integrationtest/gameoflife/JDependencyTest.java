package de.sgnosti.java8.integrationtest.gameoflife;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;

public class JDependencyTest {
	private static final String PACKAGE_NAME = "de.sgnosti.java8.gameoflife";
	JDepend jdepend = new JDepend();

	@Before
	public void setUp() throws Exception {
		jdepend.addDirectory("/Users/sgnosti/projects/java8-essentials/game-of-life/target/classes");
		jdepend.analyze();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void analyzeDependencies() {
		System.out.println("Packages found: ");
		jdepend.getPackages().stream().map(p -> ((JavaPackage) p).getName()).forEach(System.out::println);

		final JavaPackage pkg = jdepend.getPackage(PACKAGE_NAME);
		assertNotNull(pkg);

		System.out.println(pkg.getName() + "'s efferent packages: ");
		pkg.getEfferents().stream().map(p -> ((JavaPackage) p).getName()).forEach(System.out::println);

		System.out.println(pkg.getName() + "'s afferent packages: ");
		pkg.getAfferents().stream().map(p -> ((JavaPackage) p).getName()).forEach(System.out::println);

		assertFalse(pkg.containsCycle());

		final PackageFilter filter = new PackageFilter();
		jdepend.setFilter(filter);
		filter.addPackage(PACKAGE_NAME);
	}
}
