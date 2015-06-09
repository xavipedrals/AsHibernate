import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import taules.Casella;
import taules.CasellaPK;
import taules.Partida;
import java.util.Scanner;

/**
 * Created by xavivaio on 15/04/2015.
 */
public class MainManual {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        boolean sortir = false;
        Scanner scanner = new Scanner(System.in);
        while (!sortir){
            System.out.print("Joc 2048" + "\n");
            System.out.print("----------------" + "\n");
            System.out.print("Tria que vols fer:" + "\n");
            System.out.print("1) Crear partida" + "\n");
            System.out.print("2) Crear casella (no la podràs crear sense una partida)" + "\n");
            System.out.print("3) Crear casella (i una partida 'default')" + "\n");
            System.out.print("4) Borrar partida" + "\n");
            System.out.print("5) Borrar casella" + "\n");
            System.out.print("6) Sortir" + "\n");

            String aux = scanner.next();
            switch (aux){
                case ("1"):
                    session.beginTransaction();
                    Partida p = new Partida();
                    p.setIdpartida(0);
                    p.setEstaacabada(false);
                    p.setEstaguanyada(false);
                    p.setPuntuacio(0);
                    session.save(p);

                    session.getTransaction().commit();
                    break;
                case ("2"):
                    session.beginTransaction();
                    System.out.print("Escriu l'id de la partida a la que afegir la casella:\n");
                    String id = new Scanner(System.in).next();
                    System.out.print(id + "\n");
                    Partida dbPartida = (Partida) session.get(Partida.class, Integer.parseInt(id));
                    System.out.println(dbPartida.getIdpartida() + " - " + dbPartida.getPuntuacio());

                    Casella c = new Casella();
                    c.setIdpartida(dbPartida.getIdpartida());
                    c.setNumerocolumna(0);
                    c.setNumerofila(0);
                    c.setNumero(2048);
                    session.save(c);

                    session.getTransaction().commit();
                    break;
                case ("3"):
                    break;
                case ("4"):
                    break;
                case ("5"):
                    session.beginTransaction();
                    System.out.print("Escriu l'id de la partida:\n");
                    String idPartida = new Scanner(System.in).next();
                    System.out.print("Escriu numero fila:\n");
                    String numeroFila = new Scanner(System.in).next();
                    System.out.print("Escriu numero columna:\n");
                    String numeroColumna = new Scanner(System.in).next();
                    System.out.print(idPartida + " " + numeroFila + " " + numeroColumna + "\n");

                    CasellaPK casellaPK = new CasellaPK();
                    casellaPK.setIdpartida(Integer.parseInt(idPartida));
                    casellaPK.setNumerofila(Integer.parseInt(numeroFila));
                    casellaPK.setNumerocolumna(Integer.parseInt(numeroColumna));

                    Casella caselladb = (Casella) session.get(Casella.class, casellaPK);
                    System.out.println(caselladb.getIdpartida() + " - " + caselladb.getNumero());

                    session.getTransaction().commit();
                    break;
                case ("6"):
                    sortir = true;
                    break;
            }
        }
        session.close();
    }
}
