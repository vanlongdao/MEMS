package arrow.mems.test;

import java.io.File;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.annotations.AfterTest;

import arrow.framework.persistence.qualifier.ArrowDB;

@ArquillianSuiteDeployment
@CreateSchema({"drop_db.sql", "create_db.sql"})
public class DeploymentManager extends Arquillian {
  protected static WebArchive war;
  @Inject
  @ArrowDB
  EntityManager emMain;
  @Inject
  UserTransaction transaction;

  protected static WebArchive createWar() {
    final WebArchive war = DeploymentManager.createWarForWildFly("arrow");
    war.addPackages(true, "arrow");
    war.toString(true);

    return war;
  }

  private static WebArchive createWarForWildFly(final String string) {
    // When

    final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test2.war");
    final PomEquippedResolveStage resolver = Maven.resolver().loadPomFromFile("pom.xml");
    archive.addAsLibraries(resolver.importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
    archive.addPackages(true, Filters.exclude(".*Test\\.class"), "arrow");
    // // testing, resolve mockito
    archive.addAsLibraries(resolver.resolve("org.mockito:mockito-all").withTransitivity().asFile());
    archive.addAsWebInfResource(new File("src/test/resources/arquillian.xml"));
    archive.addAsWebInfResource(new File("src/test/resources/test-persistence.xml"), "classes/META-INF/persistence.xml");
    archive.addAsWebInfResource(new File("src/test/resources/test-ds.xml"));
    archive.addAsWebInfResource(new File("webapp/WEB-INF/beans.xml"));
    archive.addAsManifestResource(new File("src/main/resources/META-INF/apache-deltaspike.properties"));

    final File[] datasets = new File("src/test/resources/datasets").listFiles();
    Arrays.stream(datasets).forEach(d -> archive.addAsWebInfResource(d, "classes/datasets/" + d.getName()));

    final File[] templates = new File("src/main/resources/arrow/templates").listFiles();
    Arrays.stream(templates).forEach(t -> archive.addAsWebInfResource(t, "classes/arrow/templates/" + t.getName()));


    // final File[] resources = new File("src/main/resources").listFiles();
    // Arrays.stream(resources).filter(r -> !r.getName().endsWith("persistence.xml")).forEach(r ->
    // archive.addAsResource(r));
    System.out.println("====== WAR Content ======");
    System.out.println(archive.toString(true));
    System.out.println("====== WAR Deployment ======");
    return archive;
  }

  @Deployment(name = "mems")
  public static Archive<?> deployment() {
    return DeploymentManager.createWar();
  }

  @AfterTest
  public void resetSequence() throws Exception {
    if (this.transaction != null) {
      this.transaction.begin();
      final StringBuffer stringQuery = new StringBuffer();
      stringQuery.append("ALTER SEQUENCE mt_country_country_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE users_user_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE human_resource_hr_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE budget_budget_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE approval_config_config_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE approval_level_level_id_seq RESTART WITH 1; ");
      stringQuery.append("ALTER SEQUENCE approval_level_supervisor_level_supervisor_id_seq RESTART WITH 1; ");
      final Query query = this.emMain.createNativeQuery(stringQuery.toString());
      query.executeUpdate();
      this.transaction.commit();
    }
  }
}
