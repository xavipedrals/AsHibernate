import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import taules.Casella;
import taules.CasellaPK;
import taules.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Driver {
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
        DriverControlador dc = new DriverControlador(session);

        boolean sortir = false;
        Scanner scanner = new Scanner(System.in);

        String id_partida, fila, col, valor;
        int id;
        Partida p;

        while (!sortir){
            System.out.print("Joc 2048" + "\n");
            System.out.print("----------------" + "\n");
            System.out.print("Tria que vols fer:" + "\n");
            System.out.print("1) Crear partida" + "\n");
            System.out.print("2) Crear casella donat un id de partida" + "\n");
            System.out.print("3) Crear taulell" + "\n");
            System.out.print("4) Borrar partida" + "\n");
            System.out.print("5) Borrar casella" + "\n");
            System.out.print("6) Consultar partida" + "\n");
            System.out.print("7) Consultar caselles donat id partida" + "\n");
            System.out.print("8) Llistar partides" + "\n");
            System.out.print("9) Llistar partida i les seves caselles" + "\n");
            System.out.print("10) Sortir" + "\n");

            String aux = scanner.next();

            switch (aux){
                case ("1"):
                    id = dc.crearPartida();
                    //Finalment
                    System.out.println("Partida creada amd id " + id);
                    break;
                case ("2"):
                    System.out.print("Escriu l'id de la partida a la que afegir la casella:\n");
                    id_partida = new Scanner(System.in).next();
                    if (dc.getPartida(id_partida) != null){
                        //La partida EXISTEIX llegim les dades de la casella:
                        System.out.println("Escriu numero fila:");
                        fila = new Scanner(System.in).next();

                        System.out.println("Escriu numero columna:");
                        col = new Scanner(System.in).next();

                        System.out.println("Escriu el valor de la casella:\n");
                        valor = new Scanner(System.in).next();
                        if(dc.getCasella(id_partida,fila,col) == null) {
                            if (dc.createCasella(id_partida, fila, col, valor)) {
                                System.out.println("Assiganda a la partida: " + id_partida + " la casella amb dades" +
                                        " " + fila + " " + col + " " + valor);
                            }
                            else System.out.println("No s'ha pogut crear la casella, comprova les dades");
                        }
                        else System.out.println("Ja existeix la casella no es pot crear");
                    }
                    else System.out.println("No existeix una partida amb aquest id");
                    break;
                case ("3"):
                    id = dc.crearPartida();
                    //Ara inicialitzem les 16 caselles i les insertem a la partida
                    for (int i = 0; i < 4; ++i){
                        for (int j = 0; j < 4; ++j) {
                            dc.createCasella(id,i,j,0);
                        }
                    }
                    System.out.println("Partida creada amb id" + id + " i caselles creades.");
                    break;
                case ("4"):
                    System.out.println("Introdueix el id de la partida a esborrar: ");
                    id_partida = new Scanner(System.in).next();
                    if (dc.getPartida(id_partida) != null) {
                        if (dc.deletePartida(id_partida)) System.out.println("Partida esborrada correctament");
                        else System.out.println("No s'ha pogut esborrar la partida");
                    }
                    else System.out.println("No existeix una partida amb aquest id");
                    break;
                case ("5"):
                    //Esborrar casella.
                    System.out.print("Escriu l'id de la partida a la que esborrar la casella:\n");
                    id_partida = new Scanner(System.in).next();

                    if (dc.getPartida(id_partida) != null){
                        System.out.println("Escriu numero fila:");
                        fila = new Scanner(System.in).next();

                        System.out.println("Escriu numero columna:");
                        col = new Scanner(System.in).next();

                        //comprovarem si existeix la casella amb les dades introduïdes
                        if(dc.deleteCasella(id_partida,fila,col)) System.out.println("Casella esborrada");
                        else System.out.println("No s'ha esborrat la casella, comprova les dades");
                    }
                    else System.out.println("No existeix una partida amb aquest id");
                    break;
                case ("6"):
                    System.out.println("Escriu l'id de la partida a consultar");
                    id_partida = new Scanner(System.in).next();
                    p = dc.getPartida(id_partida);
                    if (p != null) System.out.println("Consultada partida amb id " + p.getIdpartida() + " i puntuació " + p.getPuntuacio());
                    else System.out.println("No existeix una partida amb aquest id");
                    break;
                case ("7"):
                    //CONSULTAR CASELLES
                    System.out.println("Escriu l'id de la partida de la que vols consultar les caselles");
                    id_partida = new Scanner(System.in).next();
                    if (dc.getPartida(id_partida) != null) {
                        List<Casella> l = dc.getCaselles(id_partida);
                        if (l != null) {
                            dc.llistarCaselles(l);
                        } else {
                            System.out.println("No hi ha caselles assignades a la partida " + id_partida);
                        }
                    }
                    else{
                        System.out.println("No existeix una partida amb aquest id");
                    }
                    break;
                case("8"):
                    //Llistar partides
                    System.out.println("La llista de les partides actuals es: ");
                    List<Partida> l2 = dc.getPartides();
                    if(l2 != null) {
                        for (Partida partida : l2) {
                            System.out.println("Partida: " + partida.getIdpartida() + " puntuació: " + partida.getPuntuacio());
                        }
                    }
                    else System.out.println("No hi ha partides");
                    break;
                case("9"):
                    System.out.println("Escriu l'id de la partida de la qual vols consultar la informació i les caselles");
                    id_partida = new Scanner(System.in).next();
                    p = dc.getPartida(id_partida);
                    if(p != null){
                        System.out.println("Partida: " + p.getIdpartida() + " puntuació: " + p.getPuntuacio());
                        List<Casella> l = dc.getCaselles(id_partida);
                        if (l != null) {
                            dc.llistarCaselles(l);
                        }
                        else System.out.println("No hi ha caselles assignades a la partida " + id_partida);
                    }
                    else System.out.println("No existeix una partida amb aquest id");
                    break;
                case ("10"):
                    sortir = true;
                    break;
            }
        }
        session.close();
    }

    private static class DriverControlador {
        private Session session;

        DriverControlador(Session s) {
            session = s;
        }

        public boolean createCasella(int id, int fila, int col, int valor){
            session.beginTransaction();
                Casella c = new Casella();
                c.setIdpartida(id);
                c.setNumerocolumna(col);
                c.setNumerofila(fila);
                c.setNumero(valor);
                session.save(c);
            session.getTransaction().commit();
            return true;
        }

        public boolean createCasella(String id, String fila, String col, String valor){
            session.beginTransaction();
                Casella c = new Casella();
                c.setIdpartida(Integer.parseInt(id));
                c.setNumerocolumna(Integer.parseInt(col));
                c.setNumerofila(Integer.parseInt(fila));
                c.setNumero(Integer.parseInt(valor));
            session.save(c);
            session.getTransaction().commit();
            return true;
        }

        public Casella getCasella(String id, String fila, String col){
            //retorna una instancia de la casella que hi ha a la bd
            CasellaPK casellaPK = new CasellaPK();
            casellaPK.setIdpartida(Integer.parseInt(id));
            casellaPK.setNumerofila(Integer.parseInt(fila));
            casellaPK.setNumerocolumna(Integer.parseInt(col));
            session.beginTransaction();
            Casella caselladb = (Casella) session.get(Casella.class, casellaPK);
            session.getTransaction().commit();
            return caselladb;
        }

        public Casella getCasella(int id, int fila, int col){
            //retorna una instancia de la casella que hi ha a la bd
            CasellaPK casellaPK = new CasellaPK();
            casellaPK.setIdpartida(id);
            casellaPK.setNumerofila(fila);
            casellaPK.setNumerocolumna(col);
            session.beginTransaction();
            //aixo retorna null si no es una casella valida
            Casella caselladb = (Casella) session.get(Casella.class, casellaPK);
            session.getTransaction().commit();
            return caselladb;
        }

        public boolean deleteCasella(String id, String fila, String col){
            boolean b = false;
            if (getPartida(id) != null){
                Casella caselladb = getCasella(id,fila,col);
                if(caselladb!=null) {
                    session.beginTransaction();
                    session.delete(caselladb);
                    session.getTransaction().commit();
                    b  = true;
                }
            }
            return b;
        }


        public int crearPartida() {
            session.beginTransaction();
            Partida p = new Partida();
            p.setIdpartida(0);
            p.setEstaacabada(false);
            p.setEstaguanyada(false);
            p.setPuntuacio(0);
            session.save(p);
            session.getTransaction().commit();
            return p.getIdpartida();
        }

        public Partida getPartida(String s){
            session.beginTransaction();
            Partida partidadb = (Partida) session.get(Partida.class, Integer.parseInt(s));
            session.getTransaction().commit();
            return partidadb;
        }

        public Partida getPartida(int id){
            session.beginTransaction();
            Partida partidadb = (Partida) session.get(Partida.class, id);
            session.getTransaction().commit();
            return partidadb;
        }

        public boolean deletePartida(String s){
            // retorna true si s'esborra la partida
            boolean b = false;
            Partida partidadb = getPartida(s);
            if (partidadb != null){
                session.beginTransaction();
                session.delete(partidadb);
                session.getTransaction().commit();
                b = true;
            }
            return b;
        }

        public List<Partida> getPartides() {
            //Retorna un llistat de totes les partides a la base de dades
            List<Partida> aux = (List<Partida>) session.createQuery("from Partida").list();
            return aux;
        }

        public List<Casella> getCaselles(String id) {
            //Donada la id de partida retorna totes les caselles
            int idpartida = Integer.parseInt(id);
            ArrayList<Casella> aux = new ArrayList<Casella>();
            Casella c2;
            for(int i = 0; i < 4; ++i){
                for (int j = 0 ; j < 4; ++j){
                    c2 = getCasella(idpartida,i,j);
                    if (c2 != null) aux.add(c2);
                }
            }
            if(aux.size() > 0) return aux;
            else return null;
        }

        public int getNextIdPartida() {
            //Cerca quin es el id de la següent partida a crear
            Random r = new Random();
            return r.nextInt(1000) + 1;
        }

        public void llistarCaselles(List<Casella> l) {
            System.out.println("Les caselles que té aquesta partida són:");
            for (Casella casella : l) {
                System.out.println("Casella de la partida : " + casella.getIdpartida()
                                + " fila " + casella.getNumerofila() +
                                " col " + casella.getNumerocolumna() +
                                " amb valor " + casella.getNumero()
                );
            }
        }
    }
}
