package library;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import library.core.util.CustomJerseyViolationExceptionMapper;
import library.db.BookDAO;
import library.resources.BookResource;
import org.skife.jdbi.v2.DBI;

public class LibraryApplication extends Application<LibraryConfiguration> {

    public static void main(final String[] args) throws Exception {
        new LibraryApplication().run(args);
    }

    @Override
    public String getName() {
        return "library";
    }

    @Override
    public void initialize(final Bootstrap<LibraryConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<LibraryConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LibraryConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }


    @Override
    public void run(LibraryConfiguration configuration, Environment environment) {
        environment.jersey().register(new CustomJerseyViolationExceptionMapper());
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "library");
        final BookDAO bookDAO = jdbi.onDemand(BookDAO.class);
        final BookResource bookResource = new BookResource(bookDAO);

        environment.jersey().register(bookResource);

    }
}
