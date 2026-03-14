
import org.apache.maven.model.Developer;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LiftNShip{
    protected Model model;
    protected File file;

    public LiftNShip(File file){
        this.file = file;
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (Reader fileReader = ReaderFactory.newXmlReader(file)) {
            model = reader.read(fileReader);
        } catch (IOException | XmlPullParserException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    public void process(){

    }
    public void writeOut(){
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            writer.write( new FileOutputStream(file), model);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

public class PomParserExample {

    public static Plugin createCentralPublishingPlugin(){
        Plugin central = new Plugin();
        central.setGroupId("org.sonatype.central");
        central.setArtifactId("central-publishing-maven-plugin");
        central.setVersion("0.9.0");
        central.setExtensions(true);
        String conf = """
               <configuration>
                    <publishingServerId>central</publishingServerId>
                </configuration>""";
        Xpp3Dom k = null;
        try {
            k = Xpp3DomBuilder.build(new StringReader(conf));
            central.setConfiguration(k);
        } catch (XmlPullParserException | IOException e) {
            throw new RuntimeException(e);
        }
        return central;
    }

    public static Plugin createJavaDocPlugin(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-javadoc-plugin");
        p.setVersion("3.12.0");
        return p;
    }

    public static Plugin createJavaDocPluginManagment(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-javadoc-plugin");
        p.setVersion("3.12.0");
        PluginExecution e = new PluginExecution();
        e.addGoal("jar");
        e.setId("attach-javadocs");
        String config = """
                     <configuration>
                        <additionalOptions>
                            <additionalOption>-Xdoclint:none</additionalOption>
                        </additionalOptions>
                     </configuration>
                """;
        try {
            Xpp3Dom k = Xpp3DomBuilder.build(new StringReader(config));
            p.setConfiguration(k);
        } catch (XmlPullParserException | IOException ex) {
            throw new RuntimeException(ex);
        }
        p.addExecution(e);
        return p;
    }

    public static Plugin createSourcePlugin(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-source-plugin");
        p.setVersion("3.2.1");
        return p;
    }

    public static Plugin createSourcePluginManagement(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-source-plugin");
        p.setVersion("3.2.1");
        PluginExecution e = new PluginExecution();
        e.setGoals(List.of("jar"));
        p.addExecution(e);
        return p;
    }

    public static Plugin createGpgPlugin(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-gpg-plugin");
        return p;
    }

    public static Plugin createGpgPluginManagement(){
        Plugin p = new Plugin();
        p.setGroupId("org.apache.maven.plugins");
        p.setArtifactId("maven-gpg-plugin");
        PluginExecution e = new PluginExecution();
        e.setId("sign-artifacts");
        e.addGoal("sign");
        p.addExecution(e);
        return p;
    }

    public static Developer createDeveloper(){
        Developer d = new Developer();
        d.setName("Edward Guy Capriolo");
        d.setEmail("edlinuxguru@gmail.com");
        d.setOrganization("teknek.io");
        d.setOrganizationUrl("https://github.com/edwardcapriolo");
        return d;
    }

    public static void main(String[] args) {
        String releaseTagVersion = "0.9.0-edgyrc1";
        LiftNShip l = new LiftNShip(new File("/home/edward/incubator-livy/pom.xml")){
            @Override
            public void process() {
                if (!model.getGroupId().contains("io.teknek")) {
                    model.setGroupId("io.teknek." + model.getGroupId());
                    model.setVersion(releaseTagVersion);
                    model.getMailingLists().clear();
                    model.setIssueManagement(null);
                    model.addDeveloper(createDeveloper());

                    final Model parentModel = this.model;
                    var submodules = model.getModules();
                    //System.out.println(submodules);
                    //[api, assembly, client-common, client-http, core, core/scala-${scala.binary.version}, coverage, examples, python-api, repl, repl/scala-${scala.binary.version}, rsc, scala, scala-api, scala-api/scala-${scala.binary.version}, server, test-lib, integration-test]

                    model.getModules().remove("python-api");
                    model.getModules().remove("coverage");
                    model.getScm().setConnection("scm:git:git@github.com:ecapriolo/incubator-livy.git");
                    model.getScm().setDeveloperConnection("scm:git:https://github.com/edwardcapriolo/incubator-livy.git");
                    model.getScm().setUrl("scm:git:git@github.com:ecapriolo/incubator-livy.git");

                    for (String m : submodules){
                        File subModuleFolder = new File( this.file.getParentFile(), m);
                        if(subModuleFolder.exists()){
                            LiftNShip child = getLiftNShip(subModuleFolder, parentModel);
                            child.writeOut();
                        }
                    }
                    //model.getBuild().getPluginManagement().getPlugins().removeIf( y -> y.getArtifactId().equals("apache-rat-plugin"));
                    List<Plugin> plugins = model.getBuild().getPlugins();
                    model.getBuild().setPlugins(plugins.stream().filter( y -> ! y.getArtifactId().equals("apache-rat-plugin")).toList());
                    plugins = model.getBuild().getPlugins();

                    List<Plugin> manage = model.getBuild().getPluginManagement().getPlugins();
                    model.getBuild().getPluginManagement()
                            .setPlugins(new ArrayList<>(manage.stream().filter( y -> ! y.getArtifactId().equals("maven-javadoc-plugin")).toList()));
                    model.getBuild().getPluginManagement().addPlugin(createSourcePluginManagement());
                    model.getBuild().getPluginManagement().addPlugin(createGpgPluginManagement());
                    //model.getBuild().getPluginManagement().getPluginsAsMap().remove("maven-javadoc-plugin");

                    model.getBuild().getPluginManagement().addPlugin(createJavaDocPluginManagment());

                    List<Plugin> copy = new ArrayList<>(model.getBuild().getPlugins().stream().toList());
                    copy.add(createCentralPublishingPlugin());
                    copy.add(createJavaDocPlugin());
                    copy.add(createSourcePlugin());
                    copy.add(createGpgPlugin());
                    model.getBuild().setPlugins(copy);

                    LiftNShip rsc = new LiftNShip(new File("/home/edward/incubator-livy/rsc/pom.xml")) {
                        @Override
                        public void process() {
                            model.getBuild().getPlugins().stream().forEach(plugin ->{
                                if ("maven-dependency-plugin".equals(plugin.getArtifactId())) {
                                    System.out.println("plugin " + plugin);
                                    System.out.println(plugin.getExecutions());
                                    PluginExecution z = plugin.getExecutionsAsMap().get("copy-rsc-jar");
                                    String entire = """
  <configuration>
  <artifactItems>
    <artifactItem>
      <groupId>io.teknek.org.apache.livy</groupId>
      <artifactId>livy-rsc</artifactId>
      <version>${project.version}</version>
      <type>jar</type>
      <overWrite>true</overWrite>
      <outputDirectory>${project.build.directory}/jars</outputDirectory>
    </artifactItem>
  </artifactItems>
</configuration>
                                            """;
                                    try {
                                       Xpp3Dom k = Xpp3DomBuilder.build(new StringReader(entire));
                                       z.setConfiguration(k);
                                    } catch (XmlPullParserException | IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    };
                    rsc.process();
                    rsc.writeOut();

                    //This are build dependent modules {scala.version} in name dictated by profiles
                    LiftNShip core = getLiftNShip("/core/scala-2.12/pom.xml", parentModel);
                    core.writeOut();

                    LiftNShip repl = getLiftNShip("/repl/scala-2.12/pom.xml", parentModel);
                    repl.writeOut();

                    LiftNShip scalaApi = getLiftNShip("/scala-api/scala-2.12/pom.xml", parentModel);
                    scalaApi.writeOut();

                }
            }

            private LiftNShip getLiftNShip(String x, Model parentModel) {
                LiftNShip core = new LiftNShip(new File(file.getParent() + x)) {
                    public void process() {
                        model.getParent().setGroupId(parentModel.getGroupId());
                        model.getParent().setVersion(parentModel.getVersion());
                        model.setGroupId("io.teknek." + model.getGroupId());
                        model.setVersion(releaseTagVersion);
                        if(model.getName() == null){
                            model.setName(model.getArtifactId());
                        }
                        model.getDependencies().stream().forEach( y -> {
                            if (y.getGroupId().startsWith("org.apache.livy")){
                                String origin = y.getGroupId();
                                y.setGroupId("io.teknek." + origin);
                            }
                        });
                    }
                };
                core.process();
                return core;
            }

            private LiftNShip getLiftNShip(File subModuleFolder, Model parentModel) {
                File subFile = new File(subModuleFolder, "pom.xml");
                LiftNShip child = new LiftNShip(subFile){
                    public void process(){
                        model.getParent().setGroupId(parentModel.getGroupId());
                        model.getParent().setVersion(parentModel.getVersion());
                        if (model.getGroupId() != null) {
                            model.setGroupId("io.teknek." + model.getGroupId());
                        }
                        if(model.getName() == null){
                            model.setName(model.getArtifactId());
                        }
                        model.setVersion(releaseTagVersion);
                        model.getDependencies().stream().forEach( y -> {
                            if (y.getGroupId().startsWith("org.apache.livy")){
                                String origin = y.getGroupId();
                                y.setGroupId("io.teknek." + origin);
                            }
                        });
                    }
                };
                child.process();
                return child;
            }
        };
        l.process();
        l.writeOut();



    }
}